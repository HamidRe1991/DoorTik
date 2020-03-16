package com.smr.estate.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BuildingPostDetails
{
    /**
     * estate : {"id":16,"name":"jdjjdjjxjd","breadth":8,"price":"65","rent":"","geo_lat":"NO_LOCATION","geo_lon":"NO_LOCATION","add":"jsjdj","area":1,"cons_id":11,"agent_id":null,"seller_name":"jjsjdj","tell":"84848","image":"estates/jdjjdjjxjd21-45-510.jpg","built_in":"6","discription":"jsjdj","type":5,"status":1,"official_doc":1,"expire":0,"deleted":0,"created_at":"2018-12-08 21:45:51","updated_at":"2018-12-08 21:45:51","city_id":1,"province":1,"app_user":2}
     * image : {"image_1":"estates/jdjjdjjxjd21-45-510.jpg"}
     * poss : [{"poss_id":1,"value":"0"},{"poss_id":2,"value":"0"},{"poss_id":3,"value":"0"},{"poss_id":4,"value":"0"},{"poss_id":5,"value":"0"},{"poss_id":6,"value":"0"},{"poss_id":7,"value":"0"},{"poss_id":8,"value":"0"}]
     * sell : [{"name":"فروش","sell_id":1}]
     * offer : [{"name":"پیشنهاد ویژه","offer_id":1}]
     */

    private Estate estate;
    private Image image;
    private List<Poss> poss;
    private List<Sell> sell;
    private List<Offer> offer;

    public String getBookmark()
    {
        return bookmark;
    }

    public void setBookmark(String bookmark)
    {
        this.bookmark = bookmark;
    }

    private String bookmark;

    public Estate getEstate()
    {
        return estate;
    }

    public void setEstate(Estate estate)
    {
        this.estate = estate;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public List<Poss> getPoss()
    {
        return poss;
    }

    public void setPoss(List<Poss> poss)
    {
        this.poss = poss;
    }

    public List<Sell> getSell()
    {
        return sell;
    }

    public void setSell(List<Sell> sell)
    {
        this.sell = sell;
    }

    public List<Offer> getOffer()
    {
        return offer;
    }

    public void setOffer(List<Offer> offer)
    {
        this.offer = offer;
    }

    public static class Estate
    {
        /**
         * id : 16
         * name : jdjjdjjxjd
         * breadth : 8
         * price : 65
         * rent :
         * geo_lat : NO_LOCATION
         * geo_lon : NO_LOCATION
         * add : jsjdj
         * area : 1
         * cons_id : 11
         * agent_id : null
         * seller_name : jjsjdj
         * tell : 84848
         * image : estates/jdjjdjjxjd21-45-510.jpg
         * built_in : 6
         * discription : jsjdj
         * type : 5
         * status : 1
         * official_doc : 1
         * expire : 0
         * deleted : 0
         * created_at : 2018-12-08 21:45:51
         * updated_at : 2018-12-08 21:45:51
         * city_id : 1
         * province : 1
         * app_user : 2
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
        private Object agent_id;
        private String seller_name;
        private String tell;
        private String image;
        private String built_in;
        private String discription;
        private int type;
        private int status;
        private int official_doc;
        private int expire;
        private int deleted;
        private String created_at;
        private String updated_at;
        private int city_id;
        private int province;
        private int app_user;

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

        public Object getAgent_id()
        {
            return agent_id;
        }

        public void setAgent_id(Object agent_id)
        {
            this.agent_id = agent_id;
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

        public String getBuilt_in()
        {
            return built_in;
        }

        public void setBuilt_in(String built_in)
        {
            this.built_in = built_in;
        }

        public String getDiscription()
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

        public int getCity_id()
        {
            return city_id;
        }

        public void setCity_id(int city_id)
        {
            this.city_id = city_id;
        }

        public int getProvince()
        {
            return province;
        }

        public void setProvince(int province)
        {
            this.province = province;
        }

        public int getApp_user()
        {
            return app_user;
        }

        public void setApp_user(int app_user)
        {
            this.app_user = app_user;
        }
    }

    public static class Image
    {
        /**
         * image_1 : estates/jdjjdjjxjd21-45-510.jpg
         */

        private String image_1;

        public String getImage_1()
        {
            return image_1;
        }

        public void setImage_1(String image_1)
        {
            this.image_1 = image_1;
        }
    }

    public static class Poss
    {
        /**
         * poss_id : 1
         * value : 0
         */

        private String name;
        private String value;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
    }

    public static class Sell
    {
        /**
         * name : فروش
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

    public static class Offer
    {
        /**
         * name : پیشنهاد ویژه
         * offer_id : 1
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
