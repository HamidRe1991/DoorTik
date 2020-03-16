package com.smr.estate.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;

public class PostEstate extends RealmObject
{
    /**
     * id : 56
     * name : ملک
     * breadth : 200
     * price : 2,500,000,000
     * rent : 0
     * geo_loc : 0,0
     * add : کرج
     * area : 1
     * cons_id : 4
     * seller_name : محمد
     * tell : 02632216519
     * image :
     * discription : null
     * type : 1
     * status : 1
     * official_doc : 0
     * expire : 0
     * deleted : 0
     * created_at : 2018-11-16 00:00:00
     * updated_at : 2018-11-16 00:00:00
     */

    private int id;
    private String name;
    private int breadth;
    private String price;
    private String rent;
    private String geo_lat;
    private String geo_lon;
    private String add;
    private int area;
    private int cons_id;
    private String seller_name;
    private String tell;
    private String image;
    private String discription;
    private int type;
    private int status;
    private int official_doc;
    private int expire;
    private int deleted;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getBreadth()
    {
        return breadth;
    }

    public void setBreadth(int breadth)
    {
        this.breadth = breadth;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getRent()
    {
        return rent;
    }

    public void setRent(String rent)
    {
        this.rent = rent;
    }

    public String getGeo_lat()
    {
        return geo_lat;
    }

    public void setGeo_lat(String geo_lat)
    {
        this.geo_lat = geo_lat;
    }

    public String getGeo_lon()
    {
        return geo_lon;
    }

    public void setGeo_lon(String geo_lon)
    {
        this.geo_lon = geo_lon;
    }

    public String getAdd()
    {
        return add;
    }

    public void setAdd(String add)
    {
        this.add = add;
    }

    public int getArea()
    {
        return area;
    }

    public void setArea(int area)
    {
        this.area = area;
    }

    public int getCons_id()
    {
        return cons_id;
    }

    public void setCons_id(int cons_id)
    {
        this.cons_id = cons_id;
    }

    public String getSeller_name()
    {
        return seller_name;
    }

    public void setSeller_name(String seller_name)
    {
        this.seller_name = seller_name;
    }

    public String getTell()
    {
        return tell;
    }

    public void setTell(String tell)
    {
        this.tell = tell;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Object getDiscription()
    {
        return discription;
    }

    public void setDiscription(String discription)
    {
        this.discription = discription;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getOfficial_doc()
    {
        return official_doc;
    }

    public void setOfficial_doc(int official_doc)
    {
        this.official_doc = official_doc;
    }

    public int getExpire()
    {
        return expire;
    }

    public void setExpire(int expire)
    {
        this.expire = expire;
    }

    public int getDeleted()
    {
        return deleted;
    }

    public void setDeleted(int deleted)
    {
        this.deleted = deleted;
    }

    public String getCreated_at()
    {
        return getDateDuration(created_at);
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
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
