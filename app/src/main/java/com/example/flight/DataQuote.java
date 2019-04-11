package com.example.flight;

public class DataQuote {
    int quoteId;
    float minPrice;
    Boolean direct;
    String origin;
    String dest;
    String carrier;
    int originId;
    int destId;
    int carrierId;

    public DataQuote()
    {

    }
    public DataQuote(int quoteId, float minPrice, Boolean direct, int originId, int destId, int carrierId)
    {
        this.quoteId = quoteId;
        this.minPrice = minPrice;
        this.direct = direct;
        this.originId = originId;
        this.destId = destId;
        this.carrierId = carrierId;
    }


    public void set(int quoteId, float minPrice, Boolean direct, int originId, int destId, int carrierId)
    {
        this.quoteId = quoteId;
        this.minPrice = minPrice;
        this.direct = direct;
        this.originId = originId;
        this.destId = destId;
        this.carrierId = carrierId;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public void setDest(String dest)
    {
        this.dest = dest;
    }

    public void setPlaces(DataPlace[] places)
    {
        for(int i=0; i<places.length; i++)
        {
            String temp = places[i].getNameFromPlaceID(originId);
            if(temp != "NONE")
            {
                origin = temp;
                break;
            }
        }
        for(int i=0; i<places.length; i++)
        {

            String temp = places[i].getNameFromPlaceID(destId);
            if(temp != "NONE")
            {
                dest = temp;
                break;
            }
        }
    }

    public void setCarrier(DataCarrier[] carriers)
    {
        for(int i=0; i<carriers.length; i++)
        {
            String temp = carriers[i].getNameFromId(carrierId);
            if(temp != "NONE")
            {
                carrier = temp;
                break;
            }
        }
    }
}
