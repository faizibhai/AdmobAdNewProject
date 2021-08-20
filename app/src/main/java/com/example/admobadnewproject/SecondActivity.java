package com.example.admobadnewproject;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admobadnewproject.admob_mediation.Admob_Mediation;


public class SecondActivity extends AppCompatActivity {

    Admob_Mediation app; //  abmob mediation class object ,, global class
    RelativeLayout view;
    LinearLayout bannerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        showNewMediationAd(); // show admob ads here

    }

    void showNewMediationAd(){
        bannerLayout = findViewById(R.id.bannerLayout);
        view = findViewById(R.id.mainLayout);
        app = (Admob_Mediation) getApplication();
        app.show_mediation_NativeAd(this,view);
        app.showBanner(bannerLayout);
    }
}