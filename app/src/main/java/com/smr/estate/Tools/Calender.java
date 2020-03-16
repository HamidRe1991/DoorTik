package com.smr.estate.Tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smr.estate.R;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

public class Calender extends AppCompatActivity
{
    ImageView imgBack;
    private TextView tvDay, tvEvent, tvDayStr;
    PersianCalendarView calendarView;
    PersianCalendarHandler calendarHandler;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        imgBack = findViewById(R.id.imgCalenderBack);

        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        tvEvent = findViewById(R.id.tvEvent);
        tvDay = findViewById(R.id.tvDay);
        tvDayStr = findViewById(R.id.tvDayStr);

        calendarView  = findViewById(R.id.persian_calendar);
        calendarHandler = calendarView.getCalendar();
        PersianDate today = calendarHandler.getToday();

        day = today.getDayOfMonth();
        month = today.getMonth();
        year = today.getYear();

        tvDay.setText("" + year + "/" + month + "/" + day);

        String strMonth = "";
        if (month == 1)
            strMonth = "فروردین";
        else if (month == 2)
            strMonth = "اردیبهشت";
        else if (month == 3)
            strMonth = "خرداد";
        else if (month == 4)
            strMonth = "تیر";
        else if (month == 5)
            strMonth = "مرداد";
        else if (month == 6)
            strMonth = "شهریور";
        else if (month == 7)
            strMonth = "مهر";
        else if (month == 8)
            strMonth = "آبان";
        else if (month == 9)
            strMonth = "آذر";
        else if (month == 10)
            strMonth = "دی";
        else if (month == 11)
            strMonth = "بهمن";
        else if (month == 12)
            strMonth = "اسفند";

        tvDayStr.setText("امروز: " + day + " " + strMonth + " سال " + year);

        for (int i = 0; i < calendarHandler.getAllEventsForDay(today).size(); i++)
            tvEvent.setText(calendarHandler.getAllEventsForDay(today).get(i).getTitle() + "\n");

        calendarHandler.setOnDayClickedListener(new OnDayClickedListener()
        {
            @Override
            public void onClick(PersianDate persianDate)
            {
                tvDay.setText("" + persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth());

                tvEvent.setText(null);
                for (int i = 0; i < calendarHandler.getAllEventsForDay(persianDate).size(); i++)
                    tvEvent.setText(calendarHandler.getAllEventsForDay(persianDate).get(i).getTitle() + "\n");
            }
        });

        //More event
        /*calendarHandler.getLocalEvents();
        calendarHandler.getLocalEventsForDay(today);
        calendarHandler.getOfficialEventsForDay(today);*/
    }
}
