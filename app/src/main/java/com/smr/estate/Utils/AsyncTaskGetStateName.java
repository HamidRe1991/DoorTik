package com.smr.estate.Utils;

import android.os.AsyncTask;

import com.smr.estate.Activities.RegisterActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AsyncTaskGetStateName extends AsyncTask {
    String link;
    String area;

    public AsyncTaskGetStateName(String link, String area) {
        this.link = link;
        this.area = area;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            String myData = URLEncoder.encode("area","UTF8")+"="+URLEncoder.encode(area,"UTF8");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(myData);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line  = null;
            while ((line = reader.readLine())!= null){
                builder.append(line);
                RegisterActivity.areaName = builder.toString();
            }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
    }
}
