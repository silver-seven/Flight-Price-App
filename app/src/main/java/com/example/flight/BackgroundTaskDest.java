package com.example.flight;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class BackgroundTaskDest extends AsyncTask
{
    MainActivity in;
    RequestPlace place;
    RequestHandler handler;
    String msg;
    TextView dataView;
    Boolean ready;
    public BackgroundTaskDest(MainActivity in, RequestPlace place, TextView msg, Boolean ready)
    {
        this.in = in;
        this.place = place;
        this.dataView = msg;
        this.ready = ready;

    }

    @Override
    protected Object doInBackground(Object[] objects)
    {
        ready = false;
        RequestHandler h = new RequestHandler(in, dataView);
        h.sendPlace(place);
        while(h.getWaiting())
        {

        }
        msg = h.getMsg();
        return null;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        Log.d("FLIGHT", msg);
        in.setData(msg);
        ready = true;
    }

    public Boolean getReady()
    {
        return ready;
    }

    public String getMsg()
    {
        return msg;
    }
}
