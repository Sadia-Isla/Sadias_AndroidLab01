package algonquin.cst2335.isla0059;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author sadia
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

    private String stringURL;
    URL url;
    Bitmap image = null ;
    String description = null;
    String iconName = null;
    String current = null;
    String min = null;
    String max = null;
    String humidity = null;
    TextView tv;

    TextView currentTemp ;
    TextView maxTemp;
    TextView minTemp;
    TextView humidity_tv;
    TextView description_tv;
    EditText cityField;
    ImageView icon ;
    boolean isHidden = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch( item.getItemId() )
        {
            case R.id.hide_views:
                isHidden = !isHidden;
                currentTemp.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);
                maxTemp.setVisibility(View.INVISIBLE);
                minTemp.setVisibility(View.INVISIBLE);
                humidity_tv.setVisibility(View.INVISIBLE);
                description_tv.setVisibility(View.INVISIBLE);
                cityField.setText(" ");
                break;
            case R.id.id_increase:
                float oldSize = currentTemp.getTextSize();
                float newSize = oldSize + 1;
                currentTemp.setTextSize(newSize);
                minTemp.setTextSize(newSize);
                maxTemp.setTextSize(newSize);
                humidity_tv.setTextSize(newSize);
                description_tv.setTextSize(newSize);
                cityField.setTextSize(newSize);
                break;
            case R.id.id_decrease:
                 oldSize = currentTemp.getTextSize();
                 newSize = oldSize - 1;
                currentTemp.setTextSize(newSize);
                minTemp.setTextSize(newSize);
                maxTemp.setTextSize(newSize);
                humidity_tv.setTextSize(newSize);
                description_tv.setTextSize(newSize);
                cityField.setTextSize(newSize);
                break;
            case 5:
              String cityName = item.getTitle().toString();
               runForecast(cityName);
                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icon = findViewById(R.id.icon);
        tv = findViewById(R.id.temp);
        tv = findViewById(R.id.minTemp);
        tv = findViewById(R.id.maxTemp);
        tv = findViewById(R.id.humidity);
        tv = findViewById(R.id.description);

       Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Button forecastBtn = findViewById(R.id.forecastButton);
        EditText cityText = findViewById(R.id.cityTextField);

        forecastBtn.setOnClickListener( click -> {
            String cityName = cityText.getText().toString();
            myToolbar.getMenu().add( 1, 5, 10, cityName).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
           runForecast(cityName);
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting forecast")
                    .setMessage("We're calling people in" + cityName + "to look outside their windows and tell us what's the weather look over there.")
                     .setView(new ProgressBar(MainActivity.this))
                     .show();

            Executor newThread = Executors.newSingleThreadExecutor();

            newThread.execute(() -> {
                try {
                    stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

                    url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    // ignore namespaces:
                    factory.setNamespaceAware(false);

                    //create the object
                    XmlPullParser xpp = factory.newPullParser();
                    //read from in, like Scanner
                    xpp.setInput( in  , "UTF-8");


                 while( xpp.next() != XmlPullParser.END_DOCUMENT)
                 {
                 switch (xpp.getEventType())
                 {
                 case XmlPullParser.START_TAG:
                 if(xpp.getName().equals("temperature"))
                 {
                     current = xpp.getAttributeValue(null, "value");
                     min = xpp.getAttributeValue(null, "min");
                     max = xpp.getAttributeValue(null, "max");

                 }
                 else if (xpp.getName().equals("weather"))
                 {
                description =  xpp.getAttributeValue(null, "value");
                iconName =  xpp.getAttributeValue(null, "icon");
                 }
                 else if  (xpp.getName().equals ("humidity"))
                 {
                 humidity = xpp.getAttributeValue(null, "value") ;
                 }
                 break;
                 case XmlPullParser.END_TAG:
                 break;
                 case XmlPullParser.TEXT:
                 break;

                 }
                 }

                    File file = new File(getFilesDir(), iconName +".png");
                    if (file.exists()) {
                        image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName +".png");
                    } else {

                        URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");

                        HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();

                        if (responseCode == 200) {
                            image = BitmapFactory.decodeStream(connection.getInputStream());

                            image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));

                        }
                    }

                    runOnUiThread(() -> {

                        tv.setText("The current temperature is" + current);
                        tv.setVisibility(View.VISIBLE);


                        tv.setText("The min temperature is" + min );
                        tv.setVisibility(View.VISIBLE);


                        tv.setText("The max temperature is" + max );
                        tv.setVisibility(View.VISIBLE);


                        tv.setText("The humidity is" + humidity + "%");
                        tv.setVisibility(View.VISIBLE);


                        tv.setText(description);
                        tv.setVisibility(View.VISIBLE);

                        ImageView iv = findViewById(R.id. icon);
                        iv.setImageBitmap(image);
                        iv.setVisibility(View.VISIBLE);
                        dialog.hide();


                    });


                } catch (IOException | XmlPullParserException e) {
                    Log.e("Connection error:", e.getMessage());
                }


            });
        });//end of lambda function
    }

   private void runForecast(String cityName) {
    return ;
   }
}