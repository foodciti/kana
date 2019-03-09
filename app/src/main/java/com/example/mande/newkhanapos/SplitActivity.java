package com.example.mande.newkhanapos;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
    TextView TotalText, TotalText1;
    Button PreviewBack,SaldoButton;
    List<TicketItem> split_ticket_item = new ArrayList<>();
    List<String> ItemList, ItemList1;
    List<Double> PriceList, PriceList1;
    String status="";
    Double bill1, bill2;

    List<TicketItem> TicketItemList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splitpreview);

        TotalText = findViewById(R.id.euroid1);
        TotalText1 = findViewById(R.id.euroid2);

        fullscreen();

        listdisplay = findViewById(R.id.listid2);
        listdisplay1 = findViewById(R.id.listid1);
        PreviewBack = (Button) findViewById(R.id.PreviewBack);
        PreviewBack.setText("Back");
        PreviewBack.setBackgroundResource(R.drawable.colorbutton);

        SaldoButton = (Button) findViewById(R.id.SaldoButton);
        SaldoButton.setText("Saldo");
        SaldoButton.setBackgroundResource(R.drawable.colorbutton);

        PreviewBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        ItemList = new ArrayList<>();
        ItemList1 = new ArrayList<>();
        PriceList = new ArrayList<>();
        PriceList1 = new ArrayList<>();

        bill1 = 0.0;
        bill2 = 0.0;


        /*listdisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               *//* if(count > 1){

                    count =- 1;
                } else {

                }*//*
                int itemCount = TicketItemList.get(i).getItemCount();

                if(itemCount > 1){
                    itemCount--;
                    count++;
                    if(count ==1){
                        ItemList1.add(ItemList.get(i));
                        PriceList1.add(PriceList.get(i));
                        TicketItemList.get(i).setItemCode(count);
                        split_ticket_item.add(TicketItemList.get(i));
                        ItemList.remove(i);
                        PriceList.remove(i);
                    } else {

                    }

                } else {
                    count--;
                    ItemList1.add(ItemList.get(i));
                    PriceList1.add(PriceList.get(i));
                    split_ticket_item.add(TicketItemList.get(i));
                    ItemList.remove(i);
                    PriceList.remove(i);
                    TicketItemList.remove(i);
                }


                previewDisplay1();
            }
        });

        listdisplay1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ItemList.add(ItemList1.get(i));
                PriceList.add(PriceList1.get(i));
                TicketItemList1.add(split_ticket_item.get(i));
                ItemList1.remove(i);
                PriceList1.remove(i);
                split_ticket_item.remove(i);
                previewDisplay1();
            }
        });*/

        SaldoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplitSaldoPushButton();
            }
        });
        previewDisplay();

    }

    public void SplitSaldoPushButton() {
        int splitCount =0;
        if(ItemList.size()>0 && ItemList1.size() >0){
            splitCount =2;
            postDataJson(splitCount,bill1, bill2);
        } else if(ItemList.size() > 0){
            status="1";
            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionSplit().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
            //postJsonBill(status) ;
        } else if(ItemList1.size() > 0){
            status="2";
            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionSplit1().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
            // postJsonBill(status) ;
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

    /*public JSONObject postJsonBill(){

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
                    if(status.equals("1")){
                        newJsonObject.put("totalAmount", round(bill1,2));
                    } else if(status.equals("2")){
                        newJsonObject.put("totalAmount", round(bill2,2));
                    }

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

    }*/


    public JSONObject postJsonSplit()
    {
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


    public JSONObject postJsonSplit1()
    {
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



    public void previewDisplay()
    {

        Total = 0;

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



            /*ItemList.add( TicketItemList.get(i).getItemCount() + " X " + TicketItemList.get(i).getItemId() + " "+TicketItemList.get(i).getName() +  "   "+(TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount()) +"€"
                    + Zutaten);
            PriceList.add(TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount());*/
            Total = Total + (TicketItemList.get(i).getUnitPrice() *TicketItemList.get(i).getItemCount());
        }


        AdapterList LAdapter = new AdapterList(this,TicketItemList);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        listdisplay.setLayoutManager(mLayoutManager1);
        listdisplay.setItemAnimator(new DefaultItemAnimator());
        listdisplay.setAdapter(LAdapter);
        TotalText.setText("€ " + round(Total, 2));
    }

    public void  previewDisplay1()
    {
       /* LAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList1);
        listdisplay1.setAdapter(LAdapter1);
        bill2 = pricecalculation(PriceList1);
        TotalText1.setText("€ " + round(bill2, 2));*/

        AdapterList1 adapterList1 = new AdapterList1(this,split_ticket_item);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listdisplay1.setLayoutManager(mLayoutManager);
        listdisplay1.setItemAnimator(new DefaultItemAnimator());
        listdisplay1.setAdapter(adapterList1);
        bill2 = pricecalculation(PriceList1);
        TotalText1.setText("€ " + round(bill2, 2));

        AdapterList LAdapter = new AdapterList(this,TicketItemList);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        listdisplay.setLayoutManager(mLayoutManager1);
        listdisplay.setItemAnimator(new DefaultItemAnimator());
        listdisplay.setAdapter(LAdapter);
        bill1 = pricecalculation(PriceList);
        TotalText.setText("€ " + round(bill1, 2));

    }

    public Double pricecalculation(List<Double> list)
    {
        Double value = 0.0;

        for (int i = 0; i < list.size(); i ++)
        {
            value += list.get(i);
        }

        return value;
    }

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
        public void onBindViewHolder(@NonNull AdapterList.ViewHolder holder, final int position) {
            TicketItem ticketItemList1 = ticket_list.get(position);
            holder.itemCount1.setText(String.valueOf(ticketItemList1.getItemCount()));
            holder.itemId.setText(String.valueOf(ticketItemList1.getItemId()));
            holder.itemName.setText(ticketItemList1.getName());
            holder.price.setText(String.valueOf(ticketItemList1.getItemCount() *ticketItemList1.getUnitPrice()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = TicketItemList.get(position).getItemCount();


                    if(itemCount > 1){
                        itemCount--;
                        count++;
                        if(count ==1){
                           /* ItemList1.add(ItemList.get(position));
                            PriceList1.add(PriceList.get(position));*/
                            TicketItemList.get(position).setItemCount(itemCount);
                            split_ticket_item.add(TicketItemList.get(position));
                            split_ticket_item.get(0).setItemCount(count);
                           // TicketItemList.get(position).setItemCount(itemCount);
                            /*ItemList.remove(position);
                            PriceList.remove(position);*/
                        } else {
                            itemCount = TicketItemList.get(position).getItemCount();
                            if(itemCount==1){
                                split_ticket_item.add(TicketItemList.get(position));
                                TicketItemList.remove(position);
                            } else {
                                itemCount--;
                                int item = split_ticket_item.get(position).getItemCount();
                                TicketItemList.get(position).setItemCount(itemCount);
                                split_ticket_item.get(position).setItemCount(item++);
                            }

                        }

                    } else {
                        count--;
                        /*ItemList1.add(ItemList.get(position));
                        PriceList1.add(PriceList.get(position));*/
                        split_ticket_item.add(TicketItemList.get(position));
                        TicketItemList.remove(position);
                       /* ItemList.remove(position);
                        PriceList.remove(position);*/

                    }


                    previewDisplay1();
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
        public void onBindViewHolder(@NonNull AdapterList1.ViewHolder1 holder, final int position) {
            TicketItem ticketItemList = split_list.get(position);
            holder.itemCount.setText(String.valueOf(ticketItemList.getItemCount()));
            holder.itemId.setText(String.valueOf(ticketItemList.getItemId()));
            holder.itemName.setText(ticketItemList.getName());
            holder.price.setText(String.valueOf(ticketItemList.getItemCount() *ticketItemList.getUnitPrice()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemList.add(ItemList1.get(position));
                    PriceList.add(PriceList1.get(position));
                    TicketItemList1.add(split_ticket_item.get(position));
                    ItemList1.remove(position);
                    PriceList1.remove(position);
                    split_ticket_item.remove(position);
                    previewDisplay1();
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


}
