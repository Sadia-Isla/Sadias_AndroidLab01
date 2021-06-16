package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sadia
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {


    /** This holds the text at the centre of the screen*/
    private  TextView tv = null;
    /** This holds the edit text in the middle of the screen*/
    private EditText et = null;
    /** This holds the login button in the screen*/
    private  Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

         tv = findViewById(R.id.textView);
         et = findViewById(R.id.editText);
         btn = findViewById(R.id.button);

        btn.setOnClickListener(clk ->{
            String password = et.getText().toString();
            checkPasswordComplexity(password);

            if(!foundUpperCase)
            {

                Toast.makeText(getApplicationContext(),"missing an upper case letter",Toast.LENGTH_SHORT).show();  ;// Say that they are missing an upper case letter;

                return true;

            }

            else if( ! foundLowerCase)
            {
                Toast.makeText(getApplicationContext(),"missing a lower case letter",Toast.LENGTH_SHORT).show(); ; // Say that they are missing a lower case letter;

                return true;

            }

            else if( ! foundNumber) {
                Toast.makeText(getApplicationContext()," found a number",Toast.LENGTH_SHORT).show();
                return false;
            }

            else if(! foundSpecial) {

                Toast.makeText(getApplicationContext()," found a number",Toast.LENGTH_SHORT).show();
                return false;
            }

            else

                return true; //only get here if they're all true
        });

    }

    /**
     * @param pw The String object that we are checking
     * @return Returns true if
     */
     boolean checkPasswordComplexity(String pw) {
         return pw.contains("ABC");
    }










}