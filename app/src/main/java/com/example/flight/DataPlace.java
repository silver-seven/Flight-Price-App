package com.example.flight;

public class DataPlace {
    int placeId;
    String iataCode;
    String name;
    String type;
    String skyCode;
    String cityName;
    String cityId;
    String countryName;

    public DataPlace()
    {
        placeId = 0;
        iataCode = "";
        name = "";
        type = "";
        skyCode = "";
        cityName = "";
        cityId = "";
        countryName = "";
    }

    public DataPlace(int placeId, String iataCode, String name, String type, String skyCode, String cityName, String cityId, String countryName)
    {
        this.placeId = placeId;
        this.iataCode = iataCode;
        this.name = name;
        this.type = type;
        this.skyCode = skyCode;
        this.cityName = cityName;
        this.cityId = cityId;
        this.countryName = countryName;
    }

    public String getNameFromPlaceID(int id)
    {
        String out = "NONE";
        if(id == placeId)
        {
            out = name;
        }
        return out;
    }

    public int getId()
    {
        return placeId;
    }
}
