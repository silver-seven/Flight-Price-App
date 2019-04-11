package com.example.flight;

public class RequestPlace {

    private String query;
    private String country;
    private String currency;
    private String local;
    private String url;
    private String msg;

    public RequestPlace()
    {
        query = "japan";
        country = "US";
        currency = "USD";
        local = "en-US";
        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/";
    }

    public RequestPlace(String query, String country, String currency, String local)
    {
        this.query = query;
        this.country = country;
        this.currency = currency;
        this.local = local;
        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/"
                + country + "/"
                + currency + "/"
                + local + "/"
                + "?query="
                + query;
    }

    public String getUrl()
    {
        return url;
    }

    public void setQuery(String query) {
        this.query = query;
        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/"
                + country + "/"
                + currency + "/"
                + local + "/"
                + "?query="
                + query;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
