package algonquin.cst2335.isla0059;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private static String TAG ="MainActivity";

    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor  editor = prefs.edit();
        Object defaultValue = new Object();
        prefs.getString("VariableName", String defaultValue);
        Object value = new Object();
        Object name = null;
        editor.putString(String null, String value);
        editor.apply();





        Button loginBtn = findViewById(R.id.nextPageButton);
        Log.w("MainActivity", "In onCreate() - Loading Widgets" );
        EditText words = findViewById(R.id.emailEditText);
        loginBtn.setOnClickListener( clk -> {

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", words.getText().toString());
            nextPage.putExtra("Age", 25);
            startActivity( nextPage );
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.w( TAG,"In onStart()-The application is now visible on screen" );
        assert(this !=null);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.e(TAG, "In onResume()");
    }


}