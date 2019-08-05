package com.abdsoft.msbtestudyguide;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {

    EditText textFeedback;
    String feedbackText;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter_feedback));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        AdView mAdView;
        mAdView = (AdView) findViewById(R.id.adView_feedback);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        textFeedback = findViewById(R.id.textView_feedback);

        feedbackText = new String();
        Button btn = findViewById(R.id.button_feedback);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOffline()) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(textFeedback.getText().toString()))
                {
                    textFeedback.setError("This cannot be empty");
                }
                else
                {
                    try {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signInAnonymously();
                        String userName = mAuth.getCurrentUser().getUid().toString();
                        DatabaseReference myRef = database.getReference("feeback by " + userName);

                        feedbackText = textFeedback.getText().toString();

                        myRef.setValue(feedbackText);


                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Toast.makeText(getApplicationContext(), "Feedback Sent", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Failed to send feedback", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error occurred: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }

    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();

        super.onBackPressed();

    }


    private boolean isOffline() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return !(manager != null
                && manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnectedOrConnecting());
    }


}
