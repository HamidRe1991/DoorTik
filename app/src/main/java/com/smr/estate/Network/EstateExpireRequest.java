package com.smr.estate.Network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.smr.estate.Model.Expires;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class EstateExpireRequest extends Request<ArrayList<Expires>>
{
    private Response.Listener<ArrayList<Expires>> mListener;
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private final String mRequestBody;

    public EstateExpireRequest(String url, JSONObject jsonRequest, Response.Listener<ArrayList<Expires>> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mRequestBody = jsonRequest.toString();
    }

    @Override
    public byte[] getBody()
    {
        try
        {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee)
        {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    protected Response<ArrayList<Expires>> parseNetworkResponse(NetworkResponse response)
    {
        ArrayList<Expires> postItems = new ArrayList<>();
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONArray array = new JSONArray(json);
            try
            {
                array.getJSONObject(0).getString("user_state");
                postItems = null;
            } catch (Exception e)
            {
                Gson gson = new Gson();
                Expires[] posts = gson.fromJson(array.toString(), Expires[].class);
                Collections.addAll(postItems, posts);
            }
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            postItems = null;
        } catch (JSONException e)
        {
            e.printStackTrace();
            postItems = null;
        }

        return Response.success(postItems, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(ArrayList<Expires> response)
    {
        mListener.onResponse(response);
    }
}
