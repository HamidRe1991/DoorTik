package com.smr.estate.CreatePostItems;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.smr.estate.R;
import com.smr.estate.Activities.CreatePostActivity;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Utils.Picture;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;
import wiadevelopers.com.library.ImageController.ImageController;
import wiadevelopers.com.library.ImageController.onItemDeleteListener;

public class SelectImage extends CreatePostRootView
{
    private Button btnSelect;
    private TextView txtTitle;
    private LinearLayout lnrInsert;

    private Activity activity;
    private ImageController imageController;

    public SelectImage(String title)
    {
        super(title, Constant.IS_IMAGE, SELECT_IMAGE);
        initialize();
    }

    private void initialize()
    {
        findViews();
        setupViews();
        setupListeners();
    }

    private void findViews()
    {
        txtTitle = super.view.findViewById(R.id.txtTitle);
        btnSelect = super.view.findViewById(R.id.btnSelect);
        lnrInsert = super.view.findViewById(R.id.lnrInsert);

        txtTitle.setTypeface(DivarUtils.faceLight);
        btnSelect.setTypeface(DivarUtils.faceLight);
    }

    private void setupViews()
    {
        txtTitle.setText(title);
        activity = (Activity) (context);
        imageController = new ImageController(activity, "عکس اصلی", 5, Constant.IMAGE_MAX_SIZE);
    }

    private void setupListeners()
    {
        btnSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((CreatePostActivity) activity).requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ADS);
            }
        });

        imageController.setOnItemDeleteListener(new onItemDeleteListener()
        {
            @Override
            public void onDelete(int position)
            {
                setupLayouts();
            }
        });
    }

    public void onPermissionsGranted(int requestCode)
    {
        if (requestCode == Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ADS)
            Picture.selectMultiImage(activity, Constant.REQUEST_SELECT_ADS_IMAGE);
    }

    public void onPermissionsDeny(int requestCode)
    {
        if (requestCode == Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ADS)
            Toast.makeText(activity, "جواز خواندن از حافظه داده نشد", Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constant.REQUEST_SELECT_ADS_IMAGE)
        {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);

            for (int i = 0; i < images.size(); i++)
            {
                Uri imageUri = Uri.fromFile(new File(images.get(i).getPath()));
                imageController.addBitmap(Picture.getBitmap(activity, imageUri));
                setupLayouts();
            }

            setupLayouts();
        }
    }

    private void setupLayouts()
    {
        lnrInsert.removeAllViews();
        for (int i = 0; i < imageController.getImagesCount(); i++)
        {
            View view = imageController.getView(i);
            lnrInsert.addView(view);
        }

        if (imageController.isFull())
            btnSelect.setVisibility(View.GONE);
        else
            btnSelect.setVisibility(View.VISIBLE);
    }

    public HashMap getData()
    {
        HashMap hashMap = new HashMap();
        JSONObject json = imageController.getData();

        if (json.length() != 0)
            hashMap.put(super.key, json);
        else
            hashMap.put(super.key, Constant.KEY_NO_IMAGE);

        return hashMap;
    }

    public void restoreData(ArrayList<Bitmap> bitmap)
    {
        if (bitmap.size() > 0)
        {
            for (int i = 0; i < bitmap.size(); i++)
            {
                imageController.addBitmap(bitmap.get(i));
            }

            setupLayouts();
        }
    }
}