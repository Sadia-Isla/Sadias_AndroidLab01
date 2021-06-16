package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author sadia
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {


    /** This holds the text at the centre of the screen*/
    private  TextView tv = null;
    /** This holds the text at the centre of the screen*/
    private EditText et = null;
    /** This holds the text at the centre of the screen*/
    private  Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         tv = findViewById(R.id.textView);
         et = findViewById(R.id.editText);
         btn = findViewById(R.id.button);

        btn.setOnClickListener(clk ->{
            String password = et.getText().toString();
            checkPasswordComplexity(password);
        });

    }

    /**
     * @param pw The String object that we are checking
     * @return Returns true if
     */
     void checkPasswordComplexity(String pw) {
         return ;
    }

    /**
     *
     * @param c
     * @return
     */
    boolean isSpecialCharacter(char c){
         switch(c){
             case '#':
             case '?':
             case '*':
                 return true;
             default:
                 return false;
         }

    }
}