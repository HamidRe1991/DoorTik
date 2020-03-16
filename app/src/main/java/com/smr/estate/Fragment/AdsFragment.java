package com.smr.estate.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.smr.estate.Activities.MainActivity;
import com.smr.estate.Activities.RegisterActivity;
import com.smr.estate.Adapter.OnLoadMoreListener;
import com.smr.estate.Dialog.Confirmation;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Activities.CreatePostActivity;
import com.smr.estate.Adapter.PostsAdapter;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Dialog.FilterActivity;
import com.smr.estate.Model.Post;
import com.smr.estate.Network.EstatePostRequest;
import com.smr.estate.Storage.Read;
import com.smr.estate.Storage.Write;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.MaterialLoading.MaterialProgressBar;

import static com.smr.estate.Constant.Constant.RESULT_OK;

public class AdsFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private RelativeLayout rltFilter;

    private TextView tvNoData;
    private RecyclerView recyclerPostItems;
    private MaterialProgressBar progressBar;

    private PostsAdapter adapter;
    private ArrayList<Post> postItems = new ArrayList<>();
    private int page = 1;
    private View headerFilter, headerNoInternetConnection;

    private int loadingItemIndex = 0;

    private SwipeRefreshLayout swipeRefresh;
    private JSONObject jsonFilter = null;
    private String user_id;
    private ImageView imgVoiceSearch;
    private EditText edtVoiceSearch;
    private static int REQUEST_VOICE_SEARCH = 152;

    private int getRole;

    public AdsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ads, container, false);

        initialize(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    private void initialize(View view) {
        findView(view);
        setupFragment();
        setUpListener();
        getPosts();
    }

    private void findView(View view) {
        floatingActionButton = view.findViewById(R.id.newAds);
        rltFilter = view.findViewById(R.id.rltFilter);
        tvNoData = view.findViewById(R.id.tvNoData);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerPostItems = view.findViewById(R.id.recyclerPostItems);
        headerFilter = view.findViewById(R.id.headerFilter);
        headerNoInternetConnection = view.findViewById(R.id.headerNoInternetConnection);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        imgVoiceSearch = view.findViewById(R.id.imgVoiceSearch);
        edtVoiceSearch = view.findViewById(R.id.edtVoiceSearch);
    }

    private void setUpListener() {
        rltFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                intent.putExtra(Constant.KEY_SELECTED_ID, "0");
                startActivityForResult(intent, Constant.REQUEST_FILTER_HOME_FRAGMENT);
            }
        });

        headerNoInternetConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerNoInternetConnection.setVisibility(View.GONE);
                headerFilter.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getPosts();
            }
        });

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getPosts();
                postItems.add(null);
                loadingItemIndex = postItems.size() - 1;
                adapter.notifyItemInserted(loadingItemIndex);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerPostItems.setVisibility(View.GONE);
                tvNoData.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                headerNoInternetConnection.setVisibility(View.GONE);
                headerFilter.setVisibility(View.VISIBLE);
                page = 1;
                postItems.clear();
                adapter.notifyDataSetChanged();
                edtVoiceSearch.setText("");

                getPosts();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRole = Read.readDataFromStorage("role", 5, getActivity());
                if (getRole == 2) {
                    startActivity(new Intent(getActivity().getApplication(), CreatePostActivity.class));
                }
                if (getRole == 1) {
                    //request for check permission about ads
                    CheckConsultantConfirmation();
                }
            }
        });

        imgVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVoiceSearch();
            }
        });

        edtVoiceSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getPostsSearch();
                    return true;
                } else
                    return false;
            }
        });
    }

    private void CheckConsultantConfirmation() {
        final String url = Constant.BASE_URL + "conAgent";

        JSONObject object = new JSONObject();
        try {
            object.put("agent_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("result").equals("ok")) {
                        if (response.getString("msg").equals("ok")) {
                            startActivity(new Intent(getActivity().getApplication(), CreatePostActivity.class));
                        }
                        if (response.getString("msg").equals("error")) {
                            Confirmation confirmDialog = new Confirmation(getContext(),"املاکی مورد نظر، شما را تایید نکرده است ");
                            confirmDialog.show();
                        }
                    }
                        //mitone agahi bezare

                    } catch(JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    //1
                    Log.i(G.TAG, String.valueOf(e));
                    AppSingleton.getInstance(getActivity()).cancelPendingRequests();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, listener, errorListener);


        request.setRetryPolicy(new

            DefaultRetryPolicy(10000,1,1.0f));
        AppSingleton.getInstance(

            getActivity()).

            addToRequestQueue(request);
        }

        private void setupFragment ()
        {
            user_id = Read.readDataFromStorage("user_id", "0", getContext());

            recyclerPostItems.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new PostsAdapter(getContext(), postItems, recyclerPostItems);
            recyclerPostItems.setAdapter(adapter);

            recyclerPostItems.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            headerNoInternetConnection.setVisibility(View.GONE);
            headerFilter.setVisibility(View.VISIBLE);
        }

        public void onActivityResult ( int requestCode, int resultCode, Intent data)
        {
            if (requestCode == Constant.REQUEST_FILTER_HOME_FRAGMENT && resultCode == RESULT_OK && data != null) {
                String result = data.getStringExtra(Constant.KEY_FILTER_DATA_JSON);
                try {
                    jsonFilter = new JSONObject(result);
                    recyclerPostItems.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    headerNoInternetConnection.setVisibility(View.GONE);
                    headerFilter.setVisibility(View.VISIBLE);
                    postItems.clear();
                    page = 1;

                    getPosts();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(G.TAG, result);
            }

            if (requestCode == REQUEST_VOICE_SEARCH && resultCode == RESULT_OK && data != null) {
                ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edtVoiceSearch.setText(voiceText.get(0));
                getPostsSearch();
            }
        }

        private void openVoiceSearch ()
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa-IR");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "جستجوی صوتی");

            try {
                startActivityForResult(intent, REQUEST_VOICE_SEARCH);
            } catch (ActivityNotFoundException e) {
                Intent your_browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox&hl=en"));
                startActivity(your_browser_intent);
            }
        }

        private void getPosts ()
        {
            final String url = Constant.BASE_URL + "filterEstate";

            JSONObject jsonData = new JSONObject();
            try {
                if (jsonFilter != null)
                    jsonData = jsonFilter;
                jsonData.put(Constant.CONS_ID, user_id);
                jsonData.put("deleted", "0");
                jsonData.put(Constant.EXPIRE, "0");
                jsonData.put(Constant.PAGE, page);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Response.Listener<ArrayList<Post>> listener = new Response.Listener<ArrayList<Post>>() {
                @Override
                public void onResponse(ArrayList<Post> response) {
                    try {
                        if (page != 1) {
                            postItems.remove(loadingItemIndex);
                            adapter.notifyItemRemoved(postItems.size());
                        }
                        if (response != null) {
                            postItems.addAll(response);
                            adapter.notifyDataSetChanged();
                            recyclerPostItems.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            adapter.setLoading(false);
                            swipeRefresh.setRefreshing(false);
                        } else {
                            if (page == 1) {
                                recyclerPostItems.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                tvNoData.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        Log.i(G.TAG, e.getMessage());
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    headerNoInternetConnection.setVisibility(View.VISIBLE);
                    headerFilter.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                }
            };

            EstatePostRequest request = new EstatePostRequest(url, jsonData, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Content-Type", "application/json; charset=utf-8");
                    return hashMap;
                }
            };

            AppSingleton.getInstance(getContext()).addToRequestQueue(request);
        }

        private void getPostsSearch ()
        {
            recyclerPostItems.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            page = 1;
            postItems.clear();
            adapter.notifyDataSetChanged();

            final String url = Constant.BASE_URL + "searchEstate";

            JSONObject jsonData = new JSONObject();
            try {
                if (jsonFilter != null)
                    jsonData = jsonFilter;
                jsonData.put(Constant.SEARCH_TEXT, edtVoiceSearch.getText());
                jsonData.put(Constant.CONS_ID, user_id);
                jsonData.put(Constant.PAGE, page);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Response.Listener<ArrayList<Post>> listener = new Response.Listener<ArrayList<Post>>() {
                @Override
                public void onResponse(ArrayList<Post> response) {
                    try {
                        if (page != 1) {
                            postItems.remove(loadingItemIndex);
                            adapter.notifyItemRemoved(postItems.size());
                        }
                        if (response != null) {
                            postItems.addAll(response);
                            adapter.notifyDataSetChanged();
                            recyclerPostItems.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            adapter.setLoading(false);
                            swipeRefresh.setRefreshing(false);
                        } else {
                            if (page == 1) {
                                recyclerPostItems.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                tvNoData.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        Log.i(G.TAG, e.getMessage());
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                }
            };

            EstatePostRequest request = new EstatePostRequest(url, jsonData, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Content-Type", "application/json; charset=utf-8");
                    return hashMap;
                }
            };

            AppSingleton.getInstance(getContext()).addToRequestQueue(request);
        }
    }
