package com.example.mande.newkhanapos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.SpellCheckerInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mande.newkhanapos.Services.MyAppllication;
import com.example.mande.newkhanapos.Services.NetworkChangeReceiver;
import com.example.mande.newkhanapos.Services.SpecialNoteDialog;
import com.example.mande.newkhanapos.models.Article;
import com.example.mande.newkhanapos.models.MenuItemModifierGroup;
import com.example.mande.newkhanapos.models.MenuModifier;
import com.example.mande.newkhanapos.models.MenuModifierGroup;
import com.example.mande.newkhanapos.models.Table;
import com.example.mande.newkhanapos.models.Ticket;
import com.example.mande.newkhanapos.models.TicketItem;
import com.example.mande.newkhanapos.models.TicketItemCookingInstruction;
import com.example.mande.newkhanapos.models.TicketItemModifier;
import com.example.mande.newkhanapos.models.TicketItemModifierGroup;
import com.example.mande.newkhanapos.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleActivity extends MainActivity implements NetworkChangeReceiver.ConnectivityReceiverListener,
        SpecialNoteDialog.SpecialNodeListener{

    double Total ;
    TextView TotalText;
    TextView TableDisplay;
    ListView ItemListView;
    ListAdapter LAdapter;
    int newlistpos;
    static int iselectedItem;
    Boolean bselectedItem = false;
   // SpecialNoteDialog addition;
    JSONObject Ticketjsonobject = null;
    JSONArray fetchedjsonArray = null;
   // SpecialNoteDialog.SpecialNodeListener mlistenere;
    Button Saldo,Notiz,Gaenge,Zutaten,PrintButton, HomeButton, PaymentButton, SplitButton;

    GridLayout grid;
    ConstraintLayout basenumber;

    Button[] myButton, GaengeButton, ZutatenButton, PaymentTypes;
    List<String> InstrucList = new ArrayList<>();
    List<String> ZutatenList = new ArrayList<>();

    String detectitem = "";
    int gaengepressed = 0;
    List<String> detectList = new ArrayList<>();
    List<Integer> ZutList = new ArrayList<>();
    List<Integer> ZutClick = new ArrayList<>();
    int[] numclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        fullscreen();

        KeyStringDisplay = findViewById(R.id.tableview);
        TotalText = findViewById(R.id.totalview);
        TotalText.setText("Total " + round(Total, 2));

        TableDisplay = findViewById(R.id.tabledisplay);
        TableDisplay.setText("Table " + Tableno);

        Saldo = (Button) findViewById(R.id.SaldoButton);
        Saldo.setText("Saldo");
        Saldo.setBackgroundResource(R.drawable.colorbutton);

        Notiz = (Button) findViewById(R.id.NotizButton);
        Notiz.setText("Notiz");
        Notiz.setBackgroundResource(R.drawable.colorbutton);

        Gaenge = (Button) findViewById(R.id.GaengeButton);
        Gaenge.setText("Gaenge");
        Gaenge.setBackgroundResource(R.drawable.colorbutton);

        Zutaten = (Button) findViewById(R.id.ZutatenButton);
        Zutaten.setText("Zutaten");
        Zutaten.setBackgroundResource(R.drawable.colorbutton);

        PrintButton = (Button) findViewById(R.id.PrintButton);
        PrintButton.setText("Preview");
        PrintButton.setBackgroundResource(R.drawable.colorbutton);

        HomeButton = (Button) findViewById(R.id.HomeButtonId);
        HomeButton.setText("Home");
        HomeButton.setBackgroundResource(R.drawable.colorbutton);

        PaymentButton = (Button) findViewById(R.id.paymentbutton);
        PaymentButton.setText("Payment");
        PaymentButton.setBackgroundResource(R.drawable.colorbutton);

        SplitButton = (Button) findViewById(R.id.SplitButton);
        SplitButton.setText("Split");
        SplitButton.setBackgroundResource(R.drawable.colorbutton);

        grid = (GridLayout) findViewById(R.id.grid);
        grid.setVisibility(View.INVISIBLE);

        basenumber = (ConstraintLayout)findViewById(R.id.basenumbers);


        KeyMap();
        numKey[11].setText("Art");
        KeyStringDisplay.setText("");
        ItemListView = findViewById(R.id.listview);

        ServeraddrApi = "/ticket/queryOpenTicketByTableNumber?tableNumber=" + Tableno;
        new NetworkConnectionTaskTable().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);


        /*
         * Long touch listener
         */
        ItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                TicketItemList.remove(pos);

                getPreviousArticle();
                return true;
            }
        });


        ItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long id) {

                iselectedItem = position;
                int color = Color.TRANSPARENT;
                Drawable background = arg1.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();

                if(color == Color.GREEN)
                {
                    arg1.setBackgroundColor(Color.TRANSPARENT);
                    bselectedItem = false;

                }
                else{
                    arg1.setBackgroundColor(Color.GREEN);
                    bselectedItem = true;
                }

                checkmultiplecursor();

            }
        });


        Saldo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SaldoPushButton();
            }
        });

        Notiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cookingInstruction();
            }
        });

        Gaenge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GaengeActivation();
            }
        });

        Zutaten.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ZutatenActivation();
            }
        });

        PrintButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                printactivation();
            }
        });

        HomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });

        PaymentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Paymentactivation();
            }
        });

        SplitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Splitactivation();
            }
        });


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
        MyAppllication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void DoAction() {

        Articleno = PressedKey;
        if (CurrentPressedKey.equals("Art")){

            int Error = 0;
            for(int i = 0; i <= ArticleList.size(); i++)
            {
                if(i==ArticleList.size())
                {
                    Error = 1;
                }
                else if(PressedKey.equals(ArticleList.get(i).getItemId()+"Art"))
                {
                    PressedKey = "";
                    Articleno = "";
                    KeyStringDisplay.setText(Articleno);

                    TicketItem ticketItem = new TicketItem();
                    ticketItem.setUnitPrice(ArticleList.get(i).getPrice());
                    ticketItem.setName(ArticleList.get(i).getName());
                    ticketItem.setItemId(Integer.parseInt(ArticleList.get(i).getItemId()));

                    int checkcountvalue = checkcount(Integer.parseInt(ArticleList.get(i).getItemId()));

                    if (checkcountvalue == 1) {
                        ticketItem.setItemCount(checkcountvalue);
                        TicketItemList.add(ticketItem);
                    }

                    getPreviousArticle();

                    break;
                }
            }

            // This will happen only if password with all users does not match
            if (Error ==1) {
                InfoMessage("Item does not Exist", Color.RED, 32);
                PressedKey = "";
                Articleno = "";
                KeyStringDisplay.setText(Articleno);
            }

        }
        else{
            KeyStringDisplay.setText(Articleno);
        }
    }

    @Override
    public void getJsonTable(StringBuffer buffer)
    {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Ticketjsonobject = jsonArray.getJSONObject(0);

            JSONArray jsonarray1 = new JSONArray(buffer.toString());

            jsonarray1 = Ticketjsonobject.getJSONArray("ticketItems");
            fetchedjsonArray = jsonarray1;

            JSONObject jsonobject1 = null;
            TicketItemList.clear();
            for(int i = 0; i<jsonarray1.length();i++) {

                TicketItem ticketItem = new TicketItem();
                Gson gson = new Gson();

                jsonobject1 = jsonarray1.getJSONObject(i);

                ticketItem = gson.fromJson(jsonobject1.toString(),TicketItem.class);

                TicketItemList.add(ticketItem);
            }
            newlistpos = jsonarray1.length();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPreviousArticle(){

        List<String> ItemList = new ArrayList<>();
        Total = 0;

        for (int i= 0; i < TicketItemList.size(); i++){

            String Description = "";
            String Zutaten = "";

            if(TicketItemList.get(i).getCookingInstructions() != null)
            {

                for( int j = 0; j< TicketItemList.get(i).getCookingInstructions().size(); j++)
                {
                    Description = Description + "\n + " + TicketItemList.get(i).getCookingInstructions().get(j).getDescription();
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



            ItemList.add( "G" + TicketItemList.get(i).getPrintorder()+ " "+TicketItemList.get(i).getItemId() + " "+TicketItemList.get(i).getName()   + " X " +  TicketItemList.get(i).getItemCount() +" = "+ (TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount())
            + Description + Zutaten);
            Total = Total + (TicketItemList.get(i).getUnitPrice() *TicketItemList.get(i).getItemCount());
        }

        LAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList);
        ItemListView.setAdapter(LAdapter);

        TotalText.setText("Total "+round(Total,2));
    }

    public int checkcount(int ItemId){

        int count = 1;

        for (int i= newlistpos; i < TicketItemList.size(); i++){

            if (TicketItemList.get(i).getItemId() == ItemId){

                count = TicketItemList.get(i).getItemCount() + 1;

                TicketItemList.get(i).setItemCount(count);
            }
        }

        return count;

    }

    @Override
    public void onBackPressed() {

        Tableno = "";
        Articleno = "";
        PressedKey = "";
        startActivity( new Intent(this, TableActivity.class));
    }

    public void SaldoPushButton()
    {
        onBackPressed();

        if(TicketItemList.size() < 1){

        }
        else{
            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionTask1().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
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
                    newjsonobject1.put("id", String.valueOf(TicketItemList.get(i).getId()));
                    newjsonobject1.put("modifiedTime", String.valueOf(System.currentTimeMillis()));
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

    public void checkmultiplecursor()
    {
        for (int i=0;i<LAdapter.getCount();i++){
            View arg1 = ItemListView.getChildAt(i);

            int color = Color.TRANSPARENT;
            Drawable background = arg1.getBackground();

            if(i != iselectedItem) {
                arg1.setBackgroundColor(Color.TRANSPARENT);
            }

        }

    }


    public void cookingInstruction()
    {
       if(bselectedItem)
        {

            if((TicketItemList.size()-newlistpos >0) && (iselectedItem-newlistpos >= 0)) {
                InstrucList.clear();
                ServeraddrApi = "/cookinginstruction/all";
                new NetworkConnectionTaskAlt().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
            }
            else {
                InfoMessage("Please add cooking instruction on new Item ", Color.RED,42);
            }

        }
        else{
            InfoMessage("Please select one new item", Color.RED,42);
        }

    }

    @Override
    public void getJsonAlt(StringBuffer buffer)
    {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = null;
            try {
                jsonobject = jsonArray.getJSONObject(i);

                InstrucList.add(jsonobject.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void valuesupdateAlt()
    {
        button();
        detectList.clear();
        grid.setVisibility(View.VISIBLE);
        Invisibility();
    }

    public void button()
    {
        myButton = new Button[InstrucList.size()+1];
        grid.setColumnCount(5);
        grid.setRowCount(10);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                180,
                100
        );

        params.setMargins(5, 5, 5, 5);

        for(int i= 0; i < InstrucList.size(); i++){

            myButton[i] = new Button(this);
            myButton[i].setText(InstrucList.get(i));
            myButton[i].setBackgroundResource(R.drawable.colorpopup);

            grid.addView(myButton[i],params);
            myButton[i].setOnClickListener(getOnClickDoSomething(myButton[i]));
        }
        myButton[InstrucList.size()] =  new Button(this);
        myButton[InstrucList.size()].setText("Done");
        myButton[InstrucList.size()].setTextColor(Color.BLUE);
        myButton[InstrucList.size()].setBackgroundResource(R.drawable.colorpopup);

        grid.addView(myButton[InstrucList.size()],params);
        myButton[InstrucList.size()].setOnClickListener(finalize(myButton[InstrucList.size()]));

        myButton[InstrucList.size()] =  new Button(this);
        myButton[InstrucList.size()].setText("Manual Entry");
        myButton[InstrucList.size()].setTextColor(Color.BLUE);
        myButton[InstrucList.size()].setBackgroundResource(R.drawable.colorpopup);

        grid.addView(myButton[InstrucList.size()],params);
        myButton[InstrucList.size()].setOnClickListener(ShowDialog(myButton[InstrucList.size()]));


    }

    View.OnClickListener ShowDialog(final  Button button){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpecialNoteDialog addition = SpecialNoteDialog.getInstance(true);
                addition.setCancelable(false);
                addition.show(getSupportFragmentManager(), null);
            }
        } ;

    }
    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                detectitem = button.getText().toString();
                detectList.add(detectitem);
                colorchange(button);
            }

        };
    }

    View.OnClickListener finalize(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                addInstructions();
                grid.setVisibility(View.INVISIBLE);
                Visibility();
                grid.removeAllViews();
                getPreviousArticle();
            }

        };
    }

    public void addInstructions(){

        List<TicketItemCookingInstruction> TcookList = new ArrayList<>();
        for (int i = 0; i < detectList.size();i++)
        {
            TicketItemCookingInstruction tcook = new TicketItemCookingInstruction();
            tcook.setDescription(detectList.get(i));
            tcook.setPrintedToKitchen(false);

            TcookList.add(tcook);

        }
        TicketItemList.get(iselectedItem).setCookingInstructions(TcookList);
    }

    public void colorchange(final Button button)
    {
        button.setBackgroundResource(R.drawable.colorbuttonchanged);
    }
    public void colorchangeohne(final Button button)
    {
        button.setBackgroundResource(R.drawable.colourbuttonchangedohne);
    }


    public void Invisibility()
    {

        basenumber.setVisibility(View.INVISIBLE);
        TotalText.setVisibility(View.INVISIBLE);
        TableDisplay.setVisibility(View.INVISIBLE);
        ItemListView.setVisibility(View.INVISIBLE);
        Saldo.setVisibility(View.INVISIBLE);
        Notiz.setVisibility(View.INVISIBLE);
        Gaenge.setVisibility(View.INVISIBLE);
        Zutaten.setVisibility(View.INVISIBLE);
        PrintButton.setVisibility(View.INVISIBLE);
        HomeButton.setVisibility(View.INVISIBLE);
        PaymentButton.setVisibility(View.INVISIBLE);
        SplitButton.setVisibility(View.INVISIBLE);
    }
    public void Visibility()
    {
        basenumber.setVisibility(View.VISIBLE);
        TotalText.setVisibility(View.VISIBLE);
        TableDisplay.setVisibility(View.VISIBLE);
        ItemListView.setVisibility(View.VISIBLE);
        Saldo.setVisibility(View.VISIBLE);
        Notiz.setVisibility(View.VISIBLE);
        Gaenge.setVisibility(View.VISIBLE);
        Zutaten.setVisibility(View.VISIBLE);
        PrintButton.setVisibility(View.VISIBLE);
        HomeButton.setVisibility(View.VISIBLE);
        PaymentButton.setVisibility(View.VISIBLE);
        SplitButton.setVisibility(View.VISIBLE);
    }

    public void GaengeActivation()
    {
        if(bselectedItem)
        {

            if((TicketItemList.size()-newlistpos >0) && (iselectedItem-newlistpos >= 0))
            {
                GaengeButtonActivation();
                grid.setVisibility(View.VISIBLE);

                Invisibility();
            }
            else
            {
                InfoMessage("Please add cooking instruction on new Item ", Color.RED,42);
            }

        }
        else{
            InfoMessage("Please select one new item", Color.RED,42);
        }

    }

    public void GaengeButtonActivation()
    {

        GaengeButton = new Button[6];
        grid.setColumnCount(5);
        grid.setRowCount(10);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                180,
                100
        );

        params.setMargins(5, 5, 5, 5);

        for(int i= 0; i < GaengeButton.length -1; i++){

            GaengeButton[i] = new Button(this);
            GaengeButton[i].setText((i+1)+"");
            GaengeButton[i].setId(i+1);

            grid.addView(GaengeButton[i],params);
            GaengeButton[i].setOnClickListener(GaengeOnClick(GaengeButton[i]));
            GaengeButton[i].setBackgroundResource(R.drawable.colorpopup);
        }
        GaengeButton[GaengeButton.length -1] =  new Button(this);
        GaengeButton[GaengeButton.length -1].setText("Done");
        GaengeButton[GaengeButton.length -1].setTextColor(Color.BLUE);

        GaengeButton[GaengeButton.length -1].setBackgroundResource(R.drawable.colorpopup);
        grid.addView(GaengeButton[GaengeButton.length -1],params);
        GaengeButton[GaengeButton.length -1].setOnClickListener(GaengeOnClickfinalize(GaengeButton[GaengeButton.length -1]));
    }

    View.OnClickListener GaengeOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                gaengepressed = button.getId();
                colorchange1(button);
            }

        };
    }

    View.OnClickListener GaengeOnClickfinalize(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                TicketItemList.get(iselectedItem).setPrintorder(gaengepressed);
                grid.setVisibility(View.INVISIBLE);
                Visibility();
                grid.removeAllViews();
                getPreviousArticle();
            }

        };
    }
    Drawable backgroundcolr;
    public void colorchange1(final Button button)
    {
        button.setBackgroundResource(R.drawable.colorbuttonchanged);
        selectoneitem();
    }

    public void selectoneitem()
    {
        for (int i=0;i<GaengeButton.length;i++){
            View arg1 = GaengeButton[i];

            if(i != gaengepressed-1) {
                arg1.setBackgroundResource(R.drawable.colorpopup);
            }

        }

    }

    public void ZutatenActivation()
    {
        if(bselectedItem)
        {

            if((TicketItemList.size()-newlistpos >0) && (iselectedItem-newlistpos >= 0))
            {
                ZutatenList.clear();
                ZutList.clear();
                ZutClick.clear();
                grid.setVisibility(View.VISIBLE);
                Invisibility();
                ZutatenButtonActivation();
            }
            else
            {
                InfoMessage("Please add cooking instruction on new Item ", Color.RED,42);
            }

        }
        else {
            InfoMessage("Please select one new item", Color.RED,42);
        }
    }

    public void ZutatenButtonActivation()
    {
        Article newArticle = new Article();
        MenuItemModifierGroup newMenuItemModifierGroup = new MenuItemModifierGroup();
        MenuModifierGroup newMenuModifierGroup = new MenuModifierGroup();
        Set<MenuModifier> newmodifiers = new HashSet();

        newArticle = null;

        for(int i = 0; i < ArticleList.size(); i++)
        {
           if (ArticleList.get(i).getItemId().equals(TicketItemList.get(iselectedItem).getItemId()+""))
           {
               newArticle = ArticleList.get(i);
           }
        }

        newMenuItemModifierGroup = newArticle.getMenuItemModiferGroups();
        if((newArticle != null) && (newMenuItemModifierGroup != null))
        {
            newMenuModifierGroup = newMenuItemModifierGroup.getModifierGroup();
            newmodifiers = newMenuModifierGroup.getModifiers();
        }


        if( newmodifiers.size()>0) {
            ZutatenButton = new Button[newmodifiers.size()+1];
            numclick = new int[newmodifiers.size()];
            grid.setColumnCount(5);
            grid.setRowCount(10);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    180,
                    100
            );

            params.setMargins(5, 5, 5, 5);


            MenuModifier[] MenuModifierArray = newmodifiers.toArray(new MenuModifier[newmodifiers.size()]);
            for (int i = 0; i < ZutatenButton.length -1; i++) {

                ZutatenButton[i] = new Button(this);
                numclick[i] = 0;
                ZutatenButton[i].setText(MenuModifierArray[i].getName() + "\n" + (MenuModifierArray[i].getPrice()+MenuModifierArray[i].getExtraPrice()));
                ZutatenButton[i].setId(MenuModifierArray[i].getId());
                ZutatenButton[i].setTag(i);

                grid.addView(ZutatenButton[i], params);
                ZutatenButton[i].setOnClickListener(ZutatenOnClick(ZutatenButton[i]));
                ZutatenButton[i].setBackgroundResource(R.drawable.colorpopup);
            }
            ZutatenButton[ZutatenButton.length - 1] = new Button(this);
            ZutatenButton[ZutatenButton.length - 1].setText("Done" + "\n" + "");
            ZutatenButton[ZutatenButton.length - 1].setTextColor(Color.BLUE);

            ZutatenButton[ZutatenButton.length - 1].setBackgroundResource(R.drawable.colorpopup);
            grid.addView(ZutatenButton[ZutatenButton.length - 1], params);
            ZutatenButton[ZutatenButton.length - 1].setOnClickListener(Zutatenfinalize(ZutatenButton[ZutatenButton.length - 1]));
        }
        else
        {
            InfoMessage("No Addons", Color.RED,42);
            grid.setVisibility(View.INVISIBLE);
            Visibility();
            grid.removeAllViews();
        }
    }

    View.OnClickListener ZutatenOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {

                numclick[Integer.parseInt(button.getTag().toString())]++;
                if(numclick[Integer.parseInt(button.getTag().toString())]-1 == 1)
                {
                    colorchangeohne(button);
                }
                else if (numclick[Integer.parseInt(button.getTag().toString())]-1 == 0)
                {
                    colorchange(button);
                }
                else
                {
                    button.setBackgroundResource(R.drawable.colorpopup);
                    numclick[Integer.parseInt(button.getTag().toString())] = 0;
                    removeList(button);
                }



                if (numclick[Integer.parseInt(button.getTag().toString())] > 0) {

                    ZutList.add(button.getId());
                    ZutClick.add(numclick[Integer.parseInt(button.getTag().toString())]);
                }

                if (numclick[Integer.parseInt(button.getTag().toString())] > 1) {
                        checklist(ZutList);
                }

            }

        };
    }

    public void checklist(List<Integer> ichecklist)
    {
        for (int i = 0; i < ichecklist.size(); i++)
        {
            for(int j = i+1 ; j< ichecklist.size(); j++)
            {
               if (ichecklist.get(i).intValue() == ichecklist.get(j).intValue())
               {
                   ichecklist.remove(i);
                   ZutClick.remove(i);
               }
            }
        }
    }

    public void removeList(final Button button)
    {
        for (int i = 0; i < ZutList.size(); i++)
        {
            if (ZutList.get(i).intValue() == button.getId())
            {
                ZutList.remove(i);
                ZutClick.remove(i);
            }
        }
    }

    View.OnClickListener Zutatenfinalize(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                ZutatenaddInstructions();
                grid.setVisibility(View.INVISIBLE);
                Visibility();
                grid.removeAllViews();
                getPreviousArticle();
            }

        };
    }

    public void ZutatenaddInstructions(){

        List<TicketItemModifierGroup> TicketItemModifierGroupList = new ArrayList<>();
        TicketItemModifierGroup newTicketItemModifierGroup = new TicketItemModifierGroup();
        List<TicketItemModifier> newTicketItemModifierList = new ArrayList<>();

        for (int i = 0; i < ZutList.size();i++)
        {
            boolean reset = false;


            TicketItemModifier newTicketItemModifier =  new TicketItemModifier();

            for(int j = 0; j < ArticleList.size(); j++)
            {
                MenuModifier[] MenuModifierArray = null;
                MenuItemModifierGroup testobj = ArticleList.get(j).getMenuItemModiferGroups();


                if(testobj != null) {

                    MenuModifierArray = ArticleList.get(j).getMenuItemModiferGroups().getModifierGroup().getModifiers().toArray(new MenuModifier[ArticleList.get(j).getMenuItemModiferGroups().getModifierGroup().getModifiers().size()]);


                    for (int k = 0; k < MenuModifierArray.length; k++) {
                        if ((MenuModifierArray[k].getId() == ZutList.get(i)) && !reset) {

                           try{
                               newTicketItemModifier.setItemId(MenuModifierArray[k].getId());
                               newTicketItemModifier.setName(MenuModifierArray[k].getName());
                               newTicketItemModifier.setExtraUnitPrice(MenuModifierArray[k].getExtraPrice());
                               newTicketItemModifier.setModifierType(ZutClick.get(i));
                               newTicketItemModifierList.add(newTicketItemModifier);
                           }
                           catch(NullPointerException e){
                                e.printStackTrace();
                            }

                            reset = true;
                        }

                    }
                }


            }


        }
        newTicketItemModifierGroup.setTicketItemModifiers(newTicketItemModifierList);

        TicketItemModifierGroupList.add(newTicketItemModifierGroup);

        TicketItemList.get(iselectedItem).setTicketItemModifierGroups(TicketItemModifierGroupList);
    }

    public void printactivation()
    {

        startActivity( new Intent(this, PrintActivity.class));

    }

    public void Paymentactivation() {

        grid.setVisibility(View.VISIBLE);
        Invisibility();
        PaymentButtonactivation();
    }

    Button PrintRadio, OfficialRadio;
    Boolean Printapi = true;
    Boolean Officialapi = false;

    public void PaymentButtonactivation()
    {
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
        PrintRadio.setOnClickListener(RadioChecked());
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
        OfficialRadio.setOnClickListener(OfficialRadioChecked());
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
        PaymentTypes[0].setOnClickListener(BarOnFinalise(value0));
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
        PaymentTypes[1].setOnClickListener(BarOnFinalise(value2));
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
        PaymentTypes[2].setOnClickListener(BarOnFinalise(value4));
        PaymentTypes[2].setBackgroundResource(R.drawable.colorpopup);

        /*Cancel*/
        PaymentTypes[3] = new Button(this);
        PaymentTypes[3].setText("Cancel");
        GridLayout.LayoutParams Fifth = new GridLayout.LayoutParams(row2, col0);

        Fifth.height = 100;
        Fifth.width = 180;
        Fifth.setMargins(5,5,5,5);

        grid.addView(PaymentTypes[3], Fifth);
        PaymentTypes[3].setOnClickListener(CancelonFinalise());
        PaymentTypes[3].setBackgroundResource(R.drawable.colorpopup);

    }

    View.OnClickListener BarOnFinalise(final String Value) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                grid.setVisibility(View.INVISIBLE);
                Visibility();
                grid.removeAllViews();
                getPreviousArticle();
                if(TicketItemList.size() < 1){
                    onBackPressed();
                }
                else {
                    new PostNetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + Value);
                    onBackPressed();
                }
            }

        };
    }

    View.OnClickListener CancelonFinalise() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                grid.setVisibility(View.INVISIBLE);
                Visibility();
                grid.removeAllViews();
                getPreviousArticle();
            }

        };
    }

    View.OnClickListener RadioChecked() {
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

    View.OnClickListener OfficialRadioChecked() {
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

    public void Splitactivation()
    {
        startActivity( new Intent(this, SplitActivity.class));
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
      if ( isConnected){
          Toast.makeText(this, "Good! You are Online", Toast.LENGTH_SHORT).show();
      } else {
          Toast.makeText(this, "Oops! Network Error", Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void returnSpecialNotes(String returnbackpound) {

        detectList.add(returnbackpound.trim());
        addInstructions();
        grid.setVisibility(View.INVISIBLE);
        Visibility();

        grid.removeAllViews();
        getPreviousArticle();
    }

    public class PostNetworkConnectionTask1 extends AsyncTask<String,String,String>{

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

                Log.d("error",jsonParam.toString());
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
        }
    }
}
