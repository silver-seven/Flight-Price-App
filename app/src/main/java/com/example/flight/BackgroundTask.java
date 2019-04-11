package com.example.flight;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.widget.TextView;

public class BackgroundTask extends AsyncTask
{
    MainActivity in;
    RequestPlace place;
    RequestHandler handler;
    String msg;
    TextView dataView;
    public BackgroundTask(MainActivity in, RequestPlace place, TextView msg)
    {
       this.in = in;
       this.place = place;
       this.dataView = msg;

    }

    @Override
    protected Object doInBackground(Object[] objects)
    {
        RequestHandler h = new RequestHandler(in, dataView);
        h.sendPlace(place);
        while(h.getWaiting())
        {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        in.setData(msg);
    }
}
