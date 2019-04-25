package com.example.flight;

import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class BackgroundPlaceThread extends AsyncTask {


    MainActivity in;
    Spinner comboOut;
    Spinner comboIn;
    String msg;
    RequestPlace origin;
    RequestPlace dest;
    TextView dataView;
    Boolean ready;


    RequestHandler h;
    RequestHandler h2;

    List<String> list1;
    List<String> list2;

    Boolean success;
    public BackgroundPlaceThread(MainActivity in, TextView inText, RequestPlace origin, RequestPlace dest)
    {
        this.in = in;
        dataView = inText;
        this.origin = origin;
        this.dest = dest;
        ready = false;

        success = false;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        ready = false;
        success = false;
        h = new RequestHandler(in, dataView);
        h.sendPlace(dest);
        while(h.getWaiting())
        {

        }
        String destMsg = h.getMsg();
        ///error checker ADD!!!
        JSONParser parser = new JSONParser(in);

        h2 = new RequestHandler(in, dataView);
        h2.sendPlace(origin);
        while(h2.getWaiting())
        {

        }
        String originMsg = h2.getMsg();
        if(!originMsg.equals("error") && !destMsg.equals("error")) {
            list1 = parser.parsePlace(originMsg, 0);
            list2 = parser.parsePlace(destMsg, 1);
            success = true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        ready = true;
        if(success)
        {
            in.setComboBox(list1, 0);
            in.setComboBox(list2, 1);
        }
    }
}
