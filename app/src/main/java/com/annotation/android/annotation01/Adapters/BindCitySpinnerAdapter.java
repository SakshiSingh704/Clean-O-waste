package com.annotation.android.annotation01.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.annotation.android.annotation01.pojo.GetCityModal;

import java.util.List;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class BindCitySpinnerAdapter extends ArrayAdapter<GetCityModal> {

    public Context context;
    public List<GetCityModal> objects;

    @Override
    public int getPosition(@Nullable GetCityModal item) {
        for(int i = 0; i<objects.size(); i++){
            if(item == objects.get(i))
                return i;
        }
        return 0;
    }

    public BindCitySpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<GetCityModal> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    public int getCount(){
        return objects.size();
    }

    public long getItemId(int position){
        return position;
    }

    @Nullable
    @Override
    public GetCityModal getItem(int position) {
        return (objects.size()>0?objects.get(position):null);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setPadding(15,15,15,15);
        label.setTextColor(Color.BLACK);
        label.setTextSize((float) 16.0);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(objects.get(position).getCityName());
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        TextView label = new TextView(context);
        label.setPadding(15,15,15,15);
        label.setTextColor(Color.BLACK);
        label.setTextSize((float) 16.0);
        label.setText(objects.get(position).getCityName());
        return label;
    }
}
