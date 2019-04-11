package com.example.flight;

public class DataCarrier {
    int carrierId;
    String carrierName;

    public DataCarrier(int carrierId, String carrierName)
    {
        this.carrierId = carrierId;
        this.carrierName = carrierName;
    }

    public String getNameFromId(int id)
    {
        String temp = "NONE";
        if(id == carrierId)
        {
            temp = carrierName;
        }
        return temp;
    }
}
