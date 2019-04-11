package com.example.flight;

import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    String key;
    MainActivity mainAct;
    String  msg;
    TextView text;
    String link;
    Boolean waiting;
    public RequestHandler()
    {
        link = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2019-09-01";
    }
    public RequestHandler(MainActivity m, TextView v)
    {
        text = v;
        mainAct = m;
        this.key = key;
        link = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2019-09-01";
    }

    public RequestHandler(MainActivity m, String v)
    {
        msg = v;
        mainAct = m;
        this.key = key;
        link = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2019-09-01";
    }

    public void sendPlace(RequestPlace place)
    {
        link = place.getUrl();
        sendRequest("s");
    }

    public void sendRequest(String http)
    {
        RequestQueue queue = Volley.newRequestQueue(mainAct);
        String url = link;
        msg = null;
        waiting = true;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //text.setText(response);
                        msg = response;
                        waiting = false;
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                       // text.setText("error");
                        msg = "error";
                waiting = false;
            }

        })
        {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("X-RapidAPI-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
                headers.put("X-RapidAPI-Key", "");
                return headers;
            }
        };
        queue.add(request);
        queue.start();
    }
    public Boolean getWaiting()
    {
        return waiting;
    }

    public String getMsg()
    {
        return msg;
    }


}
