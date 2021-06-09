package algonquin.cst2335.isla0059;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRoom extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(Bundle savedInstanceState);
        setCententView(R.layout.chatlayout);
        RecyclerView myrecycler = findViewById(R.id.myrecycler);
    }


}
