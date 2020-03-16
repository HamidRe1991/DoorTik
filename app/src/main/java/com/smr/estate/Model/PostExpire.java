package com.smr.estate.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;

public class PostExpire extends RealmObject
{
    /**
     * id : 2
     * commission : 320000
     * expire_date : 2018-11-25 00:00:00
     */

    private int id;
    private String commission;
    private String expire_date;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCommission()
    {
        return commission;
    }

    public void setCommission(String commission)
    {
        this.commission = commission;
    }

    public String getExpire_date()
    {
        return getDateDuration(expire_date);
    }

    public void setExpire_date(String expire_date)
    {
        this.expire_date = expire_date;
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
