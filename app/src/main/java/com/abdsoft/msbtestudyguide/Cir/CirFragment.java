package com.abdsoft.msbtestudyguide.Cir;


import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CirFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    View cirView;

    private List<CirItem> cirItems;

    String[] cirNames;

    HomeActivity homeActivity;

    public CirFragment() {
        // Required empty public constructor
    }

    private AdView mAdView;

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Semesters");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cirView = inflater.inflate(R.layout.fragment_cir, container, false);

        homeActivity = (HomeActivity)getActivity();
        homeActivity.setTitle("Semesters");

        if (isOffline()) {
            showSnackbar("No Internet Connection");
        }

        mAdView = cirView.findViewById(R.id.adView_cir);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = cirView.findViewById(R.id.recyclerview_cir);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cirItems = new ArrayList<>();

        cirNames = new String[]{"I SEM", "II SEM", "III SEM", "IV SEM", "V SEM", "VI SEM"};

        for (int iTmp = 0; iTmp < cirNames.length; iTmp++)
        {
            CirItem cirItem = new CirItem(cirNames[iTmp]);
            cirItems.add(cirItem);
        }

        adapter = new CirAdapter(cirItems, getActivity());
        recyclerView.setAdapter(adapter);


        return cirView;
    }

    private boolean isOffline() {
        ConnectivityManager manager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return !(manager != null
                && manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnectedOrConnecting());
    }

    private void showSnackbar(String errorMessageRes) {
        Snackbar.make(homeActivity.mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

}
