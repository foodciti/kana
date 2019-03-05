package com.example.mande.newkhanapos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mande.newkhanapos.models.TicketItem;
import com.example.mande.newkhanapos.models.TicketItemCookingInstruction;
import com.example.mande.newkhanapos.models.TicketItemModifier;
import com.example.mande.newkhanapos.models.TicketItemModifierGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplitActivity extends ArticleActivity {

    ListView listdisplay, listdisplay1;
    ListAdapter LAdapter, LAdapter1;
    TextView TotalText, TotalText1;
    Button PreviewBack,SaldoButton;

    List<String> ItemList, ItemList1;
    List<Double> PriceList, PriceList1;

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


        listdisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int count = TicketItemList1.get(i).getItemCount();
                if(count > 1){
                    ItemList1.add(ItemList.get(i-1));
                    count =- 1;
                } else {
                    ItemList1.add(ItemList.get(i));
                    PriceList1.add(PriceList.get(i));
                }

                ItemList.remove(i);

                PriceList.remove(i);
                previewDisplay1();
            }
        });

        listdisplay1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ItemList.add(ItemList1.get(i));
                PriceList.add(PriceList1.get(i));
                ItemList1.remove(i);
                PriceList1.remove(i);
                previewDisplay1();
            }
        });

        SaldoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplitSaldoPushButton();
            }
        });
        previewDisplay();

    }

    public void SplitSaldoPushButton() {
        if(ItemList.size()< 1){

        } else {
            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
        }

        if(ItemList1.size() < 1 ){

        } else {
            ServeraddrApi = "/ticket/save?official=true&print=true&saveType=0";
            new PostNetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
        }
    }


    public JSONObject postJson()
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
                    tablesObject.put("split", true);
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



            ItemList.add( TicketItemList.get(i).getItemCount() + " X " + TicketItemList.get(i).getItemId() + " "+TicketItemList.get(i).getName() +  "   "+(TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount()) +"€"
                    + Zutaten);
            PriceList.add(TicketItemList.get(i).getUnitPrice() * TicketItemList.get(i).getItemCount());
            Total = Total + (TicketItemList.get(i).getUnitPrice() *TicketItemList.get(i).getItemCount());
        }

        LAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList);
        listdisplay.setAdapter(LAdapter);
        TotalText.setText("€ " + round(Total, 2));
    }

    public void  previewDisplay1()
    {
        LAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList1);
        listdisplay1.setAdapter(LAdapter1);
        bill2 = pricecalculation(PriceList1);
        TotalText1.setText("€ " + round(bill2, 2));

        LAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList);
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




}
