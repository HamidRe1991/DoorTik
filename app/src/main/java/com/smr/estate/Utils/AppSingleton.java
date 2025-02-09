package com.smr.estate.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

//Class for volley request

public class AppSingleton
{
    private static AppSingleton mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private String TAG = "";

    private AppSingleton(Context context)
    {
        mContext = context;
        mRequestQueue = getRequestQueue();
        VolleyLog.DEBUG = true;
    }

    public static synchronized AppSingleton getInstance(Context context)
    {
        if (mAppSingletonInstance == null)
        {
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelPendingRequests()
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(TAG);
        }
    }
}