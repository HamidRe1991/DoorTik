package com.smr.estate.Utils;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

//Class for set permission

public abstract class RuntimePermissionsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for (int permission : grantResults)
        {
            permissionCheck = permissionCheck + permission;
        }

        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            onPermissionsGranted(requestCode);
        } else
        {
            onPermissionsDeny(requestCode);
        }
    }

    public void requestAppPermissions(final String[] requestedPermissions, final int requestCode)
    {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;

        for (String permission : requestedPermissions)
        {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
        } else
        {
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(int requestCode);

    public abstract void onPermissionsDeny(int requestCode);
}