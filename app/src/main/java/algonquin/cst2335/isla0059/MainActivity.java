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

    @Override //screen is now visible
    protected void onStart() {
        super.onStart();
        Log.w (TAG, "In onStart() - The application is now visible on screen" );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In onCreate() - Loading Widgets" );
        Button loginBtn = findViewById(R.id.nextPageButton);
        Log.w("MainActivity", "In onCreate() - Loading Widgets" );
        EditText TypeWords = findViewById(R.id.emailEditText);
        String TypeWords = prefs.getString("LoginName", "");
        SharedPreferences.Editor  editor = prefs.edit();
        editor.putString(TypeWords,TypeWords);
        editor.apply();

        loginBtn.setOnClickListener( clk -> {

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("TypeWords", TypeWords.getText().toString());
            nextPage.putExtra("Age", 25);
            startActivity( nextPage);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent fromNextPage = data;
        if(requestCode == 5739) //coming back from SecondActivity
        {
            if(resultCode == 535) {
                String city = fromNextPage.getStringExtra("City");
                int age = fromNextPage.getIntExtra("Age", 0);

                }
            }
        }


    @Override
    protected void onResume(){
        super.onResume();
        Log.w (TAG, "In onResume() - The application is now responding to user input" );
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.w (TAG, "In onPause() - The application is now paused" );
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.w (TAG, "In onStop() - The application is now stopped" );
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w (TAG, "In onDestroy() - The application is now destroyed" );
    }
}