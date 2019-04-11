package com.example.flight;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    Button button;
    TextView dataView;
    TextView outBound;
    TextView inBound;
    TextInputEditText locationInput;
    TextInputEditText destinationInput;
    String msg;
    CalendarView calendar;
    int boundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        dataView = findViewById(R.id.dataView);
        locationInput = findViewById(R.id.locationText);
        destinationInput = findViewById(R.id.destinationText);
        outBound = findViewById(R.id.dateOut);
        inBound = findViewById(R.id.dateIn);

        calendar = findViewById(R.id.calendarView);
        calendar.setVisibility(View.INVISIBLE);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                String date = year + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",dayOfMonth);

                if(boundId == R.id.dateOut)
                {
                    outBound.setText(String.valueOf(date));
                }
                else
                {
                    inBound.setText(String.valueOf(date));
                }
                calendar.setVisibility(View.INVISIBLE);
            }
        });
    }

    Boolean debounce = false;
    Boolean originRdy = false;
    Boolean destRdy = false;
    String originPMsg;
    String destPMsg;
    public void buttonHandler(View v){

        if(!debounce)    // only one button press
        {
            debounce = true;
            RequestPlace rdest = new RequestPlace();
            rdest.setQuery(destinationInput.getText().toString());
            RequestPlace rsrc = new RequestPlace();
            rsrc.setQuery(locationInput.getText().toString());

            BackgroundQuoteThread quote = new BackgroundQuoteThread(this, dataView, rsrc, rdest, outBound.getText().toString(), inBound.getText().toString());
            quote.execute();

            debounce = false;
        }



    }

    public void dateHandler(View v)
    {
        boundId = v.getId();
        calendar.setVisibility(View.VISIBLE);
    }

    public void setData(String m)
    {
        JSONParser jp = new JSONParser();
        dataView.setText(jp.parsePlace(dataView.getText().toString()));
    }

    public void sendRequest()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2019-09-01";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataView.setText(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataView.setText("That didn't work!");
            }

        }) //close param
        {   //body

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("X-RapidAPI-Key", "c586b06cbemsh55b180d6b7855d9p123a23jsn12353804c762");
                return headers;
            }
        };
        queue.add(stringRequest);



    }
}

















/*
        public void buttonHandler(View v) throws IOException {



        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2019-09-01";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText(response);
                    }

                }, new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }

        }) //close param
        {   //body


            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("X-RapidAPI-Key", "c586b06cbemsh55b180d6b7855d9p123a23jsn12353804c762");
                return headers;
            }
        };
        queue.add(stringRequest);


    }
}
*/