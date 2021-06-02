package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView topOfScreen = findViewById(R.id.textView);
        Intent fromPrevious = getIntent();
        String text = fromPrevious.getStringExtra("TypeWords");

        int age = fromPrevious.getIntExtra("Age", 0);
        float notThere = fromPrevious.getFloatExtra("notHere", 4.0f);
        topOfScreen.setText("You typed: " + text);
    }
}
