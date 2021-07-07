package algonquin.cst2335.isla0059;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    ArrayList<ChatMessage> messages = new ArrayList<>();
    RecyclerView chatList;
    MyChatAdapter adt;
    Date date = new Date();
    SQLiteDatabase db;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        EditText messageTyped = findViewById(R.id.messageEdit);
        MyOpenHelper opener = new MyOpenHelper( this );
        db = opener.getWritableDatabase();

        Button send = findViewById(R.id.sendButton);
        Button receive = findViewById(R.id.receiveButton);
        chatList = findViewById(R.id.myrecycler);

        Cursor results = db.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);

        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);
       while(results.moveToNext()) {
           long id = results.getInt(_idCol);
           String message = results.getString(messageCol);
           String time = results.getString(timeCol);
           int sendOrReceive = results.getInt(sendCol);
           messages.add(new ChatMessage(message, sendOrReceive, time, id));
       }
        adt = new MyChatAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));

        send.setOnClickListener(click ->
                {
                    ChatMessage thisMessage = new ChatMessage( messageTyped.getText().toString(), 1, date);

                    ContentValues newRow = new ContentValues();
                    newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
                    newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSendOrReceive());
                    newRow.put(MyOpenHelper.col_time_sent, thisMessage.getTimeSent());
                    long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

                    messages.add( thisMessage );
                    messageTyped.setText(" ");
                    adt.notifyItemInserted( messages.size()-1);

                }
        );

        receive.setOnClickListener(click ->
                {
                    ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(), 2, date);
                    ContentValues newRow = new ContentValues();
                    newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
                    newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSendOrReceive());
                    newRow.put(MyOpenHelper.col_time_sent, thisMessage.getTimeSent());
                    long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

                    messages.add( thisMessage );
                    messageTyped.setText(" ");
                    adt.notifyItemInserted( messages.size()-1);

                }
        );

    }

    private class MyRowViews extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;
        int position;
        public MyRowViews( View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(click-> {
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                .setTitle("Question: ")
                .setNegativeButton( "No", (dialog, cl )-> {})
                .setPositiveButton("Yes",(dialog, cl )-> {
                   // position = getAbsoluteAdapterPosition();
                    ChatMessage removedMessage = messages.get(position);
                    messages.remove(position);
                    adt.notifyItemRemoved( position );

                    db.delete(MyOpenHelper.TABLE_NAME, "_id=?" , new String []{Long.toString(removedMessage.getId())});

                    db.execSQL("Insert into " + MyOpenHelper.TABLE_NAME + " values('" + removedMessage.getId() +
                           " ',' " + removedMessage.getMessage() +
                            " ',' " + removedMessage.getSendOrReceive() +
                            " ',' " + removedMessage.getTimeSent() + " ' ); ");


                    Snackbar.make(messageText, " You deleted message # " + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk ->{
                                messages.add(position, removedMessage);
                                adt.notifyItemInserted(position);

                            })
                            .show();
                })
                .create().show();

            });
        }

        public void setPosition(int p) {
            position = p;
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews>{

        @Override
        public MyRowViews onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            int layoutID;
            if(viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;
            View loadedRow = inflater.inflate(layoutID, parent, false);
            return new MyRowViews(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyRowViews holder, int position) {
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
            holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
            return thisRow.getSendOrReceive();
        }
    }

    private class ChatMessage{
        String message;
        int sendOrReceive;
        Date timeSent;
        String currentDateAndTime;
        long id;

         public void setId(long l){ id = l;}
         public long getId(){ return id ;}

        public ChatMessage(String message, int sendOrReceive, Date timeSent) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            currentDateAndTime = sdf.format(timeSent);

            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }
        public ChatMessage(String message, int sendOrReceive, String timeSent, long id){
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.currentDateAndTime = timeSent;
            setId(id);
        }

        public String getTimeSent() {

            return currentDateAndTime;
        }

        public String getMessage() {

            return message;
        }

        public int getSendOrReceive() {

            return sendOrReceive;
        }
    }
}