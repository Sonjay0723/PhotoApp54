package com.example.photoapp54;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photoapp54.R;
import com.example.photoapp54.model.Photo;

import java.util.List;

/**
 * Adapter to hold the photo view and its details.
 */
public class PhotoAdaptor extends ArrayAdapter<Photo> {

    private Context context;

    public PhotoAdaptor(Context context, int resourceId, List<Photo> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Photo photo = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            //this is if it is on the display section
            convertView = inflater.inflate(R.layout.adaptor_view, null);
            holder = new ViewHolder();
            holder.photo = (ImageView) convertView.findViewById(R.id.imageDisplay);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.photo.setImageBitmap(photo.getBitmap());

        return convertView;
    }

    /**
     * Holds the photo and catptions privately.
     */
    private class ViewHolder {
        ImageView photo;
    }
}

