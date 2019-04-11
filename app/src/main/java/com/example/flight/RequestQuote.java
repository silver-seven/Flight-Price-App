package com.example.flight;

public class RequestQuote {

    private String country;
    private String currency;
    private String local;

    private String outDate;
    private String inDate;
    private String outPort;
    private String inPort;

    private String url;
    private String msg;

    public RequestQuote()
    {
        country = "US";
        currency = "USD";
        local = "en-US";
        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browseruotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2019-09-01";
    }

    public RequestQuote(String outDate, String inDate, String outPort, String inPort)
    {
        country = "US";
        currency = "USD";
        local = "en-US";
        this.outDate = outDate;
        this.inDate = inDate;
        this.outPort = outPort;
        this.inPort = inPort;
        this.url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browseruotes/v1.0/"
                + country + "/"
                + currency + "/"
                + local + "/"
                + outPort + "/"
                + inPort + "/"
                + outDate + "?inboundpartialdate="
                + inDate;
    }

    public String getUrl()
    {
        return url;
    }
}
