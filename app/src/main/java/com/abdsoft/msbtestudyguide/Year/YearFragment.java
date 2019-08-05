package com.abdsoft.msbtestudyguide.Year;


import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class YearFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    View yearView;

    String[] yearNames;

    HomeActivity homeActivity;


    private List<YearItem> yearItems;

    public YearFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        yearView =  inflater.inflate(R.layout.fragment_year, container, false);

        homeActivity = (HomeActivity)getActivity();

        homeActivity.setTitle("Examination Year");


        if (isOffline()) {
            showSnackbar("No Internet Connection");
        }

        recyclerView = yearView.findViewById(R.id.recyclerview_year);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AdView mAdView;

        mAdView = yearView.findViewById(R.id.adView_year);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        yearItems = new ArrayList<>();


        Toast.makeText(getActivity(), homeActivity.userChoice, Toast.LENGTH_LONG);
        switch (homeActivity.userChoice)
        {
            case "QP":
                yearNames = new String[]{"2015 Summer Question Paper", "2015 Winter Question Paper", "2016 Summer Question Paper", "2016 Winter Question Paper", "2017 Summer Question Paper", "2017 Winter question paper "};
                break;

            case "MAP":
                yearNames = new String[]{"2015 Summer Model Answer Paper", "2015 Winter Model Answer Paper", "2016 Summer Model Answer Paper", "2016 Winter Model Answer Paper", "2017 Summer Model Answer Paper", "2017 Winter Model Answer paper ", "2018 Summer model answer paper"};
                break;

            default:
                yearNames = new String[]{""};
                break;
        }


        for (int iTmp = 0; iTmp < yearNames.length; iTmp++)
        {
            YearItem yearItem = new YearItem(yearNames[iTmp]);
            yearItems.add(yearItem);
        }

        adapter = new YearAdapter(yearItems, getActivity());
        recyclerView.setAdapter(adapter);

        return yearView;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if ((homeActivity.checkSelfPermission(android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                Snackbar.make(homeActivity.mRootView, "You have to grant permissions", Snackbar.LENGTH_LONG).show();
            }
            else {
                YearAdapter yearAdapter = (YearAdapter) adapter;
                yearAdapter.downloadFile();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        homeActivity.setTitle("Examination Year");
    }
}
