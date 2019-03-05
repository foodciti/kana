package com.example.mande.newkhanapos;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PrintActivity extends ArticleActivity {

    ListView listdisplay;
    ListAdapter LAdapter;
    TextView TotalText;
    Button PreviewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printpreview);

        TotalText = findViewById(R.id.euroid1);

        fullscreen();

        listdisplay = findViewById(R.id.listid2);
        PreviewBack = (Button) findViewById(R.id.PreviewBack);
        PreviewBack.setText("Back");
        PreviewBack.setBackgroundResource(R.drawable.colorbutton);

        PreviewBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });

        previewDisplay();

    }

    public void previewDisplay()
    {
        List<String> ItemList = new ArrayList<>();
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
            Total = Total + (TicketItemList.get(i).getUnitPrice() *TicketItemList.get(i).getItemCount());
        }

        LAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, ItemList);
        listdisplay.setAdapter(LAdapter);
        TotalText.setText("€ " + round(Total, 2));
    }

}
