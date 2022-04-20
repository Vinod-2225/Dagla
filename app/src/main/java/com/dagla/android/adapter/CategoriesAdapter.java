package com.dagla.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dagla.android.GlobalFunctions;
import com.dagla.android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sys on 21-Jan-19.
 */

public class CategoriesAdapter extends BaseAdapter {

    private final Activity context;
    private final ArrayList<String> arrNames;
    private final ArrayList<Integer> arrImages;
    private final int width;


    static class ViewHolder {
        public ImageView categoryImg;
        public TextView categoryName;

        public RelativeLayout itemLayout;
    }

    public CategoriesAdapter(Activity context, ArrayList<String> arrNames, ArrayList<Integer> arrImages, int width) {
//        super(context, R.layout.layout_categories_item, arrNames);
        this.context = context;
        this.arrNames = arrNames;
        this.arrImages = arrImages;
        this.width = width;

    }

    @Override
    public int getCount() {
        return arrNames.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //
        View rowView = convertView;
        //
        if (rowView == null) {
            //
            LayoutInflater inflater = context.getLayoutInflater();
            if(GlobalFunctions.getLang(context).equals("ar")){
                rowView = inflater.inflate(R.layout.layout_categories_item_ar, null);
            }else {
                rowView = inflater.inflate(R.layout.layout_categories_item, null);
            }

            //
            ViewHolder viewHolder = new ViewHolder();
            //
            viewHolder.categoryImg = rowView.findViewById(R.id.categoryImg);
            viewHolder.categoryName = rowView.findViewById(R.id.categoryName);

            viewHolder.itemLayout = rowView.findViewById(R.id.itemLayout);

            rowView.setTag(viewHolder);
        }

        int w = GlobalFunctions.convertDpToPx(context, 375);
        int h = GlobalFunctions.convertDpToPx(context, 150);

        int nw = width;
        int nh = h;

        if (nw != w) {

            float ratio = (float)nw / (float)w;

            nh = (int)(h * ratio);
        }

        final ViewHolder holder = (ViewHolder)rowView.getTag();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.categoryImg.getLayoutParams();

        params.height = nh;

        holder.categoryImg.setLayoutParams(params);

        holder.itemLayout.setLayoutParams(params);


        holder.categoryName.setText(arrNames.get(position).toString());

        Picasso.with(context).
                load(arrImages.get(position))

                .into(holder.categoryImg);


//        String s = arrNames.get(position);
//
//        try {
//
//            final JSONObject obj = new JSONObject(s);
//
//            holder.categoryImg.setImageDrawable(null);
//
//            ImageLoader.getInstance().displayImage(obj.getString("image"), holder.categoryImg);
//
//        } catch (JSONException e) {
//
//            Log.d("CategoriesAdapter", "JSONException:" + e.getMessage());
//
//        }

        return rowView;
    }
}
