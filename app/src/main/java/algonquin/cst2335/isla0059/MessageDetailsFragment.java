package algonquin.cst2335.isla0059;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MessageDetailsFragment extends Fragment {

    MessageListFragment.ChatMessage chosenMessage;
    int chosenPosition;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.details_layout, container, false);

        TextView messageView = detailsView.findViewById(R.id.messageView);
        TextView sendView = detailsView.findViewById(R.id.sendView);
        TextView timeView = detailsView.findViewById(R.id.timeView);
        TextView idView = detailsView.findViewById(R.id.databaseIdView);

        messageView.setText ("Message is: " + chosenMessage.getMessage());
        sendView.setText (" Send or Receive?" + chosenMessage.getSendOrReceive());
        timeView.setText (" Time send:" + chosenMessage.getTimeSent());
        idView.setText ("Database id is: " + chosenMessage.getID() );

        Button closeButton = detailsView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(closeClicked ->{

            ChatRoom parent = (ChatRoom)getContext();
            FragmentManager mgr  = parent.getSupportFragmentManager();
            mgr.beginTransaction().remove(this).commit();
            //getParentFragmentManager().beginTransaction().remove(this ).commit();

        });
        Button  deleteButton = detailsView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(deleteClicked ->{
                ChatRoom parentActivity =(ChatRoom)getContext();
                parentActivity.notifyManagerDeleted(chosenMessage, chosenPosition);
                ChatRoom parent = (ChatRoom)getContext();
                FragmentManager mgr  = parent.getSupportFragmentManager();
                mgr.beginTransaction().remove(this).commit();
        });

        return detailsView;
    }
    public MessageDetailsFragment(MessageListFragment.ChatMessage message, int position){
        chosenMessage = message;
        chosenPosition = position;
    }


}
