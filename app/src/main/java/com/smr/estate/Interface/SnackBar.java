package com.smr.estate.Interface;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.smr.estate.R;

public class SnackBar
{
    public static void Create(View view, String text, int duration)
    {
        Snackbar snackBar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(view.getResources().getColor(R.color.error));
        ViewCompat.setLayoutDirection(snackBar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackBar.setDuration(3000);
        snackBar.show();
    }
}
