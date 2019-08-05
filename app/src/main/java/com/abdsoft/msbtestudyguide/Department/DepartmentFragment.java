package com.abdsoft.msbtestudyguide.Department;


import android.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment
{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    View deptView;

    HomeActivity homeActivity;

    private List<DeptItem> deptItems;

    public DepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        deptView = inflater.inflate(R.layout.fragment_department, container, false);


        homeActivity = (HomeActivity)getActivity();
        homeActivity.setTitle("Departments");
//        homeActivity.getSupportActionBar().setSubtitle("Choose Department");

        recyclerView = deptView.findViewById(R.id.recyclerview_dept);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AdView mAdView;

        mAdView = deptView.findViewById(R.id.adView_dept);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        deptItems = new ArrayList<>();

        String deptNames [] = new String[]{"Auto. Engineering", "Civil Engineering", "Computer Engineering", "ENTC Engineering", "Elect Engineering", "IT Engineering", "Mechanical Engineering"};
        for (int iTmp = 0; iTmp < deptNames.length; iTmp++)
        {
            DeptItem deptItem = new DeptItem(deptNames[iTmp]);
            deptItems.add(deptItem);
        }

        adapter = new DeptAdapter(deptItems, getActivity());
        recyclerView.setAdapter(adapter);

        return deptView;
    }

    @Override
    public void onResume() {
        super.onResume();

        homeActivity.setTitle("Departments");
//        homeActivity.getSupportActionBar().setSubtitle("Choose Department");
    }
}
