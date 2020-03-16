package com.smr.estate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.smr.estate.Application.G;
import com.smr.estate.R;
import com.smr.estate.Activities.DetailPostActivity;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Model.Post;
import com.smr.estate.Utils.IdToString;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import wiadevelopers.com.library.MaterialLoading.MaterialProgressBar;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Post> postItems;
    private Context context;
    private final int VIEW_POST_ITEM = 0;
    private final int VIEW_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private Realm realm;

    public PostsAdapter(Context context, ArrayList<Post> postItems, RecyclerView mRecyclerView)
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
            View view = inflater.inflate(R.layout.item_post, parent, false);
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
                final postViewHolder mPostViewHolder = (postViewHolder) holder;

                mPostViewHolder.tvTitle.setText(postItems.get(listPosition).getEstate().getName());

                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
                mPostViewHolder.tvPrice.setText(decimalFormat.format(Integer.parseInt(postItems.get(listPosition).getEstate().getPrice())));

                mPostViewHolder.tvTime.setText(postItems.get(listPosition).getEstate().getCreated_at());

                int typeId = postItems.get(listPosition).getSell().get(0).getSell_id();
                if (typeId == 1)
                    mPostViewHolder.viewColor.setBackgroundResource(R.color.type1);
                if (typeId == 2)
                    mPostViewHolder.viewColor.setBackgroundResource(R.color.type2);
                if (typeId == 3)
                    mPostViewHolder.viewColor.setBackgroundResource(R.color.type3);
                if (typeId == 4)
                    mPostViewHolder.viewColor.setBackgroundResource(R.color.type4);
                if (typeId == 5)
                    mPostViewHolder.viewColor.setBackgroundResource(R.color.type5);
                if (typeId == 6)
                    mPostViewHolder.viewColor.setBackgroundResource(R.color.type6);

                String typeName = postItems.get(listPosition).getSell().get(0).getName();
                int catId = postItems.get(listPosition).getEstate().getType();
                String catName = IdToString.Category(String.valueOf(catId));
                mPostViewHolder.tvType.setText(typeName + " (" + catName + ")");

                String url = postItems.get(listPosition).getImage().getImage_1();

                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions()
                                .error(R.drawable.ic_post_no_image)
                                .placeholder(R.drawable.ic_post_no_image)
                                .fitCenter())
                        .into(mPostViewHolder.imgPic);

                mPostViewHolder.crdRoot.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        try
                        {
                            final Post post = postItems.get(listPosition);
                            Intent intent = new Intent(context, DetailPostActivity.class);
                            intent.putExtra(Constant.KEY_POST_ID, post.getEstate().getId());
                            intent.putExtra(Constant.KEY_IMAGE1, post.getImage().getImage_1());
                            intent.putExtra(Constant.KEY_IMAGE2, post.getImage().getImage_2());
                            intent.putExtra(Constant.KEY_IMAGE3, post.getImage().getImage_3());
                            intent.putExtra(Constant.KEY_IMAGE4, post.getImage().getImage_4());
                            intent.putExtra(Constant.KEY_IMAGE5, post.getImage().getImage_5());
                            ((AppCompatActivity) context).startActivity(intent);

                            //Cached post in realm database
                            realm.executeTransaction(new Realm.Transaction()
                            {
                                @Override
                                public void execute(Realm realm)
                                {
                                    RealmResults<Post> result = realm.where(Post.class).equalTo(Constant.KEY_ID, post.getEstate().getId()).findAll();
                                    if (result.size() == 0)
                                        realm.copyToRealm(post);
                                }
                            });
                        } catch (Exception e)
                        {
                            Log.i(G.TAG, e.getMessage());
                        }
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
        public TextView tvTitle, tvPrice, tvTime, tvType;
        public RoundedImageView imgPic;
        public CardView crdRoot;
        public View viewColor;

        public postViewHolder(View itemView)
        {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvRowTitle);
            tvPrice = itemView.findViewById(R.id.tvRowPrice);
            tvTime = itemView.findViewById(R.id.tvRowTime);
            tvType = itemView.findViewById(R.id.tvRowType);
            imgPic = itemView.findViewById(R.id.imgPic);
            crdRoot = itemView.findViewById(R.id.crdRoot);
            viewColor = itemView.findViewById(R.id.viewColor);
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
}