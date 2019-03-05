package com.example.mande.newkhanapos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mande.newkhanapos.Services.MyAppllication;
import com.example.mande.newkhanapos.Services.NetworkChangeReceiver;
import com.example.mande.newkhanapos.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends MainActivity implements NetworkChangeReceiver.ConnectivityReceiverListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        fullscreen();
        KeyStringDisplay = findViewById(R.id.passview);
        KeyMap();

        ImageView SettingsView = (ImageView) findViewById(R.id.settings);

        SettingsView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                settingsredirect();
            }
        });

        UserList.clear();
        ArticleList.clear();

        SettingsActivity SA = new SettingsActivity();
        SA.AutoDetect();

        ServeraddrApi = "/user/all";
        new NetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);

        getArticleList getarticleList = new getArticleList();
        getarticleList.getlist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        Disabling the status bar
         */
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        MyAppllication.getInstance().setConnectivityListener(LoginActivity.this);
    }
    @Override
    public void DoAction()
    {
        if (CurrentPressedKey.equals("Ent") || (PressedKey.length() == 4)){
            int Error = 0;
                for(int i = 0; i <= UserList.size(); i++)
                {
                    if(i==UserList.size())
                    {
                        Error = 1;
                    }
                    else if((PressedKey.equals("0000" +"Ent")) || (PressedKey.equals(UserList.get(i).getPassword())))
                    {
                        UserIdentification = i;
                        InfoMessage(UserList.get(i).getFirstName()+" LogIn Successfull",Color.BLUE,32);
                        startActivity( new Intent(this, TableActivity.class));
                        break;
                    }
                }

            // This will happen only if password with all users does not match
            if (Error ==1) {
                InfoMessage("LogIn Incorrect", Color.RED, 32);
                PressedKey = "";
                KeyStringDisplay.setText(PressedKey);
                onBackPressed();
            }

               }
       else{
            KeyStringDisplay.setText(PressedKey);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String LocalStr="";
                    for (int i = 0; i < PressedKey.length(); i++)
                    {
                        LocalStr = LocalStr + "*";
                    }
                    KeyStringDisplay.setText(LocalStr);
                }
            }, 500); // Millisecond 1000 = 1 sec
        }
    }

    public void settingsredirect(){

        startActivity( new Intent(this, SettingsActivity.class));
    }

    @Override
    public void getJson(StringBuffer buffer)
    {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int x;
        try {
            x = jsonArray.length();
        }
        catch(Exception e)
        {
            x = 0;
        }
            for (int i = 0; i < x; i++) {
                JSONObject jsonobject = null;
                try {
                    jsonobject = jsonArray.getJSONObject(i);
                    User user = new User();
                    user.setAutoId(jsonobject.getInt("autoId"));
                    user.setUserId(jsonobject.getInt("userId"));
                    user.setFirstName(jsonobject.getString("firstName"));
                    user.setLastName(jsonobject.getString("lastName"));
                    user.setPassword(jsonobject.getString("password"));
                    user.setAdministrator(jsonobject.getBoolean("administrator"));
                    user.setManager(jsonobject.getBoolean("manager"));
                    UserList.add(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

    }

    @Override
    public void onBackPressed() {

        //remain here
        startActivity( new Intent(this, LoginActivity.class));
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    public void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.WHITE;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();

    }


}
