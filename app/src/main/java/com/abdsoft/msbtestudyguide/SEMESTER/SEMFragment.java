package com.abdsoft.msbtestudyguide.SEMESTER;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;
import com.abdsoft.msbtestudyguide.SEMESTER.SEMAdapter;
import com.abdsoft.msbtestudyguide.SEMESTER.SEMItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


public class SEMFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    View semView;

    private List<SEMItem> semItems;

    HomeActivity homeActivity;

    public SEMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        semView =  inflater.inflate(R.layout.fragment_sem, container, false);


        homeActivity = (HomeActivity)getActivity();
        homeActivity.setTitle("Semesters");

        recyclerView = semView.findViewById(R.id.recyclerview_sem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AdView mAdView;

        mAdView = semView.findViewById(R.id.adView_sem);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        semItems = new ArrayList<>();

        String semNames [] = new String[]{"I SEM", "II SEM", "III SEM", "IV SEM", "V SEM", "VI SEM"};
        for (int iTmp = 0; iTmp < semNames.length; iTmp++)
        {
            SEMItem semItem = new SEMItem(semNames[iTmp]);
            semItems.add(semItem);
        }

        adapter = new SEMAdapter(semItems, getActivity());
        recyclerView.setAdapter(adapter);

        return semView;
    }

    @Override
    public void onResume() {
        super.onResume();

        homeActivity.setTitle("Semesters");
    }
}
