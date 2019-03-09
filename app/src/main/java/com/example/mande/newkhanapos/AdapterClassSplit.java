/*
package com.example.mande.newkhanapos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mande.newkhanapos.models.TicketItem;

import java.util.ArrayList;
import java.util.List;

class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    List<TicketItem> ticket_list = new ArrayList<>();
    Context context;

    public AdapterList(List<TicketItem> ticketItemList) {
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
        holder.itemCount1.setText("");
        holder.itemId.setText(String.valueOf(ticketItemList1.getItemId()));
        holder.itemName.setText(ticketItemList1.getName());
        holder.price.setText(String.valueOf(ticketItemList1.getItemCount() *ticketItemList1.getUnitPrice()));
        holder.itemCount1.setText(ticketItemList1.getItemCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemCount = TicketItemList.get(position).getItemCount();


                if(itemCount > 1){
                    itemCount--;
                    count++;
                    if(count ==1){
                           */
/* ItemList1.add(ItemList.get(position));
                            PriceList1.add(PriceList.get(position));*//*

                        TicketItemList.get(position).setItemCode(count);
                        split_ticket_item.add(TicketItemList.get(position));
                        ItemList.remove(position);
                        PriceList.remove(position);
                    } else {

                    }

                } else {
                    count--;
                    ItemList1.add(ItemList.get(position));
                    PriceList1.add(PriceList.get(position));
                    split_ticket_item.add(TicketItemList.get(position));
                    ItemList.remove(position);
                    PriceList.remove(position);
                    TicketItemList.remove(position);
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
            itemCount1 = findViewById(R.id.text_item_count1);
            itemId = findViewById(R.id.ticket_item_id1);
            itemName = findViewById(R.id.ticket_item_name1);
            price = findViewById(R.id.item_price1);
        }
    }


}*/
