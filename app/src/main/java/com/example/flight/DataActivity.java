package com.example.flight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DataActivity extends AppCompatActivity {

    String quotes;
    JSONParser parser;
    DataQuote[] quoteList;
    DataViewFormat ticketAdapter;
    ArrayList<TicketItem> ticketList;
    Button carrier;
    Button price;
    Button origin;
    Button dest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent = this.getIntent();

        carrier = findViewById(R.id.airlineDVHeader);
        origin = findViewById(R.id.originDVHeader);
        dest = findViewById(R.id.destDVHeader);
        price = findViewById(R.id.priceDVHeader);
        quotes = intent.getStringExtra("quotes");
        parser = new JSONParser();
        parser.parseQuotes(quotes);
        quoteList = parser.getQuotes();

        ticketList = new ArrayList<TicketItem>();
        ListView listTicketView = (ListView) findViewById(R.id.flighttextview);
        ticketAdapter = new DataViewFormat(this, ticketList);
        listTicketView.setAdapter(ticketAdapter);

        sortQuotes();

        carrier.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                sortAirlines();
            }
        });
        origin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                sortOrigin();
            }
        });
        dest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                sortDest();
            }
        });
        price.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                sortQuotes();
            }
        });

    }


    private void sortQuotes() {
        if (!quoteList.equals(null))
        {
            for (int i = 0; i < quoteList.length; i++) {
                for (int j = 0; j < quoteList.length - i - 1; j++) {
                    if (quoteList[j].getMinPrice() > quoteList[j + 1].getMinPrice()) {
                        DataQuote temp;
                        temp = quoteList[j];
                        quoteList[j] = quoteList[j + 1];
                        quoteList[j + 1] = temp;
                    }
                }
            }
            ticketList.clear();

            for(int i = 0; i< quoteList.length; i++)
            {
                if(!quoteList[i].getDest().equals("error") && !quoteList[i].getOrigin().equals("error"))
                {
                    TicketItem temp = new TicketItem(Float.toString(quoteList[i].getMinPrice()), quoteList[i].getCarrier(), quoteList[i].getOrigin(), quoteList[i].getDest());
                    ticketList.add(temp);
                }
            }
            ticketAdapter.notifyDataSetChanged();
        }
    }

    public void sortAirlines() {

           Collections.sort(ticketList, new Comparator<TicketItem>() {
               public int compare(TicketItem item1, TicketItem item2) {
                   return item1.getAirline().charAt(0) - item2.getAirline().charAt(0);
               }
           });

           ArrayList<TicketItem> tempT = new ArrayList<TicketItem>(ticketList);
            ticketList.clear();

            for(int i = 0; i< tempT.size(); i++)
            {
                if(!tempT.get(i).getDest().equals("error") && !tempT.get(i).getOrigin().equals("error"))
                {
                    TicketItem temp = new TicketItem(tempT.get(i).getPrice(), tempT.get(i).getAirline(), tempT.get(i).getOrigin(), tempT.get(i).getDest());
                    ticketList.add(temp);
                }
            }
        ticketAdapter.notifyDataSetChanged();

    }

    public void sortOrigin() {

        Collections.sort(ticketList, new Comparator<TicketItem>() {
            public int compare(TicketItem item1, TicketItem item2) {
                return item1.getOrigin().charAt(0) - item2.getOrigin().charAt(0);
            }
        });

        ArrayList<TicketItem> tempT = new ArrayList<TicketItem>(ticketList);
        ticketList.clear();

        for(int i = 0; i< tempT.size(); i++)
        {
            if(!tempT.get(i).getDest().equals("error") && !tempT.get(i).getOrigin().equals("error"))
            {
                TicketItem temp = new TicketItem(tempT.get(i).getPrice(), tempT.get(i).getAirline(), tempT.get(i).getOrigin(), tempT.get(i).getDest());
                ticketList.add(temp);
            }
        }
        ticketAdapter.notifyDataSetChanged();

    }

    public void sortDest() {

        Collections.sort(ticketList, new Comparator<TicketItem>() {
            public int compare(TicketItem item1, TicketItem item2) {
                return item1.getDest().charAt(0) - item2.getDest().charAt(0);
            }
        });

        ArrayList<TicketItem> tempT = new ArrayList<TicketItem>(ticketList);
        ticketList.clear();

        for(int i = 0; i< tempT.size(); i++)
        {
            if(!tempT.get(i).getDest().equals("error") && !tempT.get(i).getOrigin().equals("error"))
            {
                TicketItem temp = new TicketItem(tempT.get(i).getPrice(), tempT.get(i).getAirline(), tempT.get(i).getOrigin(), tempT.get(i).getDest());
                ticketList.add(temp);
            }
        }
        ticketAdapter.notifyDataSetChanged();

    }

    public void sortPrice() {

        Collections.sort(ticketList, new Comparator<TicketItem>() {
            public int compare(TicketItem item1, TicketItem item2) {
                return Integer.parseInt(item1.getPrice()) - Integer.parseInt(item2.getPrice());
            }
        });

        ArrayList<TicketItem> tempT = new ArrayList<TicketItem>(ticketList);
        ticketList.clear();

        for(int i = 0; i< tempT.size(); i++)
        {
            if(!tempT.get(i).getDest().equals("error") && !tempT.get(i).getOrigin().equals("error"))
            {
                TicketItem temp = new TicketItem(tempT.get(i).getPrice(), tempT.get(i).getAirline(), tempT.get(i).getOrigin(), tempT.get(i).getDest());
                ticketList.add(temp);
            }
        }
        ticketAdapter.notifyDataSetChanged();

    }
}
