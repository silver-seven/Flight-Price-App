package com.example.flight;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class BackgroundQuoteThread extends AsyncTask {

    MainActivity in;
    RequestQuote quote;
    RequestHandler handler;
    String msg;
    RequestPlace origin;
    RequestPlace dest;
    TextView dataView;
    Boolean ready;
    String outDate;
    String inDate;

    RequestHandler h;
    RequestHandler h2;
    RequestHandler h3;
    public BackgroundQuoteThread(MainActivity in, TextView inText, RequestPlace origin, RequestPlace dest, String outDate, String inDate)
    {
        this.in = in;
        dataView = inText;
        this.origin = origin;
        this.dest = dest;
        ready = false;
        this.outDate = outDate;
        this.inDate = inDate;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        ready = false;
        h = new RequestHandler(in, dataView);
        h.sendPlace(dest);
        while(h.getWaiting())
        {

        }
        String destMsg = h.getMsg();

        JSONParser parser = new JSONParser();

        h2 = new RequestHandler(in, dataView);
        h2.sendPlace(origin);
        while(h2.getWaiting())
        {

        }
        String originMsg = h2.getMsg();
        String parseOrigin = parser.parsePlace(originMsg);
        String parseDest = parser.parsePlace(destMsg);
        Log.d("FLIGHT", parseOrigin);
        Log.d("FLIGHT", parseDest);
        RequestQuote quote = new RequestQuote(outDate, inDate, parseDest, parseOrigin);

        h3 = new RequestHandler(in, dataView);
        h3.sendRequest(quote.getUrl());
        while(h3.getWaiting())
        {

        }
        msg = h3.getMsg();
        Log.d("FLIGHT", msg);
        return null;
    }
    @Override
    protected void onPostExecute(Object o)
    {
        JSONParser parser = new JSONParser();
        parser.parseQuotes(msg);
        ready = true;
    }
}
