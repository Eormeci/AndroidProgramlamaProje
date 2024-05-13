package com.example.yeniprojekotlin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mTitles;
    private List<String> mDescriptions;

    public CustomAdapter(Context context, List<String> titles, List<String> descriptions) {
        super(context, R.layout.list_item_layout, titles);
        this.mContext = context;
        this.mTitles = titles;
        this.mDescriptions = descriptions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            listItemView = inflater.inflate(R.layout.list_item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleTextView = listItemView.findViewById(getTitleResourceId(position));
            viewHolder.descriptionTextView = listItemView.findViewById(getDescriptionResourceId(position));

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        viewHolder.titleTextView.setText(mTitles.get(position));
        viewHolder.descriptionTextView.setText(mDescriptions.get(position));

        return listItemView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
    }

    // Return the resource id for title TextView based on position
    private int getTitleResourceId(int position) {
        switch (position) {
            case 0:
                return R.id.item_title1;
            case 1:
                return R.id.item_title2;
            case 2:
                return R.id.item_title3;
            case 3:
                return R.id.item_title4;
            case 4:
                return R.id.item_title5;
            case 5:
                return R.id.item_title6;
            // Add more cases if needed for additional titles
            default:
                return -1; // Invalid resource id
        }
    }

    // Return the resource id for description TextView based on position
    private int getDescriptionResourceId(int position) {
        switch (position) {
            case 0:
                return R.id.item_description1;
            case 1:
                return R.id.item_description2;
            case 2:
                return R.id.item_description3;
            case 3:
                return R.id.item_description4;
            case 4:
                return R.id.item_description5;
            case 5:
                return R.id.item_description6;
            // Add more cases if needed for additional descriptions
            default:
                return -1; // Invalid resource id
        }
    }
}
