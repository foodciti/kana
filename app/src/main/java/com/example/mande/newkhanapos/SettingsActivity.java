package com.example.mande.newkhanapos;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsActivity extends MainActivity {
    EditText Serveraddrtext; TextView infotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        fullscreen();

        //Hide Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Serveraddrtext = (EditText) findViewById(R.id.serveraddrid);
        Serveraddrtext.setText(Serveraddr,TextView.BufferType.EDITABLE);

        infotext = (TextView) findViewById(R.id.foundtextid);

        Button Save = (Button) findViewById(R.id.Savebtn);
        Save.setBackgroundColor(Color.GREEN);
        Save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                change();
                backredirect();
            }
        });

        Button Auto = (Button) findViewById(R.id.Autobtn);
        Auto.setBackgroundColor(Color.GREEN);
        Auto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AutoDetect();
              //  backredirect();
            }
        });

        Button Cancel = (Button) findViewById(R.id.Cancelbtn);
        Cancel.setBackgroundColor(Color.RED);
        Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                backredirect();
            }
        });
    }

    private void backredirect() {
        startActivity( new Intent(this, LoginActivity.class));
    }

    private void change() {
        String Text = Serveraddrtext.getText().toString();
        if ((!Text.equals(Serveraddr))&&(!Text.isEmpty())) {
            Serveraddr = Text;
            InfoMessage("Server Addr Updated", Color.BLUE, 32);
        }
        else{
            InfoMessage("No Changes", Color.BLUE, 32);
        }
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
    }

    @Override
    public void onBackPressed() {

        //remain here
        startActivity( new Intent(this, LoginActivity.class));
    }

    public void AutoDetect () {
        new autocinnection().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
    }


    public class autocinnection extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... urls) {

            String value = "";

            try {
                try {
                    //Open a random port to send the package

                    DatagramSocket c = new DatagramSocket();
                    c.setBroadcast(true);
                    byte[] sendData = "REQUEST_ARE_YOU_KHANA".getBytes();
                    //Try the 255.255.255.255 first
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);

                        c.send(sendPacket);

                        value += "\n" + getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)";
                        displayinfo0(value);

                    } catch (Exception e) {

                    }
                    // Broadcast the message over all the network interfaces

                    Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

                    while (interfaces.hasMoreElements()) {

                        NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                        if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                            continue; // Don't want to broadcast to the loopback interface
                        }

                        for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                            InetAddress broadcast = interfaceAddress.getBroadcast();

                            if (broadcast == null) {
                                continue;
                            }

                            // Send the broadcast package!
                            try {
                                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                                c.send(sendPacket);
                            } catch (Exception e) {

                            }
                            value += "\n" + getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName();
                            displayinfo0(value);
                        }
                    }
                    value += getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!";
                    displayinfo0(value);

                    //Wait for a response

                    byte[] recvBuf = new byte[15000];

                    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);

                    c.setSoTimeout(5000);
                    try{
                        c.receive(receivePacket);
                    }
                    catch (SocketTimeoutException e) {

                        displayinfo0("TimeOut");
                        c.close();
                        return null;
                    }


                    //We have a response

                    value += getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress();
                    displayinfo0(value);

                    String message = new String(receivePacket.getData()).trim();

                    if (message.equals("RESPONSE_IM_KHANA")) {
                        System.out.println(getClass().getName() + ">>> Found the Server: " + receivePacket.getAddress().getHostAddress());
                        Serveraddr = receivePacket.getAddress().getHostAddress()+":8080";
                        displayinfo();
                    }

                    c.close();

                } catch (IOException ex) {
                    displayinfo1();
                }


            } catch (Exception e) {
                displayinfo1();
                return null;
            } finally {

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    public void displayinfo()
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try {
                Serveraddrtext.setText(Serveraddr,TextView.BufferType.EDITABLE);

                infotext.setText(">>> Found the Server");
                }
                catch (Exception ex){

                }
            }
        });

    }

    public void displayinfo0(final String msg)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
          try {
              Serveraddrtext.setText(".....", TextView.BufferType.EDITABLE);

              infotext.setText(msg);
          }
          catch (Exception ex){

          }
            }
        });

    }

    public void displayinfo1()
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try
                {
                infotext.setText(">>> Server Not Found");
                }
                catch (Exception ex){

                }
            }
        });

    }

}

