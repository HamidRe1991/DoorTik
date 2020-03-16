package com.smr.estate.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.esafirm.imagepicker.features.ImagePicker;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.R;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Picture
{
    public static void selectMultiImage(Activity activity, int request)
    {
        ImagePicker.create(activity)
                .folderMode(true)
                .multi()
                .limit(5)
                .showCamera(true)
                .imageDirectory("Door Thick")
                .start(request);
    }

    public static void selectSingleImage(Activity activity, int request)
    {
        ImagePicker.create(activity)
                .folderMode(true)
                .single()
                .showCamera(true)
                .imageDirectory("Door Thick")
                .start(request);
    }

    public static Bitmap getBitmap(Activity activity, Uri uri)
    {
        Bitmap bitmap = null;

        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Bitmap bitmapTemp = getResizedBitmap(bitmap, Constant.IMAGE_MAX_SIZE);

        Bitmap bitmapFinal = Picture.setWatermark(activity, bitmapTemp);

        return bitmapFinal;
    }

    public static Bitmap setWatermark(Context context, Bitmap bitmap)
    {
        WatermarkText watermarkText = new WatermarkText("درتیک")
                .setPositionX(0.6)
                .setPositionY(0.8)
                .setTextColor(Color.WHITE)
                .setTextFont(R.font.font_family)
                .setTextAlpha(150)
                .setTextSize(16);

        bitmap = WatermarkBuilder
                .create(context, bitmap)
                .loadWatermarkText(watermarkText)
                .getWatermark()
                .getOutputImage();

        return bitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int IMAGE_MAX_SIZE)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1.0F)
        {
            width = IMAGE_MAX_SIZE;
            height = (int) ((float) IMAGE_MAX_SIZE / bitmapRatio);
        } else
        {
            height = IMAGE_MAX_SIZE;
            width = (int) ((float) IMAGE_MAX_SIZE * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String getStringImage(Bitmap bmp, int IMAGE_MAX_SIZE)
    {
        bmp = getResizedBitmap(bmp, IMAGE_MAX_SIZE);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        byte[] imageBytes = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, 0);
        return encodedImage;
    }

    public static Bitmap getBitmapFromURL(String strUrl) throws IOException
    {
        Bitmap bitmap = null;
        InputStream iStream = null;

        try
        {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            iStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(iStream);

        } catch (Exception e)
        {
            Log.i(G.TAG, e.getMessage());
        } finally
        {
            iStream.close();
        }
        return bitmap;
    }
}
