package algonquin.cst2335.isla0059;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author sadia
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

    private String stringURL;
    Button forecastBtn = findViewById(R.id.forecastButton);
    EditText cityText = findViewById(R.id.cityTextField);
    ImageView iconName = findViewById(R.id.icon);
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Executor newThread = Executors.newSingleThreadExecutor();

        newThread.execute ( () -> {
            try{
                String cityName = cityText.getText().toString();
                stringURL =" https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        +"&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

                url = new URL (stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());

/**
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( in  , "UTF-8");


                String description = null;
                String iconName = null;
                String current = null;
                String min = null;
                String max = null;
                String humidity = null;


                while(xpp.next())!= XmlPullParser.END_DOCUMENT);
                {
                    switch (xpp.getEventType())
                    {
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("temperature"))
                            {
                               xpp.getAttributeValue(null, "value");
                                xpp.getAttributeValue(null, "min");
                                xpp.getAttributeValue(null, "max");

                            }
                            else if (xpp.getName().equals("weather")
                        {
                            xpp.getAttributeValue(null, "value");
                            xpp.getAttributeValue(null, "icon")
                        }
                        else (xpp.getName().equals(("humidity")))
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
 **/

                String text = (new BufferedReader(
                        new InputStreamReader(in, StandardCharsets.UTF_8)))
                        .lines()
                        .collect(Collectors.joining("\n"));

                JSONObject theDocument = new JSONObject( text );
                JSONObject coord = theDocument.getJSONObject( "coord" );
                JSONArray weatherArray = theDocument.getJSONArray ( "weather" );
                JSONObject position0 = weatherArray.getJSONObject(0);


                String description = position0.getString("description");
                String iconName = position0.getString("icon");
                // weatherArray = theDocument.getJSONArray ( "weather" );
                int vis = theDocument.getInt("visibility");
                String name = theDocument.getString( "name" );
                JSONObject mainObject = theDocument.getJSONObject( "main" );
                double current = mainObject.getDouble("temp");
                double min = mainObject.getDouble("temp_min");
                double max = mainObject.getDouble("temp_max");
                int humidity = mainObject.getInt("humidity");

                runOnUiThread( (  )  -> {
                    TextView tv = findViewById(R.id.temp);
                    tv.setText("The current temperature is" + current);
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.minTemp);
                    tv.setText("The min temperature is" + current);
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.maxTemp);
                    tv.setText("The max temperature is" + current);
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.humidity);
                    tv.setText("The humidity is" + current);
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.description);
                    tv.setText("The description is" + current);
                    tv.setVisibility(View.VISIBLE);

                });

            }
            catch(IOException | JSONException e){
              Log.e("Connection error:", e.getMessage());
            }
        } );

        Bitmap image = null;
        File file = new File(getFilesDir(), iconName + ".png");
        if(file.exists()){
            image = BitmapFactory.decodeFile(getFilesDir() + "/" +iconName + ".png");
        }
         else {
            try {
                URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int responseCode = 0;
            try {
                responseCode = connection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responseCode == 200) {
                try {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // ImageView iv = findViewById(R.id.icon);
              //  iv.setImageBitmap(image);
                try {
                    image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName +".png", Activity.MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }


    }

}