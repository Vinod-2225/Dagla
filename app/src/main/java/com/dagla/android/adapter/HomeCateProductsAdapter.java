package com.dagla.android.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.dagla.android.GlobalFunctions;
import com.dagla.android.R;
import com.dagla.android.fragments.ProductDescriptionFragment;
import com.dagla.android.parser.HomeCateProductsDetails;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class HomeCateProductsAdapter extends RecyclerView.Adapter<HomeCateProductsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<HomeCateProductsDetails> mList;

    ClickListener clickListener;

    int row_index = 0;
    private final int width;

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;

    }

    public interface ClickListener {
        void OnItemClick(int position, View v);
    }


    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout pnlHolder;
        ImageView imgPic;
        TextView lblNew, lblSale, lblBrand, lblName, lblPrice, lblOldPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            pnlHolder = itemView.findViewById(R.id.pnlHolder);
            imgPic = itemView.findViewById(R.id.imgPic);
            lblNew = itemView.findViewById(R.id.lblNew);
            lblSale = itemView.findViewById(R.id.lblSale);
            lblBrand = itemView.findViewById(R.id.lblBrand);
            lblName = itemView.findViewById(R.id.lblName);
            lblPrice = itemView.findViewById(R.id.lblPrice);
            lblOldPrice = itemView.findViewById(R.id.lblOldPrice);

            lblOldPrice.setPaintFlags(lblOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.OnItemClick(getAdapterPosition(), v);
        }

    }




    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeCateProductsAdapter(Context context, ArrayList<HomeCateProductsDetails> mList, int width) {
        super();
        this.mContext = context;
        this.mList = mList;


        this.width = width;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;

//        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_1_item_layout, parent, false);
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element holder1.imgLike.setTag(position);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)holder.pnlHolder.getLayoutParams();
        ViewGroup.LayoutParams paramsImg = holder.imgPic.getLayoutParams();

        int w = GlobalFunctions.convertDpToPx(mContext, 180);
        int h = GlobalFunctions.convertDpToPx(mContext, 370);

        int nw = (width - GlobalFunctions.convertDpToPx(mContext, 45)) / 2;
        int nh = h;

        if (nw != w) {

            float ratio = (float)nw / (float)w;

            nh = (int)(h * ratio);
        }

        params.width = nw;
        params.height = nh;

        holder.pnlHolder.setLayoutParams(params);

        h = GlobalFunctions.convertDpToPx(mContext, 226);

        if (nw != w) {

            float ratio = (float)nw / (float)w;

            nh = (int)(h * ratio);
        }

        paramsImg.height = nh;

        holder.imgPic.setLayoutParams(paramsImg);

        holder.imgPic.setImageDrawable(null);


        if (!mList.get(position).getImage().equalsIgnoreCase("")) {

            ImageLoader.getInstance().displayImage(mList.get(position).getImage(), holder.imgPic);

        }

        holder.lblBrand.setText(mList.get(position).getBannerName());

        holder.lblName.setText(mList.get(position).getName());

        holder.lblPrice.setText(mList.get(position).getPrice());
        holder.lblOldPrice.setText(mList.get(position).getOldPrice());

        if(!mList.get(position).getOldPrice().equals("")){
            holder.lblOldPrice.setVisibility(View.VISIBLE);
        }else {
            holder.lblOldPrice.setVisibility(View.GONE);
        }

        holder.lblNew.setVisibility(View.GONE);
        holder.lblSale.setVisibility(View.GONE);

//        if (!holder.lblOldPrice.getText().toString().equalsIgnoreCase("")) {
//
//            holder.lblSale.setVisibility(View.VISIBLE);
//
//        } else if (obj.getBoolean("is_new")) {
//
//            holder.lblNew.setVisibility(View.VISIBLE);
//        }

        holder.pnlHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ProductDescriptionFragment fragment = new ProductDescriptionFragment();
//                Bundle b = new Bundle();
//                b.putString("obj", s);
//                b.putString("title", ""+holder.lblName.getText().toString());
//                fragment.setArguments(b);
//
//
//                activity = ((FragmentActivity) context);
//                ft = activity.getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, fragment, "ProductDescriptionFragment")
//                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.addToBackStack(null);
//                ft.commitAllowingStateLoss();

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void Selected(int pos){
        this.row_index = pos;
        notifyDataSetChanged();
    }
}
