package com.smr.estate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Model.Note;
import com.smr.estate.Model.Post;
import com.smr.estate.R;
import com.smr.estate.Tools.CreateNote;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import wiadevelopers.com.library.MaterialLoading.MaterialProgressBar;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Note> postItems;
    private Context context;
    private final int VIEW_POST_ITEM = 0;
    private final int VIEW_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private Note note;
    private AlertDialog alertDialog;
    private Realm realm;

    public NoteAdapter(Context context, ArrayList<Note> postItems, RecyclerView mRecyclerView)
    {
        this.context = context;
        this.postItems = postItems;
        this.realm = Realm.getDefaultInstance();

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                {
                    if (onLoadMoreListener != null)
                    {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener)
    {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public boolean isLoading()
    {
        return this.isLoading;
    }

    public void setLoading(boolean loading)
    {
        isLoading = loading;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (postItems.get(position) != null)
            return VIEW_POST_ITEM;
        else
            return VIEW_LOADING;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_POST_ITEM)
        {
            View view = inflater.inflate(R.layout.item_note, parent, false);
            return new postViewHolder(view);
        } else if (viewType == VIEW_LOADING)
        {
            View view = inflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition)
    {
        if (holder instanceof postViewHolder)
        {
            try
            {
                final NoteAdapter.postViewHolder mPostViewHolder = (NoteAdapter.postViewHolder) holder;

                mPostViewHolder.tvTitle.setText(postItems.get(listPosition).getEvent());
                mPostViewHolder.tvTime.setText(postItems.get(listPosition).getUpdated_at());
                note = postItems.get(listPosition);

                mPostViewHolder.crdRoot.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        try
                        {
                            Intent intent = new Intent(context, CreateNote.class);
                            intent.putExtra(Constant.KEY_NOTE_ID, note.getId());
                            intent.putExtra(Constant.KEY_NOTE_CHECK, true);
                            intent.putExtra(Constant.KEY_NOTE, note.getEvent());
                            ((AppCompatActivity) context).startActivity(intent);

                            //Cached post in realm database
                            realm.executeTransaction(new Realm.Transaction()
                            {
                                @Override
                                public void execute(Realm realm)
                                {
                                    RealmResults<Post> result = realm.where(Post.class).equalTo(Constant.KEY_ID, note.getId()).findAll();
                                    if (result.size() == 0)
                                        realm.copyToRealm(note);
                                }
                            });

                        } catch (Exception e)
                        {
                            Log.i(G.TAG, e.getMessage());
                        }
                    }
                });

                mPostViewHolder.imgDelete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        deleteNoteDialog(String.valueOf(note.getId()));
                    }
                });
            } catch (Exception e)
            {
                Log.i(G.TAG, e.getMessage());
            }

        } else if (holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount()
    {
        if (postItems == null)
            return 0;
        return postItems.size();
    }

    static class postViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle, tvTime;
        public CardView crdRoot;
        public ImageView imgDelete;

        public postViewHolder(View itemView)
        {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvNoteText);
            tvTime = itemView.findViewById(R.id.tvNoteTime);
            imgDelete = itemView.findViewById(R.id.imgDeleteNote);
            crdRoot = itemView.findViewById(R.id.crdRoot);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        public MaterialProgressBar progressBar;

        public LoadingViewHolder(View itemView)
        {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void deleteNote(String ads_id)
    {
        String url = Constant.BASE_URL + "deleteEvent";

        final JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put(Constant.KEY_NOTE_ID, ads_id);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i(G.TAG, "onErrorResponse: " + error.getMessage());
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                return hashMap;
            }
        };

        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    private void deleteNoteDialog(final String id)
    {
        try
        {
            LayoutInflater myLayout = LayoutInflater.from(context);
            final View view = myLayout.inflate(R.layout.dialog_yes_or_no, null);

            final TextView tvTitle, tvYes, tvNo;

            tvTitle = view.findViewById(R.id.tvDialogTitle);
            tvYes = view.findViewById(R.id.tvYes);
            tvNo = view.findViewById(R.id.tvNo);

            tvTitle.setText("یادداشت مورد نظر حذف شود؟");

            tvNo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                }
            });

            tvYes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    deleteNote(id);
                    alertDialog.dismiss();
                }
            });

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(view);
            alertDialogBuilder.setCancelable(true);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } catch (Exception e)
        {
            Log.i(G.TAG, e.getMessage());
        }
    }
}