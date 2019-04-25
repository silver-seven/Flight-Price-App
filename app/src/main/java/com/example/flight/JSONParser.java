package com.example.flight;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    MainActivity main;
    String msg;
    TextView data;
    DataQuote[] quotesList;
    DataPlace[] placeList;
    DataCarrier[] carrierList;
    Spinner combo;


    public JSONParser()
    {

    }
    public JSONParser(MainActivity main)
    {
        this.main = main;
    }
    public List<String>  parsePlace(String text, int combo)
    {
        List<String> out = null;
        try {
            JSONObject obj = new JSONObject(text);
            JSONArray arr = obj.getJSONArray("Places");
            if(arr.length() > 0)
            {
                List<String> spinnerItem =  new ArrayList<String>();
                for(int i = 0; i< arr.length(); i++)
                {
                    JSONObject pObj = arr.getJSONObject(i);
                    spinnerItem.add(pObj.getString("PlaceId") + ": " + pObj.getString("PlaceName"));
                }
                out = spinnerItem;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String parsePlace(String text)
    {
        String out = "error";
        try {
            JSONObject obj = new JSONObject(text);
            JSONArray arr = obj.getJSONArray("Places");
            if(arr.length() > 0)
            {
                JSONObject pObj = arr.getJSONObject(0);
                out = pObj.getString("PlaceId");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String parseQuotes(String text)
    {
        String out = "ERROR";
        try {
            JSONObject obj = new JSONObject(text);
            JSONArray places = obj.getJSONArray("Places");
            JSONArray quotes = obj.getJSONArray("Quotes");
            JSONArray carriers = obj.getJSONArray("Carriers");
            int num_quotes = quotes.length();
            if(num_quotes > 0) {
                placeList = new DataPlace[places.length()];
                carrierList = new DataCarrier[carriers.length()];
                int placesN = places.length();
                int carriersN = carriers.length();

                for (int p = 0; p < placesN ; p++) {
                    JSONObject tempObj = places.getJSONObject(p);
                    if(tempObj.getString("Type").equals( "Station")) {
                        placeList[p] = new DataPlace(
                                tempObj.getInt("PlaceId"),
                                tempObj.getString("IataCode"),
                                tempObj.getString("Name"),
                                tempObj.getString("Type"),
                                tempObj.getString("SkyscannerCode"),
                                tempObj.getString("CityName"),
                                tempObj.getString("CityId"),
                                tempObj.getString("CountryName"));
                    }
                }
                for (int c = 0; c < carriersN; c++) {
                    JSONObject tempObj = carriers.getJSONObject(c);
                    carrierList[c] = new DataCarrier(
                            tempObj.getInt("CarrierId"),
                            tempObj.getString("Name")
                    );
                }

                quotesList = new DataQuote[num_quotes];
                for (int q = 0; q < num_quotes; q++) {
                    JSONObject tempObj = quotes.getJSONObject(q);
                    JSONObject carrierObj = tempObj.getJSONObject("OutboundLeg");
                    quotesList[q] = new DataQuote(
                            tempObj.getInt("QuoteId"),
                            tempObj.getInt("MinPrice"),
                            tempObj.getBoolean("Direct"),
                            carrierObj.getInt("OriginId"),
                            carrierObj.getInt("DestinationId"),
                            carrierObj.getJSONArray("CarrierIds").getInt(0)
                    );

                    quotesList[q].setPlaces(placeList);
                    quotesList[q].setCarrier(carrierList);
                }
                out = "SUCCESS";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public DataQuote[] getQuotes()
    {
        return quotesList;
    }
}
