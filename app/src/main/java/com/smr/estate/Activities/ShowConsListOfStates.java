package com.smr.estate.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smr.estate.Model.ConRequestModel;
import com.smr.estate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowConsListOfStates extends AppCompatActivity {

    public static String consData = "";
    ConRequestModel conRequestModel;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout lnrConsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cons_for_accept);
        FindView();
     //   GetConsData();
        CreateRequests();

    }

    private void FindView() {
        lnrConsItem = findViewById(R.id.idLnrItems);
    }

    private void CreateRequests() {
        try {
            JSONArray jsonArray = new JSONArray(consData);
            for (int i=0 ; i<jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");
                String  name = object.getString("name");
                String lastName = object.getString("last_name");
                String mobile = object.getString("mobile");
                int status = object.getInt("status");


                conRequestModel = new ConRequestModel(getApplicationContext());

                ConRequestModel.tvName.setText(name+" "+lastName);
                ConRequestModel.tvMobile.setText(mobile);
                ConRequestModel.agentId = id;
                ConRequestModel.status = status;
                Toast.makeText(getApplicationContext(),mobile+"" ,Toast.LENGTH_SHORT).show();

                if(status == 0){
                    ConRequestModel.tvReady.setBackgroundColor(getResources().getColor(R.color.yellow));
                    ConRequestModel.tvReady.setText("کاربر غیر فعال");
                }
                if(status == 1){
                    ConRequestModel.tvReady.setBackground(getResources().getDrawable(R.drawable.buy_item_background));
                    ConRequestModel.tvReady.setText("کاربر فعال");
                    ConRequestModel.tvReady.setTextColor(getResources().getColor(R.color.white));
                }

                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lnrConsItem.addView(conRequestModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
