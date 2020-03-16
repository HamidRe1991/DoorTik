package com.smr.estate.Dialog;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.smr.estate.R;

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
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;

//Class for show location

public class ShowMapsActivity extends AppCompatActivity
{
    private MapView map;
    final int BASE_MAP_INDEX = 0;
    private VectorElementLayer markerLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.show_maps_activity);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        initMap();
    }

    private void initMap()
    {
        map = findViewById(R.id.showMap);

        markerLayer = NeshanServices.createVectorElementLayer();
        map.getLayers().add(markerLayer);

        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.NESHAN));

        Double dblLat = Double.valueOf(getIntent().getStringExtra("Lat"));
        Double dblLon = Double.valueOf(getIntent().getStringExtra("Lon"));

        map.setFocalPointPosition(new LngLat(dblLat, dblLon), 0);
        map.setZoom(14, 0);

        addMarker(new LngLat(dblLat, dblLon), 0);
    }

    private void addMarker(LngLat loc, int i)
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
}
