package com.example.flight;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.List;
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
    Spinner comboOut;
    Spinner comboIn;
    List<String> table;
    int boundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize components
        button = findViewById(R.id.button);
        dataView = findViewById(R.id.dataView);
        locationInput = findViewById(R.id.locationText);
        destinationInput = findViewById(R.id.destinationText);
        outBound = findViewById(R.id.dateOut);
        inBound = findViewById(R.id.dateIn);
        comboOut = findViewById(R.id.comboOut);
        comboIn = findViewById(R.id.comboIn);
        calendar = findViewById(R.id.calendarView);
       // calendar.setVisibility(View.INVISIBLE);
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
                calendar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0 ));
               // calendar.setVisibility(View.INVISIBLE);
            }
        });
    }

    Boolean debounce = false;
    public void buttonCheckHandler(View v){

        if(!debounce)    // only one button press
        {
            debounce = true;
            RequestPlace rdest = new RequestPlace();
            rdest.setQuery(locationInput.getText().toString());
            RequestPlace rsrc = new RequestPlace();
            rsrc.setQuery(destinationInput.getText().toString());

            BackgroundPlaceThread quote = new BackgroundPlaceThread(this, dataView, rsrc, rdest);
            quote.execute();

            debounce = false;
        }
    }
    Boolean debounce2 = false;
    public void buttonHandler(View v){

        if(!debounce2)    // only one button press
        {
            debounce2 = true;
            RequestPlace rdest = new RequestPlace();
            rdest.setQuery(locationInput.getText().toString());
            RequestPlace rsrc = new RequestPlace();
            rsrc.setQuery(destinationInput.getText().toString());

            String[] cIn= comboIn.getSelectedItem().toString().split(":");
            String[] cOut = comboOut.getSelectedItem().toString().split(":");
            BackgroundQuoteThread quote = new BackgroundQuoteThread(this, dataView, cIn[0], cOut[0], outBound.getText().toString(), inBound.getText().toString());
            quote.execute();

            debounce2 = false;
        }
    }

    public void dateHandler(View v)
    {
        boundId = v.getId();
        calendar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT ));
        //calendar.setVisibility(View.VISIBLE);
    }

    public void setData(String m)
    {
        JSONParser jp = new JSONParser(this);
        dataView.setText(jp.parsePlace(dataView.getText().toString()));
    }

    public void openDataActivity(DataQuote[] quoteList, String msg)
    {
        Intent data = new Intent(this, DataActivity.class);
        data.putExtra("quotes", msg);
        startActivity(data);
    }

    public void setComboBox(List<String> list, int combo)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_items, list);

        adapter.setDropDownViewResource(R.layout.spinner_items);
        Spinner items;
        if(combo == 1)
            items = comboOut;
        else
            items = comboIn;
        items.setAdapter(adapter);
    }


}
