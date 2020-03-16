package com.smr.estate.Model;

import io.realm.RealmObject;

public class PostSell extends RealmObject
{
    /**
     * name : خرید
     * sell_id : 1
     */

    private String name;
    private int sell_id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSell_id()
    {
        return sell_id;
    }

    public void setSell_id(int sell_id)
    {
        this.sell_id = sell_id;
    }
}
