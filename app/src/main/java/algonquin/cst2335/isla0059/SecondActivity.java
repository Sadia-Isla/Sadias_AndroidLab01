package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Object filename = new Object();
        ImageView myImageView = null;
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput( "Picture.png", Context.MODE_PRIVATE);
            Bitmap mBitmap = null;
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (
                IOException e) {
            e.printStackTrace();

        }
        Object bmp = new Object();
        myImageView.setImageBitmap( Bitmap bmp );
        File file = new File(String filename);
        if(file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile(String filename);
        }


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


    }
}
