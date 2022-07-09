package com.dagla.android.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dagla.android.GlobalFunctions;
import com.dagla.android.R;
import com.dagla.android.activity.MainActivity;
import com.dagla.android.adapter.BestSellerAdapter;
import com.dagla.android.adapter.EditorialChoiceAdapter;
import com.dagla.android.adapter.HomeCateAdapter;
import com.dagla.android.adapter.HomeCateProductsAdapter;
import com.dagla.android.adapter.ProductColorsAdapter;
import com.dagla.android.adapter.WhatsNewAdapter;
import com.dagla.android.parser.HomeCateDetails;
import com.dagla.android.parser.HomeCateProductsDetails;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class HomeFragmentNew extends Fragment {

    Activity act;

    ViewPager vPagerImages;
    TabLayout tabDots;
    View rootView;
    Bundle savedInstanceState;



    Dialog dlgLoading = null;

    HomeCateDetails homeCateDetails;
    ArrayList<HomeCateDetails> homeCateDetailsArrayList = new ArrayList<HomeCateDetails>();
    HomeCateProductsDetails homeCateProductsDetails;
    ArrayList<HomeCateProductsDetails> homeCateProductsDetailsArrayList = new ArrayList<HomeCateProductsDetails>();

    RecyclerView cate_recyclerView,products_recyclerView;
    HomeCateAdapter homeCateAdapter;
    HomeCateProductsAdapter homeCateProductsAdapter;

    ArrayList<String> arrItems;

    DisplayMetrics displaymetrics;
    GridLayoutManager layoutManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        if(GlobalFunctions.getLang(getActivity()).equals("ar")){
            ((MainActivity) getActivity()).setHeaders(getResources().getString(R.string.title_home_ar),true,true,true,false, false ,"0", false);
        }else {
            ((MainActivity) getActivity()).setHeaders(getResources().getString(R.string.title_home),true,true,true,false, false ,"0", false);
        }

        ((MainActivity) getActivity()).setCartCount();

        GlobalFunctions.initImageLoader(getActivity());

        if (rootView == null) {

            if(GlobalFunctions.getLang(getActivity()).equals("ar")){
                rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
            }else {
                rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
            }

            displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            vPagerImages = rootView.findViewById(R.id.vPagerImages);
            tabDots = rootView.findViewById(R.id.tabDots);

            cate_recyclerView = rootView.findViewById(R.id.cate_recyclerView);

            cate_recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
            homeCateAdapter = new HomeCateAdapter(requireActivity(), homeCateDetailsArrayList);
            cate_recyclerView.setAdapter(homeCateAdapter);

            homeCateAdapter.setOnClickListener(new HomeCateAdapter.ClickListener() {
                @Override
                public void OnItemClick(int position, View v) {


//                    homeCateProductsDetailsArrayList.clear();
//                    homeCateProductsAdapter = new HomeCateProductsAdapter(requireActivity(), homeCateDetailsArrayList.get(position).getHomeCateProducts(),displaymetrics.widthPixels);
//                    products_recyclerView.setAdapter(homeCateProductsAdapter);
                    homeCateProductsAdapter = new HomeCateProductsAdapter(requireActivity(), homeCateDetailsArrayList.get(position).getHomeCateProducts(),displaymetrics.widthPixels);
                    products_recyclerView.setAdapter(homeCateProductsAdapter);
                    homeCateAdapter.Selected(position);

                }
            });

            products_recyclerView = rootView.findViewById(R.id.products_recyclerView);

            products_recyclerView.setNestedScrollingEnabled(false);
            //
            layoutManager = new GridLayoutManager(getActivity(), 2);
            //
            products_recyclerView.setLayoutManager(layoutManager);

//            homeCateProductsAdapter = new HomeCateProductsAdapter(requireActivity(), homeCateProductsDetailsArrayList,displaymetrics.widthPixels);
//            products_recyclerView.setAdapter(homeCateProductsAdapter);
//
//            homeCateProductsAdapter.setOnClickListener(new HomeCateProductsAdapter.ClickListener() {
//                @Override
//                public void OnItemClick(int position, View v) {
//
//                    homeCateProductsAdapter.Selected(position);
////                    homeCateProductsDetailsArrayList.clear();
////                    homeCateProductsAdapter = new HomeCateProductsAdapter(requireActivity(), homeCateDetailsArrayList.get(position).getHomeCateProducts(),displaymetrics.widthPixels);
////                    products_recyclerView.setAdapter(homeCateProductsAdapter);
//                }
//            });

            loadData();


        }



        return rootView;

    }

    private void loadData() {

        if (GlobalFunctions.hasConnection(getActivity())) {

            homeCateDetailsArrayList.clear();
            homeCateProductsDetailsArrayList.clear();

            showLoading();

            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();

            params.put("ran", GlobalFunctions.getRandom());

            if(!GlobalFunctions.getPrefrences(getActivity(), "CountryCurrency").equals("")){
                params.put("curr", GlobalFunctions.getPrefrences(getActivity(), "CountryCurrency"));
            }


            client.get(GlobalFunctions.serviceURL + "getHomeBannerData", params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {

                    hideLoading();

                    String response = new String(bytes);

                    Log.d("onSuccess", response);

                    JSONArray arr, arr1, arr2;
                    JSONObject obj, obj1, obj2;



                    try {

                        obj = ((JSONObject) new JSONTokener(response).nextValue()).getJSONObject("result");

                        if (obj.getString("status").equalsIgnoreCase("1")) {

                            arr = obj.getJSONArray("Banners");


                            for (int i = 0; i < arr.length(); i++) {


                                String main_banner_Id = arr.getJSONObject(i).getString("banner_id");
                                String home_banner_cat_id = arr.getJSONObject(i).getString("home_banner_cat_id");
                                String home_banner_cat_name_en = arr.getJSONObject(i).getString("home_banner_cat_name");
                                String home_banner_cat_name_ar = arr.getJSONObject(i).getString("home_banner_cat_name_ar");
                                String banner_cat_id = arr.getJSONObject(i).getString("banner_cat_id");
                                String banner_sub_cat_id = arr.getJSONObject(i).getString("banner_sub_cat_id");
                                String banner_product_id = arr.getJSONObject(i).getString("banner_product_id");
                                String banner_brand_id = arr.getJSONObject(i).getString("banner_brand_id");
                                String image = arr.getJSONObject(i).getString("image");



                                arr1 = arr.getJSONObject(i).getJSONArray("Products");

                                homeCateProductsDetailsArrayList = new ArrayList<HomeCateProductsDetails>();

                                for (int j = 0; j < arr1.length(); j++) {

                                    String banner_id = arr1.getJSONObject(j).getString("banner_id");
                                    String product_id = arr1.getJSONObject(j).getString("product_id");
                                    String name = arr1.getJSONObject(j).getString("name");
                                    String brand_name = arr1.getJSONObject(j).getString("brand_name");
                                    String price = arr1.getJSONObject(j).getString("price");
                                    String old_price = arr1.getJSONObject(j).getString("old_price");

                                    String pic = arr1.getJSONObject(j).getString("pic");


                                    homeCateProductsDetails = new HomeCateProductsDetails(banner_id, product_id, name, brand_name, price, old_price, pic);

                                    homeCateProductsDetailsArrayList.add(homeCateProductsDetails);

                                }



                                homeCateDetails = new HomeCateDetails(main_banner_Id,home_banner_cat_id,home_banner_cat_name_en,banner_cat_id,
                                        banner_sub_cat_id,banner_product_id,banner_brand_id,image,homeCateProductsDetailsArrayList);
                                homeCateDetailsArrayList.add(homeCateDetails);

                                if(i==0){
//                                    homeCateProductsDetailsArrayList.addAll(homeCateProductsDetailsArrayList);
//                                    homeCateProductsAdapter.notifyDataSetChanged();

                                    homeCateProductsAdapter = new HomeCateProductsAdapter(requireActivity(), homeCateDetailsArrayList.get(i).getHomeCateProducts(),displaymetrics.widthPixels);
                                    products_recyclerView.setAdapter(homeCateProductsAdapter);

                                    homeCateProductsAdapter.setOnClickListener(new HomeCateProductsAdapter.ClickListener() {
                                        @Override
                                        public void OnItemClick(int position, View v) {

                                            homeCateProductsAdapter.Selected(position);
//                    homeCateProductsDetailsArrayList.clear();
//                    homeCateProductsAdapter = new HomeCateProductsAdapter(requireActivity(), homeCateDetailsArrayList.get(position).getHomeCateProducts(),displaymetrics.widthPixels);
//                    products_recyclerView.setAdapter(homeCateProductsAdapter);
                                        }
                                    });
                                }
                            }


                            CustomImagePagerAdapter mAdapter = new CustomImagePagerAdapter(getActivity(), homeCateDetailsArrayList);
                            vPagerImages.setAdapter(mAdapter);
                            vPagerImages.setCurrentItem(0);
                            tabDots.setupWithViewPager(vPagerImages, true);

                            homeCateAdapter.notifyDataSetChanged();

//                            homeCateProductsAdapter.notifyDataSetChanged();



                        } else {

                            GlobalFunctions.showToastError(getActivity(), obj.getString("msg"));

                        }

                        if(!GlobalFunctions.getPrefrences(getActivity(), "user_id").equalsIgnoreCase("")){
//                            loadProfileData();
                        }


                    } catch (JSONException e) {

                        if (GlobalFunctions.getLang(getActivity()).equals("ar")) {
                            GlobalFunctions.showToastError(getActivity(), getString(R.string.msg_error_ar));
                        }else {
                            GlobalFunctions.showToastError(getActivity(), getString(R.string.msg_error));
                        }

                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {

                    hideLoading();

                    if (GlobalFunctions.getLang(getActivity()).equals("ar")) {
                        GlobalFunctions.showToastError(getActivity(), getString(R.string.msg_error_ar));
                    }else {
                        GlobalFunctions.showToastError(getActivity(), getString(R.string.msg_error));
                    }

                }
            });

        } else {

            if (GlobalFunctions.getLang(getActivity()).equals("ar")) {
                GlobalFunctions.showToastError(getActivity(), getString(R.string.msg_no_internet_ar));
            }else {
                GlobalFunctions.showToastError(getActivity(), getString(R.string.msg_no_internet));
            }


        }

    }


    private void showLoading() {

        if (dlgLoading == null) {

            dlgLoading = GlobalFunctions.showLoading(getActivity());

        } else {

            dlgLoading.show();
        }

    }

    private void hideLoading() {

        dlgLoading.dismiss();

    }



    public class CustomImagePagerAdapter extends PagerAdapter {
        private Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<HomeCateDetails> homeCateDetailsArrayList1;

        public CustomImagePagerAdapter(Context context, ArrayList<HomeCateDetails> homeCateDetailsArrayList1) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.homeCateDetailsArrayList1 = homeCateDetailsArrayList1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = mLayoutInflater.inflate(R.layout.home_image_item,container,false);
            ImageView adImg = (ImageView) itemView.findViewById(R.id.adImg);
//            ImageView favoriteImg = (ImageView) itemView.findViewById(R.id.favoriteImg);

//            adImg.getLayoutParams().width = (int) (General.get_device_width(mContext) / 1) - 50;
//            adImg.getLayoutParams().height = (int) (General.get_device_width(mContext) / 2) - 80;

//            Picasso.with(mContext).load(mResources[position]).into(adImg);

            final ProgressBar progress1 = (ProgressBar) itemView.findViewById(R.id.progress);



            Glide.with(mContext)
                    .load(homeCateDetailsArrayList1.get(position).getImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            progress1.setVisibility(View.GONE);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            progress1.setVisibility(View.GONE);

                            return false;
                        }


                    })
                    .into(adImg);


            container.addView(itemView);





            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
//        Log.v("mResources.length",""+mResources.length);
            return homeCateDetailsArrayList1.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }

}
