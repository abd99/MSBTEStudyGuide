package com.abdsoft.msbtestudyguide.Downloads;


import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abdsoft.msbtestudyguide.Downloads.DownloadsAdapter;
import com.abdsoft.msbtestudyguide.Downloads.DownloadsItem;
import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    View downloadsView;

    private List<DownloadsItem> downloadsItems;

    HomeActivity homeActivity;

    private TextView textEmptyDownloads;

    public DownloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Downloads");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_menu:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if ((homeActivity.checkSelfPermission(android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    {
                        Snackbar.make(homeActivity.mRootView, "You have to grant permissions", Snackbar.LENGTH_SHORT).show();
                        requestPermissions(new String[]{android.Manifest.permission.INTERNET}, 1);
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    else
                        getFileList();
                }
                else
                    getFileList();
                Toast.makeText(getActivity(), "Refresh Complete", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.refresh_menu).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        downloadsView =  inflater.inflate(R.layout.fragment_downloads, container, false);

        homeActivity = (HomeActivity)getActivity();
        homeActivity.setTitle("Downloads");


        textEmptyDownloads = downloadsView.findViewById(R.id.textView_empty_downloads);

        AdView mAdView;

        mAdView = downloadsView.findViewById(R.id.adView_downloads);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        homeActivity.drawer.setSelection(5, false);

        recyclerView = downloadsView.findViewById(R.id.recyclerview_downloads);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if ((homeActivity.checkSelfPermission(android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                requestPermissions(new String[]{android.Manifest.permission.INTERNET}, 1);
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            else
                getFileList();
        }
        else
            getFileList();

        return downloadsView;
    }

    public void getFileList()
    {

        downloadsItems = new ArrayList<>();

        String filePath = Environment.getExternalStorageDirectory().toString() + "/Download/" + getString(R.string.app_name);

        File objFile = new File(filePath);

        String [] fileNames = objFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".pdf");
            }
        });


        if (fileNames==null){
            textEmptyDownloads.setVisibility(View.VISIBLE);
        }
        else {
            if (fileNames.length == 0)
                textEmptyDownloads.setVisibility(View.VISIBLE);
            else {
                textEmptyDownloads.setVisibility(View.GONE);
                for (int iTmp = 0; iTmp < fileNames.length; iTmp++) {
                    DownloadsItem downloadsItem = new DownloadsItem(fileNames[iTmp]);
                    downloadsItems.add(downloadsItem);
                }
            }


            adapter = new DownloadsAdapter(downloadsItems, getActivity());
            recyclerView.setAdapter(adapter);
        }
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,files);
        ListView listView = (ListView)findViewById(R.id.allFiles);
        listView.setAdapter(adapter);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
