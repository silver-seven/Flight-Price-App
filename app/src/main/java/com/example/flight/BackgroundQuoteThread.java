package com.example.flight;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

public class BackgroundQuoteThread extends AsyncTask {

    MainActivity in;
    Spinner comboOut;
    Spinner comboIn;
    RequestQuote quote;
    RequestHandler handler;
    String msg;
    RequestPlace origin;
    RequestPlace dest;
    TextView dataView;
    Boolean ready;
    String outDate;
    String inDate;
    String sorigin;
    String sdest;

    RequestHandler h;
    RequestHandler h2;
    RequestHandler h3;

    DataQuote[] quoteList;
    JSONParser parser2;

    Boolean success;
    public BackgroundQuoteThread(MainActivity in, TextView inText, RequestPlace origin, RequestPlace dest, String outDate, String inDate)
    {
        this.in = in;
        dataView = inText;
        this.origin = origin;
        this.dest = dest;
        ready = false;
        this.outDate = outDate;
        this.inDate = inDate;
        success = false;
    }

    public BackgroundQuoteThread(MainActivity in, TextView inText, String origin, String dest, String outDate, String inDate)
    {
        this.in = in;
        dataView = inText;
        this.sorigin = origin;
        this.sdest = dest;
        ready = false;
        this.outDate = outDate;
        this.inDate = inDate;
        success = false;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        ready = false;
        success = false;
            RequestQuote quote = new RequestQuote(outDate, inDate, sdest, sorigin);

            RequestHandler request = new RequestHandler(in, dataView);
            String url = quote.getUrl();
            request.sendRequest(url);
            while (request.getWaiting()) {

            }
            msg = request.getMsg();
            if(!msg.isEmpty()) {
                if (!msg.equals("error")) {
                    parser2 = new JSONParser(in);
                    Log.d("FLIGHT", msg);
                    parser2.parseQuotes(msg);
                    quoteList = parser2.getQuotes();
                    success = true;
                }
            }
        return null;
    }
    @Override
    protected void onPostExecute(Object o)
    {
        ready = true;
        if(success)
            in.openDataActivity(quoteList, msg);
    }
}
