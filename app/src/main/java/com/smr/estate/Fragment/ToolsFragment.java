package com.smr.estate.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.smr.estate.R;
import com.smr.estate.Tools.Align;
import com.smr.estate.Tools.Calculator;
import com.smr.estate.Tools.Calender;
import com.smr.estate.Tools.Financial;
import com.smr.estate.Tools.FlashLight;
import com.smr.estate.Tools.ShowNote;
import com.smr.estate.Tools.RSS;

public class ToolsFragment extends Fragment
{
    LinearLayout toolsLnrBarcode;
    LinearLayout toolsLnrAlign;
    LinearLayout toolsLnrFinancial;
    LinearLayout toolsLnrCalculator;
    LinearLayout toolsLnrRSS;
    LinearLayout toolsLnrFlash;
    LinearLayout toolsLnrCalender;
    LinearLayout toolsLnrEvent;

    public ToolsFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_tools, container, false);

        findView(view);
        setOnClickListener();

        return view;
    }

    private void findView(View view)
    {
        toolsLnrBarcode = view.findViewById(R.id.toolsLnrBarcode);
        toolsLnrAlign = view.findViewById(R.id.toolsLnrAlign);
        toolsLnrFinancial = view.findViewById(R.id.toolsLnrFinancial);
        toolsLnrCalculator = view.findViewById(R.id.toolsLnrCalc);
        toolsLnrRSS = view.findViewById(R.id.toolsLnrRSS);
        toolsLnrFlash = view.findViewById(R.id.toolsLnrFlash);
        toolsLnrCalender = view.findViewById(R.id.toolsLnrCalender);
        toolsLnrEvent = view.findViewById(R.id.toolsLnrEvent);
    }

    private void setOnClickListener()
    {
        toolsLnrBarcode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();

                new IntentIntegrator(getActivity()).initiateScan();
            }
        });

        toolsLnrAlign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), Align.class));
            }
        });

        toolsLnrCalculator.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), Calculator.class));
            }
        });

        toolsLnrRSS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), RSS.class));
            }
        });

        toolsLnrFlash.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), FlashLight.class));
            }
        });

        toolsLnrCalender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), Calender.class));
            }
        });

        toolsLnrEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), ShowNote.class));
            }
        });

        toolsLnrFinancial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), Financial.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else
            {
                Intent your_browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
                startActivity(your_browser_intent);
            }
        } else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
