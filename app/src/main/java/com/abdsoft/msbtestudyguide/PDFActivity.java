package com.abdsoft.msbtestudyguide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PDFActivity extends AppCompatActivity {

    String filePath = new String();
    String fileName = new String();
    String userChoice = new String();
    int instNo = 3;

    private InterstitialAd mInterstitialAd;


    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private PDFView pdfView;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private TextView txtPageno;
    private ProgressBar progressBar;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {

        if (instNo % 3 == 0) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdf);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.black_overlay)));

        Intent intent = getIntent();

        filePath = intent.getStringExtra("path");
        fileName = intent.getStringExtra("filename");
        instNo = intent.getIntExtra("instance", 3);
        userChoice = intent.getStringExtra("userChoice");

        setTitle(fileName);
//        actionBar.setSubtitle(fileName);

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.inter_pdf));
        if (instNo % 3 == 0) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }



        txtPageno = findViewById(R.id.page_no_full);
        progressBar = findViewById(R.id.progress_pdf);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.pdf_full);
        pdfView = (PDFView) mContentView;


        if (userChoice.equals("Downloads")){

            progressBar.setVisibility(View.GONE);
            pdfView.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + getString(R.string.app_name) + "/" + filePath)).onPageChange(new OnPageChangeListener() {
                @Override
                public void onPageChanged(int page, int pageCount) {
                    page++;
                    txtPageno.setText("Page :" + page + "/" + pageCount);
                    if (txtPageno.getVisibility() == View.GONE) {
                        txtPageno.setVisibility(View.VISIBLE);
                        delayedHide(3000);
                    }
                }
            }).spacing(10).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    Toast.makeText(getApplicationContext(), "Loading Complete", Toast.LENGTH_SHORT).show();
                }
            }).onError(new OnErrorListener() {
                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }).onPageError(new OnPageErrorListener() {
                @Override
                public void onPageError(int page, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Page Error: Page: " + page + "  " + t, Toast.LENGTH_LONG).show();
                }
            }).load();

        }
        else
        {
            if (isOffline()) {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                finish();
            }


            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference reference = firebaseStorage.getReference().child(filePath);

            final long ONE_MEGABYTE = 1024 * 1024 * 60;
            reference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    progressBar.setVisibility(View.GONE);
                    pdfView.fromBytes(bytes).onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            page++;
                            txtPageno.setText("Page :" + page + "/" + pageCount);
                            if (txtPageno.getVisibility() == View.GONE) {
                                txtPageno.setVisibility(View.VISIBLE);
                                delayedHide(3000);
                            }
                        }
                    }).spacing(10).onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            Toast.makeText(getApplicationContext(), "Loading Complete", Toast.LENGTH_SHORT).show();
                        }
                    }).onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    }).onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Page Error: Page: " + page + "  " + t, Toast.LENGTH_LONG).show();
                        }
                    }).load();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressBar.setVisibility(View.GONE);
                    if (e.toString().equals("com.google.firebase.storage.StorageException: Object does not exist at location.")) {
                        Toast.makeText(getApplicationContext(), "Sorry, this file is currently not available.", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInAnonymously();
                    String userName = mAuth.getCurrentUser().getUid().toString();
                    DatabaseReference myRef = database.getReference("Error on " + userName + "'s device: " + " " + getLocalClassName() + ": ");

                    String feedbackText = new String(e.fillInStackTrace().toString() + " " + filePath);

                    myRef.setValue(feedbackText);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Toast.makeText(getApplicationContext(), "Error Reported", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
//                            Toast.makeText(getApplicationContext(), "Failed to report error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();
                }
            });
        }


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        txtPageno.setVisibility(View.GONE);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);

        txtPageno.setVisibility(View.VISIBLE);

    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    private boolean isOffline() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return !(manager != null
                && manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnectedOrConnecting());
    }
}
