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
    Button returnMain;

    int priceOrderToggle = 0;
    int carrierOrderToggle = 0;
    int originOrderToggle = 0;
    int destOrderToggle = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent = this.getIntent();

        carrier = findViewById(R.id.airlineDVHeader);
        origin = findViewById(R.id.originDVHeader);
        dest = findViewById(R.id.destDVHeader);
        price = findViewById(R.id.priceDVHeader);
        returnMain = findViewById(R.id.returnMain);

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
                carrierOrderToggle ^= 1;
                sortAirlines();
            }
        });
        origin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                originOrderToggle ^= 1;
                sortOrigin();
            }
        });
        dest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                destOrderToggle ^= 1;
                sortDest();
            }
        });
        price.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                priceOrderToggle ^= 1;
                sortQuotes();
            }
        });
        returnMain.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                finish();
            }
        });

    }

    private void sortQuotes() {
        if(quoteList != null)
        {
            for (int i = 0; i < quoteList.length; i++) {
                for (int j = 0; j < quoteList.length - i - 1; j++) {
                    if (priceOrderToggle == 0)
                    {
                        if (quoteList[j].getMinPrice() < quoteList[j + 1].getMinPrice()) {
                            DataQuote temp;
                            temp = quoteList[j];
                            quoteList[j] = quoteList[j + 1];
                            quoteList[j + 1] = temp;
                        }
                    }
                    else
                    {
                        if (quoteList[j].getMinPrice() > quoteList[j + 1].getMinPrice()) {
                            DataQuote temp;
                            temp = quoteList[j];
                            quoteList[j] = quoteList[j + 1];
                            quoteList[j + 1] = temp;
                        }
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

    private void sortAirlines() {

        if(carrierOrderToggle == 0)
        {
            Collections.sort(ticketList, new Comparator<TicketItem>() {
                public int compare(TicketItem item1, TicketItem item2) {
                    return item1.getAirline().compareToIgnoreCase(item2.getAirline());
                }
            });
        }
        else
        {
            Collections.sort(ticketList, new Comparator<TicketItem>() {
                        public int compare(TicketItem item1, TicketItem item2) {
                            return  item2.getAirline().compareToIgnoreCase(item1.getAirline());
                        }
                    });
        }

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

    private void sortOrigin() {

        if(originOrderToggle== 0) {

            Collections.sort(ticketList, new Comparator<TicketItem>() {
                public int compare(TicketItem item1, TicketItem item2) {
                    return item1.getOrigin().compareToIgnoreCase(item2.getOrigin());
                }
            });
        }
        else
        {
            Collections.sort(ticketList, new Comparator<TicketItem>() {
                public int compare(TicketItem item1, TicketItem item2) {
                    return item2.getOrigin().compareToIgnoreCase(item1.getOrigin());
                }
            });
        }

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

    private void sortDest() {

        if(destOrderToggle == 0)
        {
            Collections.sort(ticketList, new Comparator<TicketItem>() {
                public int compare(TicketItem item1, TicketItem item2) {
                    return item1.getDest().compareToIgnoreCase(item2.getDest());
                }
            });
        }
        else
        {
            Collections.sort(ticketList, new Comparator<TicketItem>() {
                public int compare(TicketItem item1, TicketItem item2) {
                    return item2.getDest().compareToIgnoreCase(item1.getDest());
                }
            });
        }

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
