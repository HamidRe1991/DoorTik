package com.smr.estate.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Expires extends RealmObject
{
    /**
     * estate : {"id":17,"name":"j","breadth":6,"price":"3","rent":"","geo_loc":"50.98703574206134,35.80779411918505","add":"j","area":1,"cons_id":3,"seller_name":"j","tell":"8","image":"estates/j11-42-110.jpg","discription":"b","type":2,"status":1,"official_doc":0,"expire":1,"deleted":0,"created_at":"2018-11-24 11:42:11","updated_at":"2018-11-24 11:42:11"}
     * image : {"image_1":"estates/j11-42-110.jpg"}
     * sell : [{"name":"معاوضه","sell_id":5}]
     * offer : [{"name":"لحظه آخری","offer_id":2}]
     * expire : {"id":2,"commission":"320000","expire_date":"2018-11-25 00:00:00"}
     */

    private PostEstate estate;
    private PostImage image;
    private PostExpire expire;
    private RealmList<PostSell> sell;

    public PostEstate getEstate()
    {
        return estate;
    }

    public void setEstate(PostEstate estate)
    {
        this.estate = estate;
    }

    public PostImage getImage()
    {
        return image;
    }

    public void setImage(PostImage image)
    {
        this.image = image;
    }

    public PostExpire getExpire()
    {
        return expire;
    }

    public void setExpire(PostExpire expire)
    {
        this.expire = expire;
    }

    public RealmList<PostSell> getSell()
    {
        return sell;
    }

    public void setSell(RealmList<PostSell> sell)
    {
        this.sell = sell;
    }
}
