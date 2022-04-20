package com.dagla.android.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dagla.android.GlobalFunctions;
import com.dagla.android.R;
import com.dagla.android.activity.MainActivity;
import com.dagla.android.adapter.BestSellerAdapter;
import com.dagla.android.adapter.EditorialChoiceAdapter;
import com.dagla.android.adapter.WhatsNewAdapter;
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

import cz.msebera.android.httpclient.Header;

public class HomeFragmentNew extends Fragment {

    Activity act;

    ViewPager vPagerImages1,vPagerImages2,vPagerImages3;
    TabLayout tabDots1,tabDots2,tabDots3;
    View rootView;
    Context context;
    Bundle savedInstanceState;

    WhatsNewAdapter whatsNewAdapter;
    BestSellerAdapter bestSellerAdapter;
    EditorialChoiceAdapter editorialChoiceAdapter;

    Dialog dlgLoading = null;

    Typeface custom_fontbold, custom_fontnormal;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        custom_fontbold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/avenir-next-bold.ttf");
        custom_fontnormal = Typeface.createFromAsset(getActivity().getAssets(), "fonts/avenir-next-regular.ttf");

        ((MainActivity) getActivity()).setHeaders(getResources().getString(R.string.title_home),true,true,true,false, false ,"0", false);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);

            ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String, Integer>> arrayList2 = new ArrayList<HashMap<String, Integer>>();

            ArrayList<HashMap<String, String>> arrayList3 = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String, Integer>> arrayList4 = new ArrayList<HashMap<String, Integer>>();

            ArrayList<HashMap<String, String>> arrayList5 = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String, Integer>> arrayList6 = new ArrayList<HashMap<String, Integer>>();

            ArrayList arrNames = new ArrayList<String>();

            arrNames.add("T Shirt");
            arrNames.add("Shoes");
            arrNames.add("Purse");
            arrNames.add("Leather Jacket");
            arrNames.add("Jacket");
            arrNames.add("Belt");

            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("Key1",arrNames.get(0).toString());
            map1.put("Key2",arrNames.get(1).toString());
            arrayList1.add(map1);

            HashMap<String, String> map2 = new HashMap<String, String>();
            map2.put("Key1",arrNames.get(2).toString());
            map2.put("Key2",arrNames.get(3).toString());
            arrayList1.add(map2);

            HashMap<String, String> map3 = new HashMap<String, String>();
            map3.put("Key1",arrNames.get(4).toString());
            map3.put("Key2",arrNames.get(5).toString());
            arrayList1.add(map3);


            HashMap<String, Integer> imagesMap1 = new HashMap<String, Integer>();
            imagesMap1.put("Key1",R.drawable.categories_1);
            imagesMap1.put("Key2",R.drawable.categories_2);
            arrayList2.add(imagesMap1);

            HashMap<String, Integer> imagesMap2 = new HashMap<String, Integer>();
            imagesMap2.put("Key1",R.drawable.categories_3);
            imagesMap2.put("Key2",R.drawable.categories_4);
            arrayList2.add(imagesMap2);

            HashMap<String, Integer> imagesMap3 = new HashMap<String, Integer>();
            imagesMap3.put("Key1",R.drawable.categories_5);
            imagesMap3.put("Key2",R.drawable.categories_6);
            arrayList2.add(imagesMap3);

////////////////////////////
            HashMap<String, String> bestHashMap1 = new HashMap<String, String>();
            bestHashMap1.put("Key1",arrNames.get(2).toString());
            bestHashMap1.put("Key2",arrNames.get(3).toString());
            arrayList3.add(bestHashMap1);

            HashMap<String, String> bestHashMap2 = new HashMap<String, String>();
            bestHashMap2.put("Key1",arrNames.get(4).toString());
            bestHashMap2.put("Key2",arrNames.get(5).toString());
            arrayList3.add(bestHashMap2);

            HashMap<String, String> bestHashMap3 = new HashMap<String, String>();
            bestHashMap3.put("Key1",arrNames.get(0).toString());
            bestHashMap3.put("Key2",arrNames.get(1).toString());
            arrayList3.add(bestHashMap3);


            HashMap<String, Integer> bestImageHashMap1 = new HashMap<String, Integer>();
            bestImageHashMap1.put("Key1",R.drawable.categories_3);
            bestImageHashMap1.put("Key2",R.drawable.categories_4);
            arrayList4.add(bestImageHashMap1);

            HashMap<String, Integer> bestImageHashMap2 = new HashMap<String, Integer>();
            bestImageHashMap2.put("Key1",R.drawable.categories_5);
            bestImageHashMap2.put("Key2",R.drawable.categories_6);
            arrayList4.add(bestImageHashMap2);

            HashMap<String, Integer> bestImageHashMap3 = new HashMap<String, Integer>();
            bestImageHashMap3.put("Key1",R.drawable.categories_1);
            bestImageHashMap3.put("Key2",R.drawable.categories_2);
            arrayList4.add(imagesMap3);

/////////////////////////////////////////////////////////

            HashMap<String, String> editHashMap1 = new HashMap<String, String>();
            editHashMap1.put("Key1",arrNames.get(4).toString());
            editHashMap1.put("Key2",arrNames.get(5).toString());
            arrayList5.add(editHashMap1);

            HashMap<String, String> editHashMap2 = new HashMap<String, String>();
            editHashMap2.put("Key1",arrNames.get(0).toString());
            editHashMap2.put("Key2",arrNames.get(1).toString());
            arrayList5.add(editHashMap2);

            HashMap<String, String> editHashMap3 = new HashMap<String, String>();
            editHashMap3.put("Key1",arrNames.get(2).toString());
            editHashMap3.put("Key2",arrNames.get(3).toString());
            arrayList5.add(editHashMap3);


            HashMap<String, Integer> editImageHashMap1 = new HashMap<String, Integer>();
            editImageHashMap1.put("Key1",R.drawable.categories_5);
            editImageHashMap1.put("Key2",R.drawable.categories_6);
            arrayList6.add(editImageHashMap1);

            HashMap<String, Integer> editImageHashMap2 = new HashMap<String, Integer>();
            editImageHashMap2.put("Key1",R.drawable.categories_1);
            editImageHashMap2.put("Key2",R.drawable.categories_2);
            arrayList6.add(editImageHashMap2);

            HashMap<String, Integer> editImageHashMap3 = new HashMap<String, Integer>();
            editImageHashMap3.put("Key1",R.drawable.categories_3);
            editImageHashMap3.put("Key2",R.drawable.categories_4);
            arrayList6.add(editImageHashMap3);


            vPagerImages1 = rootView.findViewById(R.id.vPagerImages1);
            vPagerImages2 = rootView.findViewById(R.id.vPagerImages2);
            vPagerImages3 = rootView.findViewById(R.id.vPagerImages3);

            tabDots1 = rootView.findViewById(R.id.tabDots1);
            tabDots2 = rootView.findViewById(R.id.tabDots2);
            tabDots3 = rootView.findViewById(R.id.tabDots3);


//            whatsNewAdapter = new WhatsNewAdapter(getActivity(), arrayList1, arrayList2);
//            vPagerImages1.setAdapter(whatsNewAdapter);
//            tabDots1.setupWithViewPager(vPagerImages1, true);


//            bestSellerAdapter = new BestSellerAdapter(getActivity(), arrayList3, arrayList4);
//            vPagerImages2.setAdapter(bestSellerAdapter);
//            tabDots2.setupWithViewPager(vPagerImages2, true);
//
//
//            editorialChoiceAdapter = new EditorialChoiceAdapter(getActivity(), arrayList5, arrayList6);
//            vPagerImages3.setAdapter(editorialChoiceAdapter);
//            tabDots3.setupWithViewPager(vPagerImages3, true);

        }

        return rootView;

    }


    private void loadData() {

        if (GlobalFunctions.hasConnection(context)) {

            showLoading();

            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();

            params.put("ran", GlobalFunctions.getRandom());

            client.get(GlobalFunctions.serviceURL + "getCategoryData", params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {

                    hideLoading();

                    String response = new String(bytes);

                    Log.d("onSuccess", response);

                    JSONArray arr, arr1;
                    JSONObject obj, obj1, obj2;

                    try {

                        obj = ((JSONObject) new JSONTokener(response).nextValue()).getJSONObject("result");

                        if (obj.getString("status").equalsIgnoreCase("1")) {

                            arr = obj.getJSONArray("Banners");

//                            arrList = new ArrayList<String>();
//
//                            for (int i = 0; i < arr.length(); i++) {
//
//                                arrList.add(arr.getJSONObject(i).toString());
//                            }


                        } else {

                            GlobalFunctions.showToastError(getActivity(), obj.getString("msg"));

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


}
