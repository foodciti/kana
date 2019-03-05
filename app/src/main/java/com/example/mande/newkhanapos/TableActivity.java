package com.example.mande.newkhanapos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mande.newkhanapos.Services.MyAppllication;
import com.example.mande.newkhanapos.Services.NetworkChangeReceiver;
import com.example.mande.newkhanapos.models.Table;
import com.example.mande.newkhanapos.models.TicketItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends MainActivity implements NetworkChangeReceiver.ConnectivityReceiverListener{

    RecyclerView BusyTableView;
    List<String> BusyTablelist = new ArrayList<>();
    List<String> BusyTablePricelist = new ArrayList<>();
    TableListAdapter LAdapter;
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        fullscreen();

        KeyStringDisplay = findViewById(R.id.tableview);
        KeyMap();
        numKey[11].setText("Tb");

        BusyTableView = findViewById(R.id.busytable);
       /* BusyTableView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InfoMessage("Table Exists " + BusyTablelist.get(i),Color.BLUE,32);
                PressedKey = "";
                char[] localvalue = new char[3];
                BusyTablelist.get(i).getChars(6, 9, localvalue, 0);
                Tableno = String.valueOf(localvalue);

                for(int j = 0; j< TableList.size(); j++){
                  if(TableList.get(j).getNumber() == Tableno)
                  {
                      TableIdentification = j;
                  }

                }
                redirect();
            }
        });*/

        Logout = (Button) findViewById(R.id.LogOutid);
        Logout.setText("LogOut");
        Logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });

        ServeraddrApi = "/shoptable/all";
        new NetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);

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
    public void getJson(StringBuffer buffer)
    {
        TableList.clear();
        TicketItemList.clear();
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
                Table table = new Table();
                table.setId(jsonobject.getInt("id"));
                table.setNumber(jsonobject.getString("number"));
                //table.setFloor(jsonobject.getInt("floor"));
                table.setOccupied(jsonobject.getBoolean("occupied"));
                table.setTickettype(jsonobject.getString("tickettype"));
                table.setAmount(jsonobject.getDouble("totalAmount"));
                TableList.add(table);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                VBusyTableList();
            }
        });

    }
    @Override
    public void DoAction() {

        Tableno = PressedKey;

        if (CurrentPressedKey.equals("Tb")){
            Tableno = Tableno.replace("Tb","");

            int Error = 0;
            for(int i = 0; i <= TableList.size(); i++)
            {
                if(i==TableList.size())
                {
                    Error = 1;
                }
                else if(PressedKey.equals(TableList.get(i).getNumber()+"Tb"))
                {
                    InfoMessage("Table Exists " + TableList.get(i).getAmount(),Color.BLUE,32);
                    TableIdentification = i;
                    PressedKey = "";
                    Intent intent = new Intent(this, ArticleActivity.class);
                    startActivity(intent);
                    break;
                }
            }

            // This will happen only if password with all users does not match
            if (Error ==1) {
                InfoMessage("False Entry", Color.RED, 32);
                PressedKey = "";
                Tableno ="";
                KeyStringDisplay.setText("Table "+Tableno);
            }

        }
        else{
            KeyStringDisplay.setText("Table "+Tableno);
        }
    }

    @Override
    public void onBackPressed() {

        //remain here
        startActivity( new Intent(this, LoginActivity.class));
    }

    public void VBusyTableList(){

        InfoMessage( TableList.size() + " Total Table Found", Color.GREEN,32);
        String X = "";
        String Y= "";
        for(int i = 0; i < TableList.size(); i++)
        {
            if (TableList.get(i).isOccupied()) {
                X = "Table " + TableList.get(i).getNumber();
                Y= "Amount  " + TableList.get(i).getAmount();
                BusyTablelist.add(X);
                BusyTablePricelist.add(Y);
            }

        }

        /*LAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, BusyTablelist);
        BusyTableView.setAdapter(LAdapter);*/
        LAdapter = new TableListAdapter();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        BusyTableView.setLayoutManager(mLayoutManager);
        BusyTableView.setItemAnimator(new DefaultItemAnimator());
        BusyTableView.setAdapter(LAdapter);


    }

    public void redirect() {

        startActivity( new Intent(this, ArticleActivity.class));
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if ( isConnected){
            Toast.makeText(this, "Good! You are Online", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Oops! Network Error", Toast.LENGTH_SHORT).show();
        }
    }

    // new edited
    class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.ViewHolder> {


        @NonNull
        @Override
        public TableListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_list_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TableListAdapter.ViewHolder holder, final int position) {


                holder.table_name.setText(BusyTablelist.get(position).toString());
                holder.table_price.setText(BusyTablePricelist.get(position).toString());




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InfoMessage("Table Exists " + TableList.get(position),Color.BLUE,32);
                    PressedKey = "";
                    char[] localvalue = new char[3];
                    //
                    BusyTablelist.get(position).getChars(6, 9, localvalue, 0);
                    Tableno = String.valueOf(localvalue);

                    for(int j = 0; j< TableList.size(); j++){
                        if(TableList.get(j).getNumber() == Tableno)
                        {
                            TableIdentification = j;
                        }

                    }
                    redirect();
                }
            });
        }

        @Override
        public int getItemCount() {
            return BusyTablelist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView table_name, table_price;
            public ViewHolder(View itemView) {
                super(itemView);
                 table_name = itemView.findViewById(R.id.table_list_name);
                 table_price = itemView.findViewById(R.id.table_price);
            }
        }
    }
}


