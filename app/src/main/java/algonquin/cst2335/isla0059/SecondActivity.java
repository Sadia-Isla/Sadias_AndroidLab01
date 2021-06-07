package algonquin.cst2335.isla0059;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;


public class SecondActivity extends AppCompatActivity {

    ImageView profileImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3456) {
            if (resultCode == RESULT_OK) {
                Bitmap thumbnail = data.getParcelableExtra("data");

                profileImage.setImageBitmap(thumbnail);

                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);


                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String pathToHere = getFilesDir().getPath(); //tell you where you are installed
        File file = new File(pathToHere + "/Picture.png"); // starting at root /
        profileImage = findViewById(R.id.imageView);

        if (file.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(pathToHere + "/Picture.png");
            profileImage.setImageBitmap(bmp);
        }

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        TextView topOfScreen = findViewById(R.id.textView);

        int age = fromPrevious.getIntExtra("Age", 0);
        float notThere = fromPrevious.getFloatExtra("notHere", 4.0f);
        topOfScreen.setText("Welcome back: " + emailAddress);


        Button btn1 = findViewById(R.id.button);


        btn1.setOnClickListener(clk -> {
            Intent next = new Intent(Intent.ACTION_DIAL);

            next.setData(Uri.parse("tel:" + "613 123 4567"));
            startActivity(next);
        });
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(clk -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraIntent, 3456);

        });

    }
}