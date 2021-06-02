package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
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

        Button btn1 = findViewById(R.id.button);
        btn1.setOnClickListener( clk -> {
            Intent next = new Intent(Intent.ACTION_DIAL);
            next.setData(Uri.parse("tel:" + "613 123 4567"));
            startActivity(next);

        });
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener( click ->
        {   //one line to return
            Intent dataBack = new Intent();
            dataBack.putExtra("Age", 35);
            dataBack.putExtra("City", "Ottawa");

            setResult(535, dataBack);//send data back to first page
            finish(); //actuall goes back
            //onActivityResult() in first page is next
        });

    }
}
