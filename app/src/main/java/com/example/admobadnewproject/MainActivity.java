package com.example.admobadnewproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admobadnewproject.admob_mediation.Admob_Mediation;


public class MainActivity extends AppCompatActivity {
    Admob_Mediation app; //  abmob mediation class object
    LinearLayout bannerLayout;  //  linear layout for banner ad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAdmobMediationAd();
    }

    void showAdmobMediationAd(){
        bannerLayout = findViewById(R.id.bannerLayout);
        app = (Admob_Mediation) getApplication();
        app.showBanner(bannerLayout);
    }

    public void showintersitialAd(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        app.showAdmobInterstitialAd(MainActivity.this); // show interstitial ad here
    } // on button click

    public void showintersitialAd2(View view){
        Intent intent = new Intent(this, Third_Screen.class);
        startActivity(intent);
        app.showAdmobInterstitialAd(MainActivity.this); // show interstitial ad here
    } // on button click

    @Override
    protected void onResume() {
        super.onResume();
        app.showBanner(bannerLayout);
    }
}