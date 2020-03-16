package com.smr.estate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smr.estate.Model.RSSFeed;
import com.smr.estate.R;

import java.util.List;

public class RSSFeedListAdapter extends RecyclerView.Adapter<RSSFeedListAdapter.FeedModelViewHolder>
{
    private List<RSSFeed> mRssFeedModels;
    private Context context;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder
    {
        private View rssFeedView;
        LinearLayout lnrRSS;

        public FeedModelViewHolder(View v)
        {
            super(v);
            rssFeedView = v;
            lnrRSS = v.findViewById(R.id.lnrRSS);
        }
    }

    public RSSFeedListAdapter(Context context, List<RSSFeed> rssFeedModels)
    {
        mRssFeedModels = rssFeedModels;
        this.context = context;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, final int position)
    {
        final RSSFeed rssFeedModel = mRssFeedModels.get(position);
        ((TextView) holder.rssFeedView.findViewById(R.id.titleText)).setText(rssFeedModel.title);
        ((TextView) holder.rssFeedView.findViewById(R.id.descriptionText)).setText(rssFeedModel.description);

        if (holder instanceof FeedModelViewHolder)
        {
            FeedModelViewHolder modelViewHolder = (FeedModelViewHolder) holder;

            modelViewHolder.lnrRSS.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final RSSFeed rssFeed = mRssFeedModels.get(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssFeed.link));

                    context.startActivity(browserIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mRssFeedModels.size();
    }
}
