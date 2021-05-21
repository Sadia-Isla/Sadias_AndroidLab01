package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView thetext = findViewById(R.id.textview);
        ImageButton myImBtn = findViewById( R.id.myImageButton );
        myImBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Context context = getApplicationContext();
                CharSequence text = "The width = " + myImBtn.getWidth() + " and height = " + myImBtn.getHeight();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        EditText myedit = findViewById(R.id.myedittext);

        Button myButton = findViewById(R.id.mybutton );
        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myButton.getWidth();
                myButton.getHeight();

                String editString = myedit.getText().toString();
                thetext.setText( "Your edit text has: " + editString);
                myButton.setOnClickListener(   vw  ->  thetext.setText("Your edit text has: " + editString)    );

            }
        });

        thetext.setText( "This is new text");

        myButton.setText( thetext.getText());

        CheckBox mycb = findViewById(R.id.thecheckbox);
        Switch mySwitch =findViewById(R.id.myswitch);
        RadioButton myrb = findViewById(R.id.theradiobutton);
        ImageView myimage =findViewById(R.id.imageView);

    }

}