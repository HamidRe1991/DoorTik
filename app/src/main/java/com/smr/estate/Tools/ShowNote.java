package com.smr.estate.Tools;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smr.estate.Adapter.NoteAdapter;
import com.smr.estate.Adapter.OnLoadMoreListener;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Model.Note;
import com.smr.estate.Network.EstateNoteRequest;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.MaterialLoading.MaterialProgressBar;

public class ShowNote extends AppCompatActivity
{
    private FloatingActionButton floatingActionButton;
    private TextView tvNoData;
    private RecyclerView recyclerPostItems;
    private MaterialProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private ImageView imgBack;

    private NoteAdapter adapter;
    private ArrayList<Note> postItems = new ArrayList<>();
    private int page = 1;
    private int loadingItemIndex = 0;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initialize();
    }

    private void initialize()
    {
        findView();
        setupFragment();
        setUpListener();
        getNote();
    }

    private void findView()
    {
        imgBack = findViewById(R.id.imgNoteBack);
        floatingActionButton = findViewById(R.id.newNote);
        tvNoData = findViewById(R.id.tvNoData);
        progressBar = findViewById(R.id.progressBar);
        recyclerPostItems = findViewById(R.id.recyclerNoteItems);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }

    private void setUpListener()
    {
        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        adapter.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                page++;
                getNote();
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

                getNote();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ShowNote.this, CreateNote.class));
                ShowNote.this.finish();
            }
        });
    }

    private void setupFragment()
    {
        user_id = Read.readDataFromStorage("user_id", "0", this);

        recyclerPostItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this, postItems, recyclerPostItems);
        recyclerPostItems.setAdapter(adapter);

        recyclerPostItems.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getNote()
    {
        final String url = Constant.BASE_URL + "allEvent";

        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put(Constant.CONS_ID, user_id);
            jsonData.put(Constant.PAGE, page);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Response.Listener<ArrayList<Note>> listener = new Response.Listener<ArrayList<Note>>()
        {
            @Override
            public void onResponse(ArrayList<Note> response)
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
                Log.i(G.TAG, "onErrorResponse: " + error.getMessage());
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }
        };

        EstateNoteRequest request = new EstateNoteRequest(url, jsonData, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                return hashMap;
            }
        };

        AppSingleton.getInstance(this).addToRequestQueue(request);
    }
}
