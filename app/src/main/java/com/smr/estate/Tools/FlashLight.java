package com.smr.estate.Tools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smr.estate.Adapter.Flash;
import com.smr.estate.R;

public class FlashLight extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private static final String LONG_PRESS = "long_press";
    public static final String WHITE = "white";
    private final Flash flash = new Flash();
    private View background;
    private ToggleButton theButton;
    private Drawable dark;
    private boolean changeColor = false;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        theButton = findViewById(R.id.flashlightButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ViewParent vp = theButton.getParent();
        if (vp instanceof View)
        {
            background = (View) vp;
            background.setOnLongClickListener(new LongClickListener());
            dark = background.getBackground();
        } else
        {
            background = new View(this);
        }

        ImageSpan imageSpan = new ImageSpan(this, R.drawable.power);
        SpannableString content = new SpannableString("X");
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        theButton.setText(content);
        theButton.setTextOn(content);
        theButton.setTextOff(content);
    }

    public class FlashTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids)
        {
            return flash.on();
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            theButton.setEnabled(true);
            if (!success)
            {
                Toast.makeText(FlashLight.this, "Failed to access camera!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class WhiteTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            return sharedPreferences.getBoolean(WHITE, false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {

        }
    }

    public class PressTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            return sharedPreferences.getBoolean(LONG_PRESS, false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            background.setLongClickable(aBoolean);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        switch (key)
        {
            case LONG_PRESS:
                new PressTask().execute();
                break;
            case WHITE:
                new WhiteTask().execute();
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        if (theButton.isChecked())
        {
            theButton.setEnabled(false);
            new FlashTask().execute();
            theButton.setKeepScreenOn(true);
        } else
        {
            flash.off();
        }

        new PressTask().execute();
        new WhiteTask().execute();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        flash.close();
    }

    public void onToggleClicked(View v)
    {
        if (theButton.isChecked())
        {
            new FlashTask().execute();
            v.setKeepScreenOn(true);
            if (changeColor)
            {
                background.setBackgroundColor(Color.WHITE);
            }
        } else
        {
            flash.off();
            v.setKeepScreenOn(false);
        }
    }

    public class LongClickListener implements View.OnLongClickListener
    {
        @Override
        public boolean onLongClick(View view)
        {
            theButton.setChecked(!theButton.isChecked());
            onToggleClicked(theButton);
            return true;
        }
    }
}
