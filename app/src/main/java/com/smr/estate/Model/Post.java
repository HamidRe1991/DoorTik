package com.smr.estate.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Post extends RealmObject
{
    private PostEstate estate;
    private PostImage image;
    private RealmList<PostSell> sell;
    private RealmList<PostOffer> offer;

    /**
     * estate : {"id":56,"name":"ملک","breadth":200,"price":"2,500,000,000","rent":"0","geo_loc":"0,0","add":"کرج","area":1,"cons_id":4,"seller_name":"محمد","tell":"02632216519","image":"image/01.jpg image/02.jpg image/03.jpg","discription":null,"type":1,"status":1,"official_doc":0,"expire":0,"deleted":0,"created_at":"2018-11-16 00:00:00","updated_at":"2018-11-16 00:00:00"}
     * poss : []
     * sell : [{"name":"خرید","sell_id":1}]
     * offer : []
     */

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

    public RealmList<PostSell> getSell()
    {
        return sell;
    }

    public void setSell(RealmList<PostSell> sell)
    {
        this.sell = sell;
    }

    public RealmList<PostOffer> getOffer()
    {
        return offer;
    }

    public void setOffer(RealmList<PostOffer> offer)
    {
        this.offer = offer;
    }
}
