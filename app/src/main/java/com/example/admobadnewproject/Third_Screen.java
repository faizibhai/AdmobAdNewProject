package com.example.admobadnewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.admobadnewproject.admob_mediation.Admob_Mediation;

public class Third_Screen extends AppCompatActivity {
    Admob_Mediation app; //  abmob mediation class object ,, global class
    RelativeLayout view;
    LinearLayout bannerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third__screen);

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