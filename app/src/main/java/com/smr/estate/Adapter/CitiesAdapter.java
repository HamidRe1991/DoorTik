package com.smr.estate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.smr.estate.R;

import java.util.ArrayList;

import wiadevelopers.com.library.DivarUtils;

public class CitiesAdapter extends BaseAdapter implements Filterable
{
    private Context context;
    private ArrayList<String> cities;
    private final ArrayList<String> tmpCities;
    private LayoutInflater inflater = null;

    public CitiesAdapter(Context context, ArrayList<String> cities)
    {
        this.cities = cities;
        this.tmpCities = cities;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return cities.size();
    }

    public String getItem(int position)
    {
        return cities.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();
                ArrayList<String> myResult = new ArrayList<>();
                for (int i = 0; i < tmpCities.size(); i++)
                {
                    if (charSequence.length() > 0)
                    {
                        if (tmpCities.get(i).contains(charSequence.toString()))
                            myResult.add(tmpCities.get(i));
                    } else
                        myResult.add(tmpCities.get(i));
                }
                results.count = myResult.size();
                results.values = myResult;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                cities = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public static class ViewHolder
    {
        public TextView txtText;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null)
        {
            vi = inflater.inflate(R.layout.item_city, null);

            holder = new ViewHolder();
            holder.txtText = vi.findViewById(R.id.itemCityTxtText);
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        holder.txtText.setTypeface(DivarUtils.faceLight);
        if (cities.size() != 0)
            holder.txtText.setText(cities.get(position));
        else
            holder.txtText.setText("آیتمی یافت نشد.");
        return vi;
    }
}
