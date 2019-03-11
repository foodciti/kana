package com.example.mande.newkhanapos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mande.newkhanapos.models.TicketItem;
import com.example.mande.newkhanapos.models.TicketItemCookingInstruction;
import com.example.mande.newkhanapos.models.TicketItemModifier;
import com.example.mande.newkhanapos.models.TicketItemModifierGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SplitActivity extends ArticleActivity {

    RecyclerView listdisplay, listdisplay1;
    int count =0;
    public static SplitActivity splitActivity;
    GridLayout gridLayout;
    Button payment,payment2;
    TextView TotalText, TotalText1;
    Button PreviewBack,SaldoButton, SaldoButton1;
    List<TicketItem> split_ticket_item = new ArrayList<>();
    List<String> countTemp = new ArrayList<>();
    List<String> ItemList, ItemList1;
    List<Double> PriceList, PriceList1;
    String status="0";
    String status1="0";
    int round=0;
    String what;
    boolean check=false;
    int pre_round=0;
    Double bill1, bill2;

    List<TicketItem> TicketItemList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splitpreview);

        TotalText = findViewById(R.id.euroid1);
        TotalText1 = findViewById(R.id.euroid2);
        payment = findViewById(R.id.payment);
        payment2 = findViewById(R.id.payment2);
        fullscreen();

        listdisplay = findViewById(R.id.listid2);
        listdisplay1 = findViewById(R.id.listid1);
        PreviewBack = (Button) findViewById(R.id.PreviewBack);
        PreviewBack.setText("Back");
        PreviewBack.setBackgroundResource(R.drawable.colorbutton);

        SaldoButton = (Button) findViewById(R.id.SaldoButton);
        SaldoButton.setText("Saldo");
        SaldoButton.setBackgroundResource(R.drawable.colorbutton);
        SaldoButton1 = findViewById(R.id.saldo);
        SaldoButton1.setText("Saldo");
        SaldoButton1.setBackgroundResource(R.drawable.colorbutton);
        grid = (GridLayout) findViewById(R.id.grid);
        grid.setVisibility(View.INVISIBLE);

        gridLayout = (GridLayout) findViewById(R.id.grid1);
        gridLayout.setVisibility(View.INVISIBLE);
        PreviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(status.equals("1") && status1.equals("1")){
                    onBackPressed();
                } else if(status.equals("0")){
                    InfoMessage("Payment pending from first Person", Color.RED,32);
                } else if(status1.equals("0")){
                    InfoMessage("Payment pending from Second Person", Color.RED,32);
                }

            }
        });
        ItemList = new ArrayList<>();
        ItemList1 = new ArrayList<>();
        PriceList = new ArrayList<>();
        PriceList1 = new ArrayList<>();

        bill1 = 0.0;
        bill2 = 0.0;

        SaldoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "bottom";
                SplitSaldoPushButton(what);
               // startActivity(new Intent(SplitActivity.this, TableActivity.class));
               // finish();
            }
        });

        SaldoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "top";
                SplitSaldoPushButton(what);
               // startActivity(new Intent(SplitActivity.this, TableActivity.class));
               // finish();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                check = true;
                PaymentactivationButton();
            }
        });

        payment2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                check = true;
                PaymentactivationButton1();
            }
        });
        previewDisplay();

    }

/*<!-----------------------------------------top_list------------------------------->*/


    public void PaymentactivationButton() {

        grid.setVisibility(View.VISIBLE);
        findViewById(R.id.listid2).setVisibility(View.INVISIBLE);
        PaymentButtonactivationForPayment();
    }


    private void PaymentButtonactivationForPayment() {

      /*  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                180,
                100
        );

        params.setMargins(5, 5, 5, 5);*/


            GridLayout.Spec row0 = GridLayout.spec(0);
            GridLayout.Spec row1 = GridLayout.spec(1);
            GridLayout.Spec row2 = GridLayout.spec(2);
            GridLayout.Spec row3 = GridLayout.spec(3);
            GridLayout.Spec row4 = GridLayout.spec(4);
            GridLayout.Spec row5 = GridLayout.spec(5);

            GridLayout.Spec col0 = GridLayout.spec(0);
            GridLayout.Spec col1 = GridLayout.spec(1);
            GridLayout.Spec col2 = GridLayout.spec(2);
            GridLayout.Spec col3 = GridLayout.spec(3);
            GridLayout.Spec col4 = GridLayout.spec(4);
            GridLayout.Spec col5 = GridLayout.spec(5);

            GridLayout.LayoutParams First = new GridLayout.LayoutParams(row0, col0);

            First.height = 100;
            First.width = 180;
            First.setMargins(5,5,5,5);

            PaymentTypes = new Button[4];

            PrintRadio = new Button(getApplicationContext());
            PrintRadio.setText("Print");
            PrintRadio.setTextColor(Color.BLACK);
            PrintRadio.setBackgroundResource(R.drawable.colorbuttonchanged);
            PrintRadio.setOnClickListener(RadioCheckedSpilt());
            PrintRadio.setId(1);

            grid.addView(PrintRadio,First);

        /*
        Official Radio button
         */
            GridLayout.LayoutParams First1 = new GridLayout.LayoutParams(row0, col1);

            First1.height = 100;
            First1.width = 180;
            First1.setMargins(5,5,5,5);

            OfficialRadio = new Button(getApplicationContext());
            OfficialRadio.setText("Official Reciept");
            OfficialRadio.setTextColor(Color.BLACK);
            OfficialRadio.setBackgroundResource(R.drawable.colorpopup);
            OfficialRadio.setOnClickListener(OfficialRadioSplitChecked());
            OfficialRadio.setId(0);

            grid.addView(OfficialRadio,First1);


            /* Bar with Receipt*/

            PaymentTypes[0] = new Button(this);
            PaymentTypes[0].setText("Bar");

            GridLayout.LayoutParams Second = new GridLayout.LayoutParams(row1, col0);

            Second.height = 100;
            Second.width = 180;
            Second.setMargins(5,5,5,5);

            grid.addView(PaymentTypes[0], Second);
            String value0 = "/ticket/save?official="+Officialapi+"&print="+Printapi+"&saveType=1&paymentType=0";
            PaymentTypes[0].setOnClickListener(BarOnFinaliseSplit(value0));
            PaymentTypes[0].setBackgroundResource(R.drawable.colorpopup);



            /*card*/
            PaymentTypes[1] = new Button(this);
            PaymentTypes[1].setText("Card");
            GridLayout.LayoutParams Third = new GridLayout.LayoutParams(row1, col1);

            Third.height = 100;
            Third.width = 180;
            Third.setMargins(5,5,5,5);

            grid.addView(PaymentTypes[1], Third);
            String value2  = "/ticket/save?official="+Officialapi+"&print="+Printapi+"&saveType=1&paymentType=1";
            PaymentTypes[1].setOnClickListener(BarOnFinaliseSplit(value2));
            PaymentTypes[1].setBackgroundResource(R.drawable.colorpopup);


            /*Online*/
            PaymentTypes[2] = new Button(this);
            PaymentTypes[2].setText("Online");
            GridLayout.LayoutParams Fourth = new GridLayout.LayoutParams(row1, col2);

            Fourth.height = 100;
            Fourth.width = 180;
            Fourth.setMargins(5,5,5,5);

            grid.addView(PaymentTypes[2], Fourth);
            String value4  = "/ticket/save?official="+Officialapi+"&print="+Printapi+"&saveType=1&paymentType=2";
            PaymentTypes[2].setOnClickListener(BarOnFinaliseSplit(value4));
            PaymentTypes[2].setBackgroundResource(R.drawable.colorpopup);

            /*Cancel*/
            PaymentTypes[3] = new Button(this);
            PaymentTypes[3].setText("Cancel");
            GridLayout.LayoutParams Fifth = new GridLayout.LayoutParams(row2, col0);

            Fifth.height = 100;
            Fifth.width = 180;
            Fifth.setMargins(5,5,5,5);

            grid.addView(PaymentTypes[3], Fifth);
            PaymentTypes[3].setOnClickListener(CancelonFinaliseSplit());
            PaymentTypes[3].setBackgroundResource(R.drawable.colorpopup);

    }

    View.OnClickListener BarOnFinaliseSplit(final String Value) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                grid.setVisibility(View.INVISIBLE);
               // Visibility();
                grid.removeAllViews();
                //getPreviousArticle();
                if(TicketItemList.size() < 1){
                    onBackPressed();
                }
                else {
                    new PostNetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + Value);
                    if(status_split_payment.equals("1")){
                        status="1";
                        listdisplay.setVisibility(View.INVISIBLE);
                        SaldoButton1.setVisibility(View.INVISIBLE);
                        payment.setVisibility(View.INVISIBLE);
                    } else  if(status_split_payment.equals("0")){
                        status="0";
                        listdisplay.setVisibility(View.VISIBLE);
                        SaldoButton1.setVisibility(View.VISIBLE);
                        payment.setVisibility(View.VISIBLE);
                    }
                }
            }

        };
    }

    View.OnClickListener CancelonFinaliseSplit(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                grid.setVisibility(View.INVISIBLE);
                grid.removeAllViews();
                findViewById(R.id.listid2).setVisibility(View.VISIBLE);
                //Visibility();

                //getPreviousArticle();
            }

        };
    }

    View.OnClickListener RadioCheckedSpilt() {
        return new View.OnClickListener() {
            public void onClick(View v) {

                if(PrintRadio.getId() == 1)
                {
                    PrintRadio.setBackgroundResource(R.drawable.colorpopup);
                    PrintRadio.setId(0);
                    Printapi = false;
                }
                else
                {
                    PrintRadio.setBackgroundResource(R.drawable.colorbuttonchanged);
                    PrintRadio.setId(1);
                    Printapi = true;
                }
            }

        };
    }

    View.OnClickListener OfficialRadioSplitChecked() {
        return new View.OnClickListener() {
            public void onClick(View v) {

                if(OfficialRadio.getId() == 1)
                {
                    OfficialRadio.setBackgroundResource(R.drawable.colorpopup);
                    OfficialRadio.setId(0);
                    Officialapi = false;
                }
                else
                {
                    OfficialRadio.setBackgroundResource(R.drawable.colorbuttonchanged);
                    OfficialRadio.setId(1);
                    Officialapi = true;
                }
            }

        };
    }

    /*-----------------------------------end--------------------------*/

    /* -------------------------------second Split List----------------------------*/

    public void PaymentactivationButton1() {

        gridLayout.setVisibility(View.VISIBLE);
        findViewById(R.id.listid1).setVisibility(View.INVISIBLE);
        PaymentButtonactivationForPayment1();
    }

    private void PaymentButtonactivationForPayment1() {

      /*  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                180,
                100
        );

        params.setMargins(5, 5, 5, 5);*/


        GridLayout.Spec row00 = GridLayout.spec(0);
        GridLayout.Spec row11 = GridLayout.spec(1);
        GridLayout.Spec row22 = GridLayout.spec(2);
        GridLayout.Spec row33 = GridLayout.spec(3);
        GridLayout.Spec row44 = GridLayout.spec(4);
        GridLayout.Spec row55 = GridLayout.spec(5);

        GridLayout.Spec col00 = GridLayout.spec(0);
        GridLayout.Spec col11 = GridLayout.spec(1);
        GridLayout.Spec col22 = GridLayout.spec(2);
        GridLayout.Spec col33 = GridLayout.spec(3);
        GridLayout.Spec col44 = GridLayout.spec(4);
        GridLayout.Spec col55 = GridLayout.spec(5);

        GridLayout.LayoutParams First = new GridLayout.LayoutParams(row00, col00);

        First.height = 100;
        First.width = 180;
        First.setMargins(5,5,5,5);

        PaymentTypes = new Button[4];

        PrintRadio = new Button(getApplicationContext());
        PrintRadio.setText("Print");
        PrintRadio.setTextColor(Color.BLACK);
        PrintRadio.setBackgroundResource(R.drawable.colorbuttonchanged);
        PrintRadio.setOnClickListener(RadioCheckedSpilt1());
        PrintRadio.setId(1);

        gridLayout.addView(PrintRadio,First);

        /*
        Official Radio button
         */
        GridLayout.LayoutParams First1 = new GridLayout.LayoutParams(row00, col11);

        First1.height = 100;
        First1.width = 180;
        First1.setMargins(5,5,5,5);

        OfficialRadio = new Button(getApplicationContext());
        OfficialRadio.setText("Official Reciept");
        OfficialRadio.setTextColor(Color.BLACK);
        OfficialRadio.setBackgroundResource(R.drawable.colorpopup);
        OfficialRadio.setOnClickListener(OfficialRadioSplitChecked1());
        OfficialRadio.setId(0);

        gridLayout.addView(OfficialRadio,First1);


        /* Bar with Receipt*/

        PaymentTypes[0] = new Button(this);
        PaymentTypes[0].setText("Bar");

        GridLayout.LayoutParams Second = new GridLayout.LayoutParams(row11, col00);

        Second.height = 100;
        Second.width = 180;
        Second.setMargins(5,5,5,5);

        gridLayout.addView(PaymentTypes[0], Second);
        String value0 = "/ticket/save?official="+Officialapi+"&print="+Printapi+"&saveType=1&paymentType=0";
        PaymentTypes[0].setOnClickListener(BarOnFinaliseSplit1(value0));
        PaymentTypes[0].setBackgroundResource(R.drawable.colorpopup);



        /*card*/
        PaymentTypes[1] = new Button(this);
        PaymentTypes[1].setText("Card");
        GridLayout.LayoutParams Third = new GridLayout.LayoutParams(row11, col11);

        Third.height = 100;
        Third.width = 180;
        Third.setMargins(5,5,5,5);

        gridLayout.addView(PaymentTypes[1], Third);
        String value2  = "/ticket/save?official="+Officialapi+"&print="+Printapi+"&saveType=1&paymentType=1";
        PaymentTypes[1].setOnClickListener(BarOnFinaliseSplit1(value2));
        PaymentTypes[1].setBackgroundResource(R.drawable.colorpopup);


        /*Online*/
        PaymentTypes[2] = new Button(this);
        PaymentTypes[2].setText("Online");
        GridLayout.LayoutParams Fourth = new GridLayout.LayoutParams(row11, col22);

        Fourth.height = 100;
        Fourth.width = 180;
        Fourth.setMargins(5,5,5,5);

        gridLayout.addView(PaymentTypes[2], Fourth);
        String value4  = "/ticket/save?official="+Officialapi+"&print="+Printapi+"&saveType=1&paymentType=2";
        PaymentTypes[2].setOnClickListener(BarOnFinaliseSplit1(value4));
        PaymentTypes[2].setBackgroundResource(R.drawable.colorpopup);

        /*Cancel*/
        PaymentTypes[3] = new Button(this);
        PaymentTypes[3].setText("Cancel");
        GridLayout.LayoutParams Fifth = new GridLayout.LayoutParams(row22, col00);

        Fifth.height = 100;
        Fifth.width = 180;
        Fifth.setMargins(5,5,5,5);

        gridLayout.addView(PaymentTypes[3], Fifth);
        PaymentTypes[3].setOnClickListener(CancelonFinaliseSplit1());
        PaymentTypes[3].setBackgroundResource(R.drawable.colorpopup);

    }

    View.OnClickListener BarOnFinaliseSplit1(final String Value) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                gridLayout.setVisibility(View.INVISIBLE);
                //Visibility();
                gridLayout.removeAllViews();
               // getPreviousArticle();
                if(split_ticket_item.size() < 1){

                    onBackPressed();
                }
                else {
                    new PostNetworkConnectionTaskForPaymentSplit().execute(ServeraddrHeader + Serveraddr + Value);

                }
            }

        };
    }

    View.OnClickListener CancelonFinaliseSplit1() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                gridLayout.setVisibility(View.INVISIBLE);
                gridLayout.removeAllViews();
                findViewById(R.id.listid1).setVisibility(View.VISIBLE);
               // Visibility();

                //getPreviousArticle();
            }

        };
    }

    View.OnClickListener RadioCheckedSpilt1() {
        return new View.OnClickListener() {
            public void onClick(View v) {

                if(PrintRadio.getId() == 1)
                {
                    PrintRadio.setBackgroundResource(R.drawable.colorpopup);
                    PrintRadio.setId(0);
                    Printapi = false;
                }
                else
                {
                    PrintRadio.setBackgroundResource(R.drawable.colorbuttonchanged);
                    PrintRadio.setId(1);
                    Printapi = true;
                }
            }

        };
    }

    View.OnClickListener OfficialRadioSplitChecked1() {
        return new View.OnClickListener() {
            public void onClick(View v) {

                if(OfficialRadio.getId() == 1)
                {
                    OfficialRadio.setBackgroundResource(R.drawable.colorpopup);
                    OfficialRadio.setId(0);
                    Officialapi = false;
                }
                else
                {
                    OfficialRadio.setBackgroundResource(R.drawable.colorbuttonchanged);
                    OfficialRadio.setId(1);
                    Officialapi = true;
                }
            }

        };
    }


    /*------------------------------------End----------------------------------------*/

    public void SplitSaldoPushButton(String what) {
        int splitCount =0;

       /* if(TicketItemList.size() > 0){
            status="1";
            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionSplit().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
            //postJsonBill(status) ;
        } else */

       if (what.equals("bottom")){
           if(split_ticket_item.size() > 0){

               ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
               new PostNetworkConnectionSplit1().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
               // postJsonBill(status) ;
           }
       } else if(what.equals("top")){

        if(TicketItemList.size() > 0){

            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionSplit().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
            //postJsonBill(status) ;
        }
       }


    }

    private void postDataJson(int splitCount, Double bill1, Double bill2) {

        for (int i =1; i <= splitCount; i++){
            if(i==1){

                ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
                new PostNetworkConnectionSplit().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);

            } else if(i==2){
                ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
                new PostNetworkConnectionSplit1().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);

            }
        }
    }

    public JSONObject postJsonSplit() {
        JSONObject newJsonObject = new JSONObject();
        JSONObject newJsonObjectcheck = new JSONObject();
        newJsonObjectcheck = Ticketjsonobject;

        JSONArray jsonarray = new JSONArray();
        try {
            if(newJsonObjectcheck==null)
            {

            }
            else {
                newJsonObject = newJsonObjectcheck;
                jsonarray = newJsonObject.getJSONArray("ticketItems");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject prevjsonobject1 = null;
        JSONObject newjsonobject1 = null;

        if(jsonarray.length() == 0)
        {
            for(int i = 0; i<split_ticket_item.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = split_ticket_item.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",split_ticket_item.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }

                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = split_ticket_item.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }

                try {
                    newjsonobject1.put("name", split_ticket_item.get(i).getName());
                    newjsonobject1.put("itemCount", split_ticket_item.get(i).getItemCount());
                    newjsonobject1.put("itemId", split_ticket_item.get(i).getItemId());
                    newjsonobject1.put("unitPrice", split_ticket_item.get(i).getUnitPrice());
                    newjsonobject1.put("id", null);
                    newjsonobject1.put("modifiedTime", null);
                    newjsonobject1.put("printedToKitchen", false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("tableNumber", Tableno);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",split_ticket_item.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarray.put(newjsonobject1);

                JSONObject UserJsonObject = new JSONObject();

                JSONArray tableArray = new JSONArray();
                JSONObject tablesObject = new JSONObject();

                JSONObject tObject = new JSONObject();

                try {
                    UserJsonObject.put("autoId", UserList.get(UserIdentification).getAutoId());
                    UserJsonObject.put("userId", UserList.get(UserIdentification).getUserId());
                    UserJsonObject.put("password", UserList.get(UserIdentification).getPassword());
                    UserJsonObject.put("firstName", UserList.get(UserIdentification).getFirstName());
                    UserJsonObject.put("lastName", UserList.get(UserIdentification).getLastName());
                    UserJsonObject.put("administrator", UserList.get(UserIdentification).isAdministrator());
                    UserJsonObject.put("manager", UserList.get(UserIdentification).isManager());

                    tablesObject.put("id", TableList.get(TableIdentification).getId());
                    tablesObject.put("number", TableList.get(TableIdentification).getNumber());
                    tablesObject.put("occupied", true);

                    tablesObject.put("tickettype", TableList.get(TableIdentification).getTickettype());
                    tableArray.put(tablesObject);


                    tObject.put("present", true);


                    newJsonObject.put("id", JSONObject.NULL);
                    newJsonObject.put("ticketid", JSONObject.NULL);
                    newJsonObject.put("drawerResetted", false);
                    newJsonObject.put("reOpened", false);
                    newJsonObject.put("ticketType", TableList.get(TableIdentification).getTickettype());
                    newJsonObject.put("split", true);
                    newJsonObject.put("owner", UserJsonObject);
                    newJsonObject.put("ticketItems", jsonarray);

                    newJsonObject.put("couponAndDiscounts", new JSONArray());
                    newJsonObject.put("tables", tableArray);
                    newJsonObject.put("tableNumbers", TableList.get(TableIdentification).getNumber());
                    newJsonObject.put("gutschein", 0);
                    newJsonObject.put("beverageCount", 2);
                    newJsonObject.put("totalAmount", round(bill1,2));
                    newJsonObject.put("type", TableList.get(TableIdentification).getTickettype());


                    newJsonObject.put("table", tObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        else if (jsonarray.length() != split_ticket_item.size()){

            for(int i = jsonarray.length(); i<split_ticket_item.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = split_ticket_item.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }
                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",split_ticket_item.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }


                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = split_ticket_item.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }


                try {
                    newjsonobject1.put("name",split_ticket_item.get(i).getName());
                    newjsonobject1.put("itemCount",split_ticket_item.get(i).getItemCount());
                    newjsonobject1.put("itemId",split_ticket_item.get(i).getItemId());
                    newjsonobject1.put("unitPrice",split_ticket_item.get(i).getUnitPrice());
                    newjsonobject1.put("printedToKitchen",false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",split_ticket_item.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonarray.put(newjsonobject1);

            }

        }


        return newJsonObject;

    }

    public JSONObject postJsonSplit1() {
        JSONObject newJsonObject = new JSONObject();
        JSONObject newJsonObjectcheck = new JSONObject();
        newJsonObjectcheck = Ticketjsonobject;

        JSONArray jsonarray = new JSONArray();
        try {
            if(newJsonObjectcheck==null)
            {

            }
            else {
                newJsonObject = newJsonObjectcheck;
                jsonarray = newJsonObject.getJSONArray("ticketItems");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject prevjsonobject1 = null;
        JSONObject newjsonobject1 = null;

        if(jsonarray.length() == 0)
        {
            for(int i = 0; i<TicketItemList.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = TicketItemList.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",TicketItemList.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }

                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = TicketItemList.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }

                try {
                    newjsonobject1.put("name", TicketItemList.get(i).getName());
                    newjsonobject1.put("itemCount", TicketItemList.get(i).getItemCount());
                    newjsonobject1.put("itemId", TicketItemList.get(i).getItemId());
                    newjsonobject1.put("unitPrice", TicketItemList.get(i).getUnitPrice());
                    newjsonobject1.put("id", null);
                    newjsonobject1.put("modifiedTime", null);
                    newjsonobject1.put("printedToKitchen", false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("tableNumber", Tableno);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",TicketItemList.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarray.put(newjsonobject1);

                JSONObject UserJsonObject = new JSONObject();

                JSONArray tableArray = new JSONArray();
                JSONObject tablesObject = new JSONObject();

                JSONObject tObject = new JSONObject();

                try {
                    UserJsonObject.put("autoId", UserList.get(UserIdentification).getAutoId());
                    UserJsonObject.put("userId", UserList.get(UserIdentification).getUserId());
                    UserJsonObject.put("password", UserList.get(UserIdentification).getPassword());
                    UserJsonObject.put("firstName", UserList.get(UserIdentification).getFirstName());
                    UserJsonObject.put("lastName", UserList.get(UserIdentification).getLastName());
                    UserJsonObject.put("administrator", UserList.get(UserIdentification).isAdministrator());
                    UserJsonObject.put("manager", UserList.get(UserIdentification).isManager());

                    tablesObject.put("id", TableList.get(TableIdentification).getId());
                    tablesObject.put("number", TableList.get(TableIdentification).getNumber());
                    tablesObject.put("occupied", true);

                    tablesObject.put("tickettype", TableList.get(TableIdentification).getTickettype());
                    tableArray.put(tablesObject);


                    tObject.put("present", true);


                    newJsonObject.put("id", JSONObject.NULL);
                    newJsonObject.put("ticketid", JSONObject.NULL);
                    newJsonObject.put("drawerResetted", false);
                    newJsonObject.put("reOpened", false);
                    newJsonObject.put("ticketType", TableList.get(TableIdentification).getTickettype());
                    newJsonObject.put("split", true);
                    newJsonObject.put("owner", UserJsonObject);
                    newJsonObject.put("ticketItems", jsonarray);

                    newJsonObject.put("couponAndDiscounts", new JSONArray());
                    newJsonObject.put("tables", tableArray);
                    newJsonObject.put("tableNumbers", TableList.get(TableIdentification).getNumber());
                    newJsonObject.put("gutschein", 0);
                    newJsonObject.put("beverageCount", 2);

                    newJsonObject.put("totalAmount", round(bill2,2));
                    newJsonObject.put("type", TableList.get(TableIdentification).getTickettype());


                    newJsonObject.put("table", tObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        else if (jsonarray.length() != TicketItemList.size()){

            for(int i = jsonarray.length(); i<TicketItemList.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = TicketItemList.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }
                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",TicketItemList.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }


                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = TicketItemList.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }


                try {
                    newjsonobject1.put("name",TicketItemList.get(i).getName());
                    newjsonobject1.put("itemCount",TicketItemList.get(i).getItemCount());
                    newjsonobject1.put("itemId",TicketItemList.get(i).getItemId());
                    newjsonobject1.put("unitPrice",TicketItemList.get(i).getUnitPrice());
                    newjsonobject1.put("printedToKitchen",false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",TicketItemList.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonarray.put(newjsonobject1);

            }

        }


        return newJsonObject;

    }

    public void previewDisplay() {

        Total = 0;
        /*if(TicketItemList.size()>0){
            try{
                TicketItemList1.add((TicketItem) TicketItemList);
            } catch (ClassCastException e){
                e.printStackTrace();
            }

        }*/


        for (int i= 0; i < TicketItemList.size(); i++){

            String Description = "";
            String Zutaten = "";

            if(TicketItemList.get(i).getCookingInstructions() != null)
            {

                for( int j = 0; j< TicketItemList.get(i).getCookingInstructions().size(); j++)
                {
                    Description = Description + "\n * " + TicketItemList.get(i).getCookingInstructions().get(j).getDescription();
                }

            }

            if(TicketItemList.get(i).getTicketItemModifierGroups() != null)
            {
                for( int j = 0; j< TicketItemList.get(i).getTicketItemModifierGroups().size(); j++)
                {

                    for(int k = 0; k < TicketItemList.get(i).getTicketItemModifierGroups().get(j).getTicketItemModifiers().size(); k++)
                    {
                        Zutaten = Zutaten + "\n + " + TicketItemList.get(i).getTicketItemModifierGroups().get(j).getTicketItemModifiers().get(k).getName()
                                + " " + TicketItemList.get(i).getTicketItemModifierGroups().get(j).getTicketItemModifiers().get(k).getExtraUnitPrice();
                        Total = Total + TicketItemList.get(i).getTicketItemModifierGroups().get(j).getTicketItemModifiers().get(k).getExtraUnitPrice();
                    }
                }
            }


            countTemp.add(String.valueOf(TicketItemList.get(i).getItemCount()));
            ItemList.add( TicketItemList.get(i).getItemCount() + " X " + TicketItemList.get(i).getItemId() + " "+TicketItemList.get(i).getName() +  "   "+(TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount()) +"€"
                    + Zutaten);
            PriceList.add(TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount());
            Total = Total + (TicketItemList.get(i).getUnitPrice() *TicketItemList.get(i).getItemCount());
            payment2.setVisibility(View.INVISIBLE);
            SaldoButton.setVisibility(View.INVISIBLE);
        }


        AdapterList LAdapter = new AdapterList(this,TicketItemList);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        listdisplay.setLayoutManager(mLayoutManager1);
        listdisplay.setItemAnimator(new DefaultItemAnimator());
        listdisplay.setAdapter(LAdapter);
        TotalText.setText("€ " + round(Total, 2));
    }

    public void  previewDisplay1() {
       /* LAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList1);
        listdisplay1.setAdapter(LAdapter1);
        bill2 = pricecalculation(PriceList1);
        TotalText1.setText("€ " + round(bill2, 2));*/

        AdapterList1 adapterList1 = new AdapterList1(this,split_ticket_item);
        if(split_ticket_item.size()> 0){
            SaldoButton.setVisibility(View.VISIBLE);
            payment2.setVisibility(View.VISIBLE);
        } else {
            SaldoButton.setVisibility(View.INVISIBLE);
            payment2.setVisibility(View.INVISIBLE);
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listdisplay1.setLayoutManager(mLayoutManager);
        listdisplay1.setItemAnimator(new DefaultItemAnimator());
        listdisplay1.setAdapter(adapterList1);
        adapterList1.notifyDataSetChanged();
        bill2 = pricecalculation(split_ticket_item);
        TotalText1.setText("€ " + round(bill2, 2));

        AdapterList LAdapter = new AdapterList(this,TicketItemList);
        if(TicketItemList.size()> 0){
            SaldoButton1.setVisibility(View.VISIBLE);
            payment.setVisibility(View.VISIBLE);
        } else {
            SaldoButton1.setVisibility(View.INVISIBLE);
            payment.setVisibility(View.INVISIBLE);
        }
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        listdisplay.setLayoutManager(mLayoutManager1);
        listdisplay.setItemAnimator(new DefaultItemAnimator());
        listdisplay.setAdapter(LAdapter);
        LAdapter.notifyDataSetChanged();
        bill1 = pricecalculation(TicketItemList);
        TotalText.setText("€ " + round(bill1, 2));

    }

    public Double pricecalculation(List<TicketItem> split_ticket_item){

        Double value = 0.0;

        for (int i = 0; i < split_ticket_item.size(); i ++)
        {
            value += Double.parseDouble(String.valueOf(split_ticket_item.get(i).getItemCount() * split_ticket_item.get(i).getUnitPrice()));
        }

        return value;
    }
   /* public Double pricecalculation(List<Double> list)
    {
        Double value = 0.0;

        for (int i = 0; i < list.size(); i ++)
        {
            value += list.get(i);
        }

        return value;
    }*/

    public class PostNetworkConnectionSplit extends AsyncTask<String,String,String> {

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
                jsonParam = postJsonSplit();
                Log.d("Error", jsonParam.toString());

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
                    status="1";
                    payment.setVisibility(View.INVISIBLE);
                    SaldoButton1.setVisibility(View.INVISIBLE);
                    listdisplay.setVisibility(View.INVISIBLE);
                } else {
                    status="0";
                    payment.setVisibility(View.VISIBLE);
                    SaldoButton1.setVisibility(View.VISIBLE);
                    listdisplay.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class PostNetworkConnectionSplit1 extends AsyncTask<String,String,String> {

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
                jsonParam = postJsonSplit1();
                Log.d("Error", jsonParam.toString());

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
                    status1="1";
                    payment2.setVisibility(View.INVISIBLE);
                    SaldoButton.setVisibility(View.INVISIBLE);
                    listdisplay1.setVisibility(View.INVISIBLE);
                } else {
                    status1="0";
                    payment2.setVisibility(View.VISIBLE);
                    SaldoButton.setVisibility(View.VISIBLE);
                    listdisplay1.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //PostNetworkConnectionTaskForPaymentSplit
    public class PostNetworkConnectionTaskForPaymentSplit extends AsyncTask<String,String,String>{

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
                jsonParam = postJsonForSplit();

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
                    status1="1";
                    listdisplay1.setVisibility(View.INVISIBLE);
                    payment2.setVisibility(View.INVISIBLE);
                    SaldoButton.setVisibility(View.INVISIBLE);
                } else {
                    status1 = "0";
                    listdisplay1.setVisibility(View.VISIBLE);
                    payment2.setVisibility(View.VISIBLE);
                    SaldoButton.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
        List<TicketItem> ticket_list = new ArrayList<>();
        Context context;

        public AdapterList(SplitActivity splitActivity, List<TicketItem> ticketItemList) {
            this.context = splitActivity;
            this.ticket_list = ticketItemList;
        }

        @NonNull
        @Override
        public AdapterList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ticket_item_split, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final AdapterList.ViewHolder holder, final int position) {
            TicketItem ticketItemList1 = ticket_list.get(position);
            holder.itemCount1.setText(String.valueOf(ticketItemList1.getItemCount()));
            holder.itemId.setText(String.valueOf(ticketItemList1.getItemId()));
            holder.itemName.setText(ticketItemList1.getName());
            holder.price.setText(String.valueOf(ticketItemList1.getItemCount() *ticketItemList1.getUnitPrice()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!check){
                        int itemCount =  TicketItemList.get(position).getItemCount();
                        if(TicketItemList.get(position).getItemCount()==1){

                            TicketItem ticketItem = new TicketItem();
                            ticketItem.setItemCount(TicketItemList.get(position).getItemCount());
                            ticketItem.setItemId(TicketItemList.get(position).getItemId());
                            ticketItem.setName(TicketItemList.get(position).getName());
                            ticketItem.setUnitPrice(TicketItemList.get(position).getUnitPrice());
                            split_ticket_item.add(ticketItem);
                            TicketItemList.remove(position);
                            previewDisplay1();
                        } else {
                            round = round+1;

                            if(round <= TicketItemList.get(position).getItemCount() ){

                                holder.itemCount1.setText(String.valueOf(TicketItemList.get(position).getItemCount()-round));

                                TicketItem ticketItem = new TicketItem();
                                ticketItem.setItemCount(round);
                                ticketItem.setItemId(TicketItemList.get(position).getItemId());
                                ticketItem.setName(TicketItemList.get(position).getName());
                                ticketItem.setUnitPrice(TicketItemList.get(position).getUnitPrice() * round);
                                split_ticket_item.add(ticketItem);
                                TicketItemList.get(position).setItemCount(itemCount -= 1);
                                previewDisplay1();
                            } else {
                                round=0;
                            }

                        }
                    } else {

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return ticket_list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemCount1, itemId,itemName, price;
            public ViewHolder(View itemView) {
                super(itemView);
                itemCount1 = itemView.findViewById(R.id.text_item_count1);
                itemId = itemView.findViewById(R.id.ticket_item_id1);
                itemName = itemView.findViewById(R.id.ticket_item_name1);
                price =itemView.findViewById(R.id.item_price1);
            }
        }


    }

    class AdapterList1 extends RecyclerView.Adapter<AdapterList1.ViewHolder1> {
        List<TicketItem> split_list;
        Context context1;

        public AdapterList1(SplitActivity splitActivity,List<TicketItem> split_ticket_item) {
            this.context1 = splitActivity;
            this.split_list = split_ticket_item;

        }

        @NonNull
        @Override
        public AdapterList1.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ticket_item_split_,parent, false );
            return new ViewHolder1(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final AdapterList1.ViewHolder1 holder, final int position) {
            TicketItem ticketItemList = split_list.get(position);
            holder.itemCount.setText(String.valueOf(ticketItemList.getItemCount()));
            holder.itemId.setText(String.valueOf(ticketItemList.getItemId()));
            holder.itemName.setText(ticketItemList.getName());
            holder.price.setText(String.valueOf(ticketItemList.getItemCount() *ticketItemList.getUnitPrice()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!check){
                        int itemCount = split_ticket_item.get(position).getItemCount();

                        if(split_ticket_item.get(position).getItemCount()==1){

                            TicketItem ticketItem = new TicketItem();
                            ticketItem.setItemCount(split_ticket_item.get(position).getItemCount());
                            ticketItem.setItemId(split_ticket_item.get(position).getItemId());
                            ticketItem.setName(split_ticket_item.get(position).getName());
                            ticketItem.setUnitPrice(split_ticket_item.get(position).getUnitPrice());
                            TicketItemList.add(ticketItem);
                            split_ticket_item.remove(position);
                            previewDisplay1();
                        } else {

                            pre_round = pre_round+1;

                            if(pre_round <= split_ticket_item.get(position).getItemCount() ){

                                holder.itemCount.setText(String.valueOf(split_ticket_item.get(position).getItemCount()-pre_round));
                                TicketItemList=new ArrayList<>();
                                TicketItem ticketItem = new TicketItem();
                                ticketItem.setItemCount(pre_round);
                                ticketItem.setItemId(split_ticket_item.get(position).getItemId());
                                ticketItem.setName(split_ticket_item.get(position).getName());
                                ticketItem.setUnitPrice(split_ticket_item.get(position).getUnitPrice() * pre_round);
                                TicketItemList.add(ticketItem);
                                split_ticket_item.get(position).setItemCount(itemCount -= 1);
                                previewDisplay1();
                            } else {
                                pre_round=0;
                            }

                        }
                    } else {

                    }



                    /*ItemList.add(ItemList1.get(position));
                    PriceList.add(PriceList1.get(position));
                    TicketItemList1.add(split_ticket_item.get(position));
                    ItemList1.remove(position);
                    PriceList1.remove(position);
                    split_ticket_item.remove(position);*/
                   // previewDisplay1(round);
                }
            });

        }

        @Override
        public int getItemCount() {
            return split_list.size();
        }

        public class ViewHolder1  extends RecyclerView.ViewHolder {
            TextView itemCount, itemId,itemName, price;
            public ViewHolder1(View itemView) {
                super(itemView);
                itemCount = itemView.findViewById(R.id.text_item_count);
                itemId = itemView.findViewById(R.id.ticket_item_id);
                itemName = itemView.findViewById(R.id.ticket_item_name);
                price = itemView.findViewById(R.id.item_price);

            }
        }
    }

    public JSONObject postJson() {
        JSONObject newJsonObject = new JSONObject();
        JSONObject newJsonObjectcheck = new JSONObject();
        newJsonObjectcheck = Ticketjsonobject;

        JSONArray jsonarray = new JSONArray();
        try {
            if(newJsonObjectcheck==null)
            {

            }
            else {
                newJsonObject = newJsonObjectcheck;
                jsonarray = newJsonObject.getJSONArray("ticketItems");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject prevjsonobject1 = null;
        JSONObject newjsonobject1 = null;

        if(jsonarray.length() == 0)
        {
            for(int i = 0; i<TicketItemList.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = TicketItemList.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",TicketItemList.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }

                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = TicketItemList.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }

                try {
                    newjsonobject1.put("name", TicketItemList.get(i).getName());
                    newjsonobject1.put("itemCount", TicketItemList.get(i).getItemCount());
                    newjsonobject1.put("itemId", TicketItemList.get(i).getItemId());
                    newjsonobject1.put("unitPrice", TicketItemList.get(i).getUnitPrice());
                    newjsonobject1.put("id", null);
                    newjsonobject1.put("modifiedTime", null);
                    newjsonobject1.put("printedToKitchen", false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("tableNumber", Tableno);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",TicketItemList.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarray.put(newjsonobject1);

                JSONObject UserJsonObject = new JSONObject();

                JSONArray tableArray = new JSONArray();
                JSONObject tablesObject = new JSONObject();

                JSONObject tObject = new JSONObject();

                try {
                    UserJsonObject.put("autoId", UserList.get(UserIdentification).getAutoId());
                    UserJsonObject.put("userId", UserList.get(UserIdentification).getUserId());
                    UserJsonObject.put("password", UserList.get(UserIdentification).getPassword());
                    UserJsonObject.put("firstName", UserList.get(UserIdentification).getFirstName());
                    UserJsonObject.put("lastName", UserList.get(UserIdentification).getLastName());
                    UserJsonObject.put("administrator", UserList.get(UserIdentification).isAdministrator());
                    UserJsonObject.put("manager", UserList.get(UserIdentification).isManager());

                    tablesObject.put("id", TableList.get(TableIdentification).getId());
                    tablesObject.put("number", TableList.get(TableIdentification).getNumber());
                    tablesObject.put("occupied", true);
                    tablesObject.put("tickettype", TableList.get(TableIdentification).getTickettype());
                    tableArray.put(tablesObject);


                    tObject.put("present", true);


                    newJsonObject.put("id", JSONObject.NULL);
                    newJsonObject.put("ticketid", JSONObject.NULL);
                    newJsonObject.put("drawerResetted", false);
                    newJsonObject.put("reOpened", false);
                    newJsonObject.put("ticketType", TableList.get(TableIdentification).getTickettype());
                    newJsonObject.put("split", true);
                    newJsonObject.put("owner", UserJsonObject);
                    newJsonObject.put("ticketItems", jsonarray);

                    newJsonObject.put("couponAndDiscounts", new JSONArray());
                    newJsonObject.put("tables", tableArray);

                    newJsonObject.put("deletedItems", new JSONArray());
                    newJsonObject.put("tableNumbers", TableList.get(TableIdentification).getNumber());
                    newJsonObject.put("gutschein", 0);
                    newJsonObject.put("beverageCount", 2);
                    newJsonObject.put("type", TableList.get(TableIdentification).getTickettype());


                    newJsonObject.put("table", tObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        else if (jsonarray.length() != TicketItemList.size()){

            for(int i = jsonarray.length(); i<TicketItemList.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = TicketItemList.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }
                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",TicketItemList.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }


                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = TicketItemList.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",TicketItemList.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }


                try {
                    newjsonobject1.put("name",TicketItemList.get(i).getName());
                    newjsonobject1.put("itemCount",TicketItemList.get(i).getItemCount());
                    newjsonobject1.put("itemId",TicketItemList.get(i).getItemId());
                    newjsonobject1.put("unitPrice",TicketItemList.get(i).getUnitPrice());
                    newjsonobject1.put("printedToKitchen",false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",TicketItemList.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonarray.put(newjsonobject1);

            }

        }


        return newJsonObject;

    }

    public JSONObject postJsonForSplit() {
        JSONObject newJsonObject = new JSONObject();
        JSONObject newJsonObjectcheck = new JSONObject();
        newJsonObjectcheck = Ticketjsonobject;

        JSONArray jsonarray = new JSONArray();
        try {
            if(newJsonObjectcheck==null)
            {

            }
            else {
                newJsonObject = newJsonObjectcheck;
                jsonarray = newJsonObject.getJSONArray("ticketItems");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject prevjsonobject1 = null;
        JSONObject newjsonobject1 = null;

        if(jsonarray.length() == 0)
        {
            for(int i = 0; i<split_ticket_item.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = split_ticket_item.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",split_ticket_item.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }

                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = split_ticket_item.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }

                try {
                    newjsonobject1.put("name", split_ticket_item.get(i).getName());
                    newjsonobject1.put("itemCount", split_ticket_item.get(i).getItemCount());
                    newjsonobject1.put("itemId", split_ticket_item.get(i).getItemId());
                    newjsonobject1.put("unitPrice", split_ticket_item.get(i).getUnitPrice());
                    newjsonobject1.put("id", null);
                    newjsonobject1.put("modifiedTime", null);
                    newjsonobject1.put("printedToKitchen", false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("tableNumber", Tableno);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",split_ticket_item.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarray.put(newjsonobject1);

                JSONObject UserJsonObject = new JSONObject();

                JSONArray tableArray = new JSONArray();
                JSONObject tablesObject = new JSONObject();

                JSONObject tObject = new JSONObject();

                try {
                    UserJsonObject.put("autoId", UserList.get(UserIdentification).getAutoId());
                    UserJsonObject.put("userId", UserList.get(UserIdentification).getUserId());
                    UserJsonObject.put("password", UserList.get(UserIdentification).getPassword());
                    UserJsonObject.put("firstName", UserList.get(UserIdentification).getFirstName());
                    UserJsonObject.put("lastName", UserList.get(UserIdentification).getLastName());
                    UserJsonObject.put("administrator", UserList.get(UserIdentification).isAdministrator());
                    UserJsonObject.put("manager", UserList.get(UserIdentification).isManager());

                    tablesObject.put("id", TableList.get(TableIdentification).getId());
                    tablesObject.put("number", TableList.get(TableIdentification).getNumber());
                    tablesObject.put("occupied", true);
                    tablesObject.put("tickettype", TableList.get(TableIdentification).getTickettype());
                    tableArray.put(tablesObject);


                    tObject.put("present", true);


                    newJsonObject.put("id", JSONObject.NULL);
                    newJsonObject.put("ticketid", JSONObject.NULL);
                    newJsonObject.put("drawerResetted", false);
                    newJsonObject.put("reOpened", false);
                    newJsonObject.put("ticketType", TableList.get(TableIdentification).getTickettype());
                    newJsonObject.put("split", true);
                    newJsonObject.put("owner", UserJsonObject);
                    newJsonObject.put("ticketItems", jsonarray);

                    newJsonObject.put("couponAndDiscounts", new JSONArray());
                    newJsonObject.put("tables", tableArray);

                    newJsonObject.put("deletedItems", new JSONArray());
                    newJsonObject.put("tableNumbers", TableList.get(TableIdentification).getNumber());
                    newJsonObject.put("gutschein", 0);
                    newJsonObject.put("beverageCount", 2);
                    newJsonObject.put("type", TableList.get(TableIdentification).getTickettype());


                    newJsonObject.put("table", tObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        else if (jsonarray.length() != split_ticket_item.size()){

            for(int i = jsonarray.length(); i<split_ticket_item.size();i++) {

                TicketItem ticketItem = new TicketItem();

                try {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(ticketItem);
                    prevjsonobject1 = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newjsonobject1 = prevjsonobject1;
                TicketItemCookingInstruction TicketItemCookingInstructionobject = new TicketItemCookingInstruction();
                JSONArray cookingJsonarray = new JSONArray();

                int size = 0;
                try{
                    size = split_ticket_item.get(i).getCookingInstructions().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }
                for (int j = 0; j < size ;j++) {

                    JSONObject cookingobject = null;
                    try {
                        Gson gsonobj = new Gson();
                        String jsonStringobj = gsonobj.toJson(TicketItemCookingInstructionobject);
                        cookingobject = new JSONObject(jsonStringobj);
                        cookingobject.put("description",split_ticket_item.get(i).getCookingInstructions().get(j).getDescription());
                        cookingobject.put("printedToKitchen",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cookingJsonarray.put(cookingobject);
                }


                TicketItemModifierGroup TicketItemModifierGroupObject = new  TicketItemModifierGroup();
                JSONArray TicketItemModifierGroupArray = new JSONArray();

                TicketItemModifier TicketItemModifierObject = new TicketItemModifier();
                JSONArray TicketItemModifierArray = new JSONArray();

                int zusize = 0;
                int zuModifiersize = 0;

                try {zusize = split_ticket_item.get(i).getTicketItemModifierGroups().size();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }

                for ( int k = 0; k < zusize; k++)
                {
                    JSONObject ModifierGroupobject = null;
                    Gson gsonobj1 = new Gson();
                    String jsonStringobj1 = gsonobj1.toJson(TicketItemModifierGroupObject);


                    try{zuModifiersize = split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().size();
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    for (int j = 0; j < zuModifiersize ;j++) {

                        JSONObject Modifierobject = null;
                        try {
                            Gson gsonobj = new Gson();
                            String jsonStringobj = gsonobj.toJson(TicketItemModifierObject);
                            Modifierobject = new JSONObject(jsonStringobj);
                            Modifierobject.put("itemId",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getItemId());
                            Modifierobject.put("itemCount",1);
                            Modifierobject.put("name",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getName());
                            Modifierobject.put("extraUnitPrice",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getExtraUnitPrice());
                            Modifierobject.put("modifierType",split_ticket_item.get(i).getTicketItemModifierGroups().get(k).getTicketItemModifiers().get(j).getModifierType());
                            Modifierobject.put("shouldPrintToKitchen",true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TicketItemModifierArray.put(Modifierobject);
                    }
                    try {
                        ModifierGroupobject = new JSONObject(jsonStringobj1);
                        ModifierGroupobject.put("ticketItemModifiers",TicketItemModifierArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TicketItemModifierGroupArray.put(ModifierGroupobject);
                }


                try {
                    newjsonobject1.put("name",split_ticket_item.get(i).getName());
                    newjsonobject1.put("itemCount",split_ticket_item.get(i).getItemCount());
                    newjsonobject1.put("itemId",split_ticket_item.get(i).getItemId());
                    newjsonobject1.put("unitPrice",split_ticket_item.get(i).getUnitPrice());
                    newjsonobject1.put("printedToKitchen",false);
                    newjsonobject1.put("shouldPrintToKitchen",true);
                    newjsonobject1.put("cookingInstructions",cookingJsonarray);
                    newjsonobject1.put("printorder",split_ticket_item.get(i).getPrintorder());
                    newjsonobject1.put("ticketItemModifierGroups",TicketItemModifierGroupArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonarray.put(newjsonobject1);

            }

        }


        return newJsonObject;

    }


}
