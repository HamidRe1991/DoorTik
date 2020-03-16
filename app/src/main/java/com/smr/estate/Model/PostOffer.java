package com.smr.estate.Model;

import io.realm.RealmObject;

public class PostOffer extends RealmObject
{
    /**
     * name : لحظه آخری
     * offer_id : 2
     */

    private String name;
    private int offer_id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOffer_id()
    {
        return offer_id;
    }

    public void setOffer_id(int offer_id)
    {
        this.offer_id = offer_id;
    }
}
