package algonquin.cst2335.isla0059;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    //onStop() onPause()

}