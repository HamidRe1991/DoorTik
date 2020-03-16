package com.smr.estate.Tools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.smr.estate.Adapter.RSSFeedListAdapter;
import com.smr.estate.Model.RSSFeed;
import com.smr.estate.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RSS extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private List<RSSFeed> mFeedModelList;
    private ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        mRecyclerView = findViewById(R.id.recyclerView);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayout);
        imgBack = findViewById(R.id.imgRSSBack);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new FetchFeedTask().execute((Void) null);

        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new FetchFeedTask().execute((Void) null);
            }
        });
    }

    public List<RSSFeed> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException
    {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        List<RSSFeed> items = new ArrayList<>();

        try
        {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT)
            {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG)
                {
                    if (name.equalsIgnoreCase("item"))
                    {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG)
                {
                    if (name.equalsIgnoreCase("item"))
                    {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MainActivity", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT)
                {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title"))
                {
                    title = result;
                } else if (name.equalsIgnoreCase("link"))
                {
                    link = result;
                } else if (name.equalsIgnoreCase("description"))
                {
                    description = result;
                }

                if (title != null && link != null && description != null)
                {
                    if (isItem)
                    {
                        RSSFeed item = new RSSFeed(title, link, description);
                        items.add(item);
                    }

                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally
        {
            inputStream.close();
        }
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean>
    {

        private String urlLink;

        @Override
        protected void onPreExecute()
        {
            mSwipeLayout.setRefreshing(true);
            urlLink = "http://www.shahrekhabar.com/rss.jsp?type=4";
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try
            {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e)
            {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e)
            {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            mSwipeLayout.setRefreshing(false);

            if (success)
            {
                mRecyclerView.setAdapter(new RSSFeedListAdapter(RSS.this, mFeedModelList));
            } else
            {
                Toast.makeText(RSS.this,
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
