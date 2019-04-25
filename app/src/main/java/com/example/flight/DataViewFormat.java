package com.example.flight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class DataViewFormat extends BaseAdapter {


    private ArrayList<TicketItem> items;
    private Context context;
    DataSource produitSource;
    LayoutInflater inflater;


    public DataViewFormat(Context context, ArrayList<TicketItem> items)
    {
        this.context = context;
        this.items = items;
        inflater=LayoutInflater.from(context);

    }


    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.basedataview, parent, false);
        }

        // get current item to be displayed
        TicketItem currentItem = (TicketItem) getItem(position);

        // get the TextView for item name and item description
        TextView priceView = (TextView)
                convertView.findViewById(R.id.priceDV);
        TextView airlineView = (TextView)
                convertView.findViewById(R.id.airlineDV);
        TextView originView = (TextView)
                convertView.findViewById(R.id.originDV);
        TextView destView = (TextView)
                convertView.findViewById(R.id.destDV);

        //sets the text for item name and item description from the current item object
        priceView.setText(currentItem.getPrice());
        airlineView.setText(currentItem.getAirline());
        originView.setText(currentItem.getOrigin());
        destView.setText(currentItem.getDest());

        // returns the view for the current row
        return convertView;
    }
}
