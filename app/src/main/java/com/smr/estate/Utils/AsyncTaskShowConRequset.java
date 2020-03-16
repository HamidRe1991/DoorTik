package com.smr.estate.Utils;

import android.os.AsyncTask;

import com.smr.estate.Activities.ShowConsListOfStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AsyncTaskShowConRequset extends AsyncTask {
    String link;
    String userId;

    public AsyncTaskShowConRequset(String link, String userCode) {
        this.link = link;
        this.userId = userCode;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            String myData = URLEncoder.encode("user_id", "UTF8") + "=" + URLEncoder.encode(String.valueOf(userId), "UTF8");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(myData);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                ShowConsListOfStates.consData = builder.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

