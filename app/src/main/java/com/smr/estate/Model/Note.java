package com.smr.estate.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;

public class Note extends RealmObject
{

    /**
     * id : 1
     * cons_id : 8
     * event_date : 2018-12-01
     * event : 1
     * created_at : 2018-12-02 12:37:52
     * updated_at : 2018-12-02 12:37:52
     */

    private int id;
    private int cons_id;
    private String event_date;
    private String event;
    private String created_at;
    private String updated_at;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCons_id()
    {
        return cons_id;
    }

    public void setCons_id(int cons_id)
    {
        this.cons_id = cons_id;
    }

    public String getEvent_date()
    {
        return event_date;
    }

    public void setEvent_date(String event_date)
    {
        this.event_date = event_date;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return getDateDuration(updated_at);
    }

    public void setUpdated_at(String updated_at)
    {
        this.updated_at = updated_at;
    }

    //Function for set character date
    public static String getDateDuration(String date)
    {
        String postReleaseDate = date.replace("T", " ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(c.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        try
        {
            d1 = sdf.parse(postReleaseDate);
            d2 = sdf.parse(currentDate);
        } catch (ParseException var12)
        {
            var12.printStackTrace();
        }

        long diffIn = d2.getTime() - d1.getTime();
        int diff = (int) TimeUnit.MINUTES.convert(diffIn, TimeUnit.MILLISECONDS);
        String result = "";
        if (diff <= 59)
        {
            result = diff + " دقیقه";
        } else if (diff <= 1439)
        {
            result = diff / 60 + " ساعت";
        } else if (diff <= 10079)
        {
            result = diff / 1440 + " روز";
        } else if (diff >= 10080)
        {
            result = diff / 10080 + " هفته";
        }

        result = result + " پیش";
        return result;
    }
}
