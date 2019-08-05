package com.abdsoft.msbtestudyguide;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AboutActivity extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AdView mAdView;
        mAdView = findViewById(R.id.adView_about);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter_about));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        TextView textView = findViewById(R.id.privacy_link);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://msbtestudyguide.firebaseapp.com"));
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.feedback_menu:
                Intent feedbackIntent = new Intent(getApplicationContext(),FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        super.onBackPressed();
    }
}
