package com.example.flight;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    String msg;
    TextView data;
    DataQuote[] quotesList;

    public JSONParser()
    {

    }
    public String parsePlace(String text)
    {
        String out = "ERROR";
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
            if(num_quotes > 0)
            {
                DataPlace[] placeList = new DataPlace[places.length()];
                DataCarrier[] carrierList = new DataCarrier[carriers.length()];
                for(int i = 0; i<placeList.length; i++)
                {
                    JSONObject tempObj = places.getJSONObject(i);
                    placeList[i] = new DataPlace(
                            tempObj.getInt("PlaceId"),
                            tempObj.getString("IataCode"),
                            tempObj.getString("Name"),
                            tempObj.getString("Type"),
                            tempObj.getString("SkyscannerCode"),
                            tempObj.getString("CityName"),
                            tempObj.getString("CityId"),
                            tempObj.getString("CountryName"));
                }
                for(int i = 0; i<carrierList.length; i++)
                {
                    JSONObject tempObj = carriers.getJSONObject(i);
                    carrierList[i] = new DataCarrier(
                            tempObj.getInt("CarrierId"),
                            tempObj.getString("Name")
                    );
                }

                quotesList = new DataQuote[num_quotes];
                for(int i = 0; i <num_quotes; i++)
                {
                   JSONObject tempObj = quotes.getJSONObject(i);
                   JSONObject carrierObj = tempObj.getJSONObject("OutboundLeg");
                   quotesList[i] = new DataQuote(
                           tempObj.getInt("QuoteId"),
                           tempObj.getInt("MinPrice"),
                           tempObj.getBoolean("Direct"),
                           carrierObj.getInt("OriginId"),
                           carrierObj.getInt("DestinationId"),
                           carrierObj.getJSONArray("CarrierIds").getInt(0)
                   );

                   quotesList[i].setPlaces(placeList);
                   quotesList[i].setCarrier(carrierList);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }
}
