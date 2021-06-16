package algonquin.cst2335.isla0059;

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

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        EditText messageTyped = findViewById(R.id.messageEdit);
        Button send = findViewById(R.id.sendButton);
        Button receive = findViewById(R.id.receiveButton);
        chatList = findViewById(R.id.myrecycler);
        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));

        send.setOnClickListener(click ->
                {
                    ChatMessage thisMessage = new ChatMessage( messageTyped.getText().toString(), 1, date);
                    messages.add( thisMessage );
                    messageTyped.setText(" ");
                    adt.notifyItemInserted( messages.size());

                }
        );

        receive.setOnClickListener(click ->
                {
                    ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(), 2, date);
                    messages.add( thisMessage );
                    messageTyped.setText(" ");
                    adt.notifyItemInserted( messages.size()-1);
                    //adt.notifyDataSetChanged( );

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
                    position = getAbsoluteAdapterPosition();
                    ChatMessage removedMessage = messages.get(position);
                    messages.remove(position);
                    adt.notifyItemRemoved( position );
                    Snackbar.make(messageText, " You deleted message # " + position,Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk ->{
                                messages.add(position, removedMessage);
                                adt.notifyItemInserted(position);

                            })
                            .show();
                })
                .create().show();

            });
        }

       // public void setPosition(int p) {
         //   position = p;
       // }
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
           // holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
            return super.getItemViewType(position);
        }
    }

    private class ChatMessage{
        String message;
        int sendOrReceive;
        Date timeSent;
        String currentDateAndTime;

        public ChatMessage(String message, int sendOrReceive, Date timeSent) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            currentDateAndTime = sdf.format(timeSent);

            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
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