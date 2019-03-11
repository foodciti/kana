package com.example.mande.newkhanapos;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mande.newkhanapos.models.Article;
import com.example.mande.newkhanapos.models.Table;
import com.example.mande.newkhanapos.models.TicketItem;
import com.example.mande.newkhanapos.models.User;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

public class MainActivity extends AppCompatActivity {

    Button[] numKey = new Button[12];
    String PressedKey = "";
    static String Tableno = "";
    static String Articleno = "";
    public String status_split_payment="0";
    String CurrentPressedKey ="";
    static List<Article> ArticleList = new ArrayList<>();
    static List<User> UserList = new ArrayList<>();
    static List<Table> TableList = new ArrayList<>();
    static int UserIdentification;
    static int TableIdentification;

    String ServeraddrHeader = "http://";
    static String Serveraddr = "";
    String ServeraddrApi = "/";

    static HttpURLConnection connection = null;
    static List<TicketItem> TicketItemList = new ArrayList<>();

    /*
    This needs to be initialised in sub class create method
     */
    TextView KeyStringDisplay;

    public void KeyMap() {
        numKey[0] = (Button) findViewById(R.id.num0);
        numKey[1] = (Button) findViewById(R.id.num1);
        numKey[2] = (Button) findViewById(R.id.num2);
        numKey[3] = (Button) findViewById(R.id.num3);
        numKey[4] = (Button) findViewById(R.id.num4);
        numKey[5] = (Button) findViewById(R.id.num5);
        numKey[6] = (Button) findViewById(R.id.num6);
        numKey[7] = (Button) findViewById(R.id.num7);
        numKey[8] = (Button) findViewById(R.id.num8);
        numKey[9] = (Button) findViewById(R.id.num9);
        numKey[10] = (Button) findViewById(R.id.numBack);
        numKey[11] = (Button) findViewById(R.id.numEnt);

        for(int i = 0; i<=11 ; i++) {
            ListenerOnButton(numKey[i]);
        }
    }

    public void ListenerOnButton(final Button numKey){

        numKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (numKey.getText().toString().equals("X")){
                    PressedKey = "";
                }
                else
                {
                    PressedKey = PressedKey + numKey.getText().toString();
                }
                DoAction();
            }
        });

        numKey.setOnTouchListener(new View.OnTouchListener() {
            int Original_Color = numKey.getCurrentTextColor();
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    numKey.setTextColor(Color.RED);
                    CurrentPressedKey = numKey.getText().toString();
                }
                else {
                    numKey.setTextColor(Original_Color);
                }
                return false;
            }
        });
    }

    public void DoAction()
    {
       // define in each sub class
        // what has to be done after pressing keypads
        // For example - Log in functionalty etc
    }

    /*
    This is for network connection and api accessing
     */

    public class NetworkConnectionTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... urls) {

            // Network Settings
            BufferedReader reader = null;

            URL url = null;
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line ="";
               // DatabaseHelper Db = new DatabaseHelper(getApplicationContext());
                while((line = reader.readLine())!= null)
                {
                  //  Db.insertData(line);
                    buffer.append(line);
                }
                getJson(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                return null;
              // e.printStackTrace();
             //  NetworkError(e.getMessage());
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            valuesupdate();
            super.onPostExecute(result);
        }
    }

    public class NetworkConnectionTaskAlt extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... urls) {

            // Network Settings
            BufferedReader reader = null;

            URL url = null;
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line ="";
                while((line = reader.readLine())!= null)
                {
                    buffer.append(line);
                }

                getJsonAlt(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                NetworkError(e.getMessage());
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            valuesupdateAlt();
            super.onPostExecute(result);
        }
    }

    public class PostNetworkConnectionTask extends AsyncTask<String,String,String>{

        //Execute this before the request is made
        @Override
        protected void onPreExecute() {
            InfoMessage("starting", Color.GREEN, 32);
        }

        //Perform the request in background
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            try {

                connection = (HttpURLConnection) new URL(params[0])
                        .openConnection();
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Content-Type", "application/json");
         //       connection.setRequestProperty("Accept", "application/json");
                connection.setConnectTimeout(5000);
                connection.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam = postJson();

                //Create a writer object and make the request
                OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
                outputStream.write(jsonParam.toString());
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                String response = convertInputStreamToString(inputStream);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Run this once the background task returns.
        @Override
        protected void onPostExecute(String result) {
            InfoMessage(result, Color.RED, 32);
            super.onPostExecute(result);
            try {
                JSONObject jsonObject1 = new JSONObject(result);
                if(jsonObject1.getString("success").equals("true")){
                    status_split_payment="1";
                } else{
                    status_split_payment="0";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void InfoMessage(String Info , int color, int size)
    {
        Toast toast = Toast.makeText(getApplicationContext(), Info, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(Color.TRANSPARENT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(color);
        v.setTextSize(size);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void fullscreen()
    {
        /*
        Disabling the status bar
         */
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void getJson(StringBuffer buffer)
    {
        // get respective json objects
        // required in each activity
    }
    public void getJsonAlt(StringBuffer buffer)
    {
        // get respective json objects
        // required in each activity
    }

    @Override
    public void onBackPressed() {

        //remain here
        startActivity( new Intent(this, MainActivity.class));
    }

    public void NetworkError(final String Info)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), Info, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundColor(Color.TRANSPARENT);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.RED);
                v.setTextSize(20);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void valuesupdate()
    {

    }
    public void valuesupdateAlt()
    {

    }

    public JSONObject postJson()
    {
        return null;
    }

    public String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public class NetworkConnectionTaskTable extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... urls) {

            // Network Settings
            BufferedReader reader = null;

            URL url = null;
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                //
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while((line = reader.readLine())!= null)
                {
                    buffer.append(line);
                }

                getJsonTable(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                NetworkError(e.getMessage());
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            getPreviousArticle();
            super.onPostExecute(result);
        }
    }

    public void getJsonTable(StringBuffer buffer)
    {
        // get respective json objects
        // required in each activity
    }
    public void getPreviousArticle(){

    }


}
