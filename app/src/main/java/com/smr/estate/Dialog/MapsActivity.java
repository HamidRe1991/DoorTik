package com.smr.estate.Dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.smr.estate.Application.G;
import com.smr.estate.BuildConfig;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Utils.RuntimePermissionsActivity;

import org.neshan.core.LngLat;
import org.neshan.core.Range;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.AnimationStyle;
import org.neshan.styles.AnimationStyleBuilder;
import org.neshan.styles.AnimationType;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.ClickData;
import org.neshan.ui.ClickType;
import org.neshan.ui.MapEventListener;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;

import java.text.DateFormat;
import java.util.Date;

//Class for select location

public class MapsActivity extends RuntimePermissionsActivity
{
    private MapView map;

    final int REQUEST_CODE = 123;
    final int BASE_MAP_INDEX = 0;
    VectorElementLayer markerLayer;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    private Boolean mRequestingLocationUpdates;
    private CoordinatorLayout coordinatorLayoutMaps;
    private String Lat = null, Lon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        coordinatorLayoutMaps = findViewById(R.id.coordinatorMaps);
    }

    @Override
    public void onPermissionsGranted(int requestCode)
    {
        mRequestingLocationUpdates = true;
        startLocationUpdates();
    }

    @Override
    public void onPermissionsDeny(int requestCode)
    {
        openSettings();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        initLayoutReferences();
        initLocation();
        startReceivingLocationUpdates();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        startLocationUpdates();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        stopLocationUpdates();
    }

    private void initLayoutReferences()
    {
        initViews();
        initMap();

        map.setMapEventListener(new MapEventListener()
        {
            @Override
            public void onMapClicked(ClickData mapClickInfo)
            {
                if (mapClickInfo.getClickType() == ClickType.CLICK_TYPE_LONG)
                {
                    LngLat clickedLocation = mapClickInfo.getClickPos();
                    addMarker(clickedLocation);

                    Lat = String.valueOf(clickedLocation.getX());
                    Lon = String.valueOf(clickedLocation.getY());

                    Intent intent = new Intent();
                    intent.putExtra("Lat", Lat);
                    intent.putExtra("Lon", Lon);
                    setResult(Constant.RESULT_OK, intent);
                    MapsActivity.this.finish();
                }

                if (mapClickInfo.getClickType() == ClickType.CLICK_TYPE_SINGLE)
                {
                    SnackBar.Create(coordinatorLayoutMaps, "برای تایید مکان انگشتتان را نگه دارید",3);
                }
            }
        });
    }

    private void initViews()
    {
        map = findViewById(R.id.map);
    }

    private void initMap()
    {
        markerLayer = NeshanServices.createVectorElementLayer();
        map.getLayers().add(markerLayer);

        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.NESHAN));

        map.setFocalPointPosition(new LngLat(51.330743, 35.767234), 0);
        map.setZoom(14, 0);
    }

    private void addMarker(LngLat loc)
    {
        markerLayer.clear();

        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
        animStBl.setPhaseInDuration(0.5f);
        animStBl.setPhaseOutDuration(0.5f);
        AnimationStyle animSt = animStBl.buildStyle();

        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(40f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.marker)));
        markStCr.setAnimationStyle(animSt);
        MarkerStyle markSt = markStCr.buildStyle();

        Marker marker = new Marker(loc, markSt);
        markerLayer.add(marker);
    }

    private void initLocation()
    {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                super.onLocationResult(locationResult);

                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    private void startLocationUpdates()
    {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>()
                {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse)
                    {
                        Log.i(G.TAG, "All location settings are satisfied.");

                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                        onLocationChange();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode)
                        {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(G.TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try
                                {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapsActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie)
                                {
                                    Log.i(G.TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(G.TAG, errorMessage);

                                Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        onLocationChange();
                    }
                });
    }

    public void stopLocationUpdates()
    {
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                    }
                });
    }

    public void startReceivingLocationUpdates()
    {
        MapsActivity.super.requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.REQUEST_PERMISSIONS_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_CODE:
                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                        Log.e(G.TAG, "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(G.TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings()
    {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void onLocationChange()
    {
        if (userLocation != null)
        {
            addUserMarker(new LngLat(userLocation.getLongitude(), userLocation.getLatitude()));
        }
    }

    private void addUserMarker(LngLat loc)
    {
        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(40f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.marker)));
        MarkerStyle markSt = markStCr.buildStyle();

        Marker marker = new Marker(loc, markSt);
        markerLayer.clear();
        markerLayer.add(marker);
    }

    public void focusOnUserLocation(View view)
    {
        if (userLocation != null)
        {
            map.setFocalPointPosition(
                    new LngLat(userLocation.getLongitude(), userLocation.getLatitude()), 0.25f);
            map.setZoom(15, 0.25f);
        }
    }
}
