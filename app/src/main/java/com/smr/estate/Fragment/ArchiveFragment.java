package com.smr.estate.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smr.estate.Adapter.ExpireAdapter;
import com.smr.estate.Adapter.OnLoadMoreListener;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Model.Expires;
import com.smr.estate.Network.EstateExpireRequest;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.MaterialLoading.MaterialProgressBar;

public class ArchiveFragment extends Fragment
{
    private TextView tvNoData;
    private RecyclerView recyclerPostItems;
    private MaterialProgressBar progressBar;

    private ExpireAdapter adapter;
    private ArrayList<Expires> postItems = new ArrayList<>();
    private int page = 1;

    private int loadingItemIndex = 0;

    private SwipeRefreshLayout swipeRefresh;
    private String user_id;

    public ArchiveFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_archive, container, false);

        user_id = Read.readDataFromStorage("user_id", "0", getContext());

        initialize(view);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    private void initialize(View view)
    {
        findView(view);
        setupFragment();
        setUpListener();
        getPosts();
    }

    private void findView(View view)
    {
        tvNoData = view.findViewById(R.id.tvNoData);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerPostItems = view.findViewById(R.id.recyclerPostItems);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
    }

    private void setUpListener()
    {
        adapter.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                page++;
                getPosts();
                postItems.add(null);
                loadingItemIndex = postItems.size() - 1;
                adapter.notifyItemInserted(loadingItemIndex);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                recyclerPostItems.setVisibility(View.GONE);
                tvNoData.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                page = 1;
                postItems.clear();
                adapter.notifyDataSetChanged();

                getPosts();
            }
        });
    }

    private void setupFragment()
    {
        recyclerPostItems.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpireAdapter(getContext(), postItems, recyclerPostItems);
        recyclerPostItems.setAdapter(adapter);

        recyclerPostItems.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getPosts()
    {
        final String url = Constant.BASE_URL + "expireList";

        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put(Constant.CONS_ID, user_id);
            jsonData.put(Constant.PAGE, page);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Response.Listener<ArrayList<Expires>> listener = new Response.Listener<ArrayList<Expires>>()
        {
            @Override
            public void onResponse(ArrayList<Expires> response)
            {
                try
                {
                    if (page != 1)
                    {
                        postItems.remove(loadingItemIndex);
                        adapter.notifyItemRemoved(postItems.size());
                    }
                    if (response != null)
                    {
                        postItems.addAll(response);
                        adapter.notifyDataSetChanged();
                        recyclerPostItems.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        adapter.setLoading(false);
                        swipeRefresh.setRefreshing(false);
                    } else
                    {
                        if (page == 1)
                        {
                            recyclerPostItems.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e)
                {
                    Log.i(G.TAG, e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }
        };

        EstateExpireRequest request = new EstateExpireRequest(url, jsonData, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                return hashMap;
            }
        };

        AppSingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}
