package com.abdsoft.msbtestudyguide;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View homeView;

    HomeActivity homeActivity;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);

        homeActivity = (HomeActivity)getActivity();

        homeActivity.setTitle(R.string.app_name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if ((homeActivity.checkSelfPermission(android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                requestPermissions(new String[]{android.Manifest.permission.INTERNET}, 1);
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        // Inflate the layout for this fragment

        View.OnClickListener btnHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                HomeActivity homeActivity = (HomeActivity)getActivity();
                switch (id)
                {
                    case R.id.button_cir:
                    {
                        homeActivity.userChoice = "Cir";
                        homeActivity.drawer.setSelection(2, false);
                        homeActivity.showDepartmentFragment("Home");
                        homeActivity.optionSelected = true;
                    }
                    break;

                    case R.id.button_ques_paper:
                    {
                        homeActivity.userChoice = "QP";
                        homeActivity.drawer.setSelection(3, false);
                        homeActivity.showDepartmentFragment("Home");
                        homeActivity.optionSelected = true;
                    }
                    break;

                    case R.id.button_ans_paper:
                    {
                        homeActivity.userChoice  = "MAP";
                        homeActivity.drawer.setSelection(4, false);
                        homeActivity.showDepartmentFragment("Home");
                        homeActivity.optionSelected = true;
                    }
                    break;

                    case R.id.button_about:
                    {
                        homeActivity.drawer.setSelection(6, false);
                        Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(aboutIntent);
                    }
                    break;

                    case R.id.button_downloads:
                    {
                        homeActivity.userChoice  = "Downloads";
                        homeActivity.drawer.setSelection(5, false);
                        /*Intent downloadsIntent = new Intent(getActivity(), DownloadsActivity.class);
                        startActivity(downloadsIntent);*/
                        homeActivity.showDownloadsFragment("Home");
                        homeActivity.optionSelected = true;
                    }
                    break;

                    case R.id.button_exit:
                    {
                        homeActivity.drawer.setSelection(8, false);
                        getActivity().finish();
                    }
                    break;
                }
            }
        };

        int btnArr[] = {R.id.button_cir, R.id.button_ques_paper, R.id.button_ans_paper, R.id.button_about, R.id.button_downloads, R.id.button_exit};
        for(int iTmp = 0; iTmp < btnArr.length; iTmp++)
        {
            CardView btn =  homeView.findViewById(btnArr[iTmp]);
            btn.setOnClickListener(btnHandler);
        }

        return homeView;
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

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        homeActivity.optionSelected = false;
        homeActivity.setTitle(R.string.app_name);
        homeActivity.getSupportActionBar().setSubtitle("");
        homeActivity.drawer.setSelection(1, false);
    }
}
