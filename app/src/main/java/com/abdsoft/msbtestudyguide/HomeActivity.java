package com.abdsoft.msbtestudyguide;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.abdsoft.msbtestudyguide.Cir.CirFragment;
import com.abdsoft.msbtestudyguide.Department.DepartmentFragment;
import com.abdsoft.msbtestudyguide.Downloads.DownloadsFragment;
import com.abdsoft.msbtestudyguide.SEMESTER.SEMFragment;
import com.abdsoft.msbtestudyguide.Subject.SubjectFragment;
import com.abdsoft.msbtestudyguide.Year.YearFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class HomeActivity extends AppCompatActivity
{
    Toolbar toolbar;
    public Drawer drawer = null;
    public FragmentManager fragmentManager;
    public Fragment fragment;
    public String departmentSelected = new String();
    public String semesterSelected = new String();
    public String subjectSelected = new String();
    public String yearSelected = new String();
    public String userChoice = new String();
    public int instance = 2;
    FirebaseAuth firebaseAuth;

    boolean optionSelected = false;

    public View mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRootView = findViewById(R.id.rootview);

        fragmentManager = getFragmentManager();

        MobileAds.initialize(this,getString(R.string.admob_app_initialize_id));

        showHomeFragment();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Failed to establish connection with the server.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if ((checkSelfPermission(android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                requestPermissions(new String[]{android.Manifest.permission.INTERNET}, 1);
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }*/

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withSelectable(true).withIdentifier(1).withName("Home").withIcon(R.drawable.home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Curriculum").withIdentifier(2).withSelectable(true).withIcon(R.mipmap.cir);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Question Paper").withIdentifier(3).withSelectable(true).withIcon(R.mipmap.qp);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Model Answer Paper").withIdentifier(4).withSelectable(true).withIcon(R.mipmap.ap);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withName("Downloads").withIdentifier(5).withSelectable(true).withIcon(R.drawable.download);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withName("About").withIdentifier(6).withSelectable(true).withIcon(R.drawable.about);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withName("Feedback").withIdentifier(7).withSelectable(true).withIcon(R.drawable.feedback);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withName("Exit").withIdentifier(8).withSelectable(true).withIcon(R.drawable.exit);
        //create the drawer and remember the `Drawer` drawer object
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5,
                        item6,
                        item7,
                        item8
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (view.getId()){

                            case 1:
                                showHomeFragment();
                                drawer.closeDrawer();
                                break;

                            case 2:
                                userChoice = "Cir";
                                showDepartmentFragment("Home");
                                drawer.closeDrawer();
                                optionSelected = true;
                                break;


                            case 3:
                                userChoice = "QP";
                                showDepartmentFragment("Home");
                                drawer.closeDrawer();
                                optionSelected = true;
                                break;

                            case 4:
                                userChoice = "MAP";
                                showDepartmentFragment("Home");
                                drawer.closeDrawer();
                                optionSelected = true;
                                break;

                            case 5:
                                userChoice = "Downloads";
                                /*Intent downloadsIntent = new Intent(getApplicationContext(), DownloadsActivity.class);
                                startActivity(downloadsIntent);*/
                                showDownloadsFragment("Home");
                                drawer.closeDrawer();
                                optionSelected = true;
                                break;

                            case 6:
                                Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
                                startActivity(aboutIntent);
                                drawer.closeDrawer();
                                break;

                            case 7:
                                Intent feedbackIntent = new Intent(getApplicationContext(), FeedbackActivity.class);
                                startActivity(feedbackIntent);
                                drawer.closeDrawer();
                                break;

                            case 8:
                                finish();
                                drawer.closeDrawer();
                                break;

                            default:
                                break;
                        }
                        closeContextMenu();
//                        drawer.closeDrawer();
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();


        if (isOffline()){
            showSnackbar("Device offline, redirecting to Downloads.");
            userChoice = "Downloads";
            drawer.setSelection(5, false);
            Thread downloadsThread = new Thread(){
                public void run()
                {
                    try
                    {
                        sleep(1500);
                        showDownloadsFragment("Home");
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            downloadsThread.start();
        }


    }

    public void showHomeFragment()
    {
        fragment = new HomeFragment();
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).commit();
    }

    public void showDepartmentFragment(String backStackTag)
    {
        fragment = new DepartmentFragment();

        if (backStackTag.equals(null))
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack(backStackTag).commit();
    }

    public void showSEMFragment(String backStackTag)
    {
        fragment = new SEMFragment();
        if (backStackTag.equals(null))
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack(backStackTag).commit();
    }

    public void showSubjectFragment(String backStackTag)
    {
        fragment = new SubjectFragment();
        if (backStackTag.equals(null))
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack(backStackTag).commit();
    }

    public void showYearFragment(String backStackTag)
    {
        fragment = new YearFragment();
        if (backStackTag.equals(null))
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack(backStackTag).commit();
    }

    public void showCirFragment(String backStackTag)
    {
        fragment = new CirFragment();
        if (backStackTag.equals(null))
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack(backStackTag).commit();
    }

    public void showDownloadsFragment(String backStackTag)
    {
        fragment = new DownloadsFragment();

        if (backStackTag.equals(null))
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.enter_anim,  R.animator.exit_anim).replace(R.id.content_frame, fragment).addToBackStack(backStackTag).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds yearItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menus, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.exit_menu) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the fragment
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if ((checkSelfPermission(android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                Toast.makeText(getApplicationContext(), "You have to grant permission to access files", Toast.LENGTH_LONG).show();
            }
        }

    }*/


    @Override
    protected void onResume() {
        super.onResume();

        if (optionSelected){
            switch (userChoice){
                case "Cir":
                    drawer.setSelection(2, false);
                    break;

                case "QP":
                    drawer.setSelection(3, false);
                    break;

                case "MAP":
                    drawer.setSelection(4, false);
                    break;

                case "Downloads":
                    drawer.setSelection(5, false);
                    break;

            }
        } else {
            drawer.setSelection(1, false);
        }


    }


    private boolean isOffline() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return !(manager != null
                && manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnectedOrConnecting());
    }

    private void showSnackbar(String message) {
        Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG).show();
    }
}
