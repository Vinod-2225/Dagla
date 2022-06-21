package com.dagla.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dagla.android.AreasAdapter;
import com.dagla.android.GlobalFunctions;
import com.dagla.android.R;
import com.dagla.android.activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TailoringFragment extends Fragment {

    View rootView;
    Context context;
    Bundle savedInstanceState;

    Button btnHomeVisit,btnOrderTracking;

    Dialog dlgLoading = null;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        if(GlobalFunctions.getLang(getActivity()).equals("ar")){
            ((MainActivity) getActivity()).setHeaders(getResources().getString(R.string.tailoring_ar),false,false,false,true, false ,"0", false);
        }else {
            ((MainActivity) getActivity()).setHeaders(getResources().getString(R.string.tailoring),false,false,false,true, false ,"0", false);
        }


        if (rootView == null) {

            if(GlobalFunctions.getLang(getActivity()).equals("ar")){
                rootView = inflater.inflate(R.layout.fragment_tailoring_ar, container, false);
            }else {
                rootView = inflater.inflate(R.layout.fragment_tailoring, container, false);
            }


            btnHomeVisit = rootView.findViewById(R.id.btnHomeVisit);
            btnOrderTracking = rootView.findViewById(R.id.btnOrderTracking);


            btnHomeVisit.setVisibility(View.GONE);
            btnOrderTracking.setVisibility(View.VISIBLE);

            btnHomeVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HomeVisitServiceFragment fragment1 = new HomeVisitServiceFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
//                            .remove(fragment2)
//                            .add(R.id.fragment_container, fragment1, "RegisterFragment")
                            .replace(R.id.fragment_container, fragment1, "HomeVisitServiceFragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            // .setCustomAnimations(R.anim.right_to_left, R.anim.fadeout_2,0, R.anim.left_to_right)
                            .commitAllowingStateLoss();

                }
            });

            btnOrderTracking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    TrackYourDishdasha fragment1 = new TrackYourDishdasha();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
//                            .remove(fragment2)
//                            .add(R.id.fragment_container, fragment1, "RegisterFragment")
                            .replace(R.id.fragment_container, fragment1, "TrackYourDishdasha")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            // .setCustomAnimations(R.anim.right_to_left, R.anim.fadeout_2,0, R.anim.left_to_right)
                            .commitAllowingStateLoss();


                }
            });





        }

        return rootView;

    }





}
