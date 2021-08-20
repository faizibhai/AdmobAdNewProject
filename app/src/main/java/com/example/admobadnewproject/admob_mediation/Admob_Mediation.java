package com.example.admobadnewproject.admob_mediation;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.admobadnewproject.R;
import com.example.admobadnewproject.checkInternet.Network_Connectivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

///    admob ads global class
public class Admob_Mediation extends Application {
    public AdView adView;
    InterstitialAd interstitialAd1;
    AdRequest adRequest;
    AdLoader adLoader;
    NativeAdView nativeAdView;
    FrameLayout nativeContainer;
    NativeAd nativeAd1;
    String TAG = "admob_ad";
   // public InterstitialManager adsManager;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e(TAG, "admob sdk initialization completed");
            }
        });

        loadAdmobBannerAd(); // admob banner
        loadAdmobInterstitialAd(); // admob intersitial
        loadNativeAd(); // admob native
    }

    void loadAdmobBannerAd(){
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.banner_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    // Native Ad
    public  void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null || nativeAd.getStarRating() < 3) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

    void loadNativeAd(){
        adLoader = new AdLoader.Builder(this, getString(R.string.native_id))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                        nativeAd1 = nativeAd;
                        Log.e(TAG,"native ad: laoded success");
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError error) {
                        Log.e(TAG, "native ad : loading failed  "+error.toString());
                    }
                }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public  void show_mediation_NativeAd(Activity activity, View view) {
        if (nativeAd1 != null){
            nativeContainer = view.findViewById(R.id.native_container);
            nativeAdView = (NativeAdView) activity.getLayoutInflater()
                    .inflate(R.layout.mediation_native_ad, null);

            populateNativeAdView(nativeAd1, nativeAdView);
            nativeContainer.removeAllViews();
            nativeContainer.addView(nativeAdView);
            Log.e(TAG,"native ad: already loaded , shown");
        }
        else if (Network_Connectivity.isOnline(this)) {

            loadNativeAdAfterInternetConnected(activity, view);
            Log.e(TAG, "native ad: connected to internet, loading native...,");
        }

            else {
                Log.e(TAG,"native ad is null: not connected to internet ,, cannot load native ad,");
            }
    }

    void loadNativeAdAfterInternetConnected(Activity activity, View view){
        adLoader = new AdLoader.Builder(this, getString(R.string.native_id))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                        nativeAd1 = nativeAd;
                        Log.e(TAG,"native ad: loaded success");
                        nativeContainer = view.findViewById(R.id.native_container);
                        nativeAdView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(R.layout.mediation_native_ad, null);

                        populateNativeAdView(nativeAd1, nativeAdView);
                        nativeContainer.removeAllViews();
                        nativeContainer.addView(nativeAdView);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError error) {
                        Log.e(TAG, "native ad: loading failed   "+error.toString());
                        nativeAd1 = null;
                    }
                }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    void loadAdmobInterstitialAd(){
        adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.interstitial_id),
                adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitialAd1 = interstitialAd;
                        Log.e(TAG, "interstitial ad: loaded success");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.e(TAG, "interstitial ad: loading failed: "+loadAdError.getMessage());
                        interstitialAd1 = null;
                    }
                });

    }

    public void showAdmobInterstitialAd(Activity activity){

        if (interstitialAd1 != null ){
            interstitialAd1.show(activity);
            loadAdmobInterstitialAd();
            Log.e(TAG, "interstitial not null showing  "+activity.getLocalClassName());
        }
        else if (Network_Connectivity.isOnline(this)){
            loadAdmobInterstitialAd();
            Log.e(TAG, "interstitial ad:  connected to net reloading ad   " +activity.getLocalClassName());
            //loadInter();
        }
        else {
            Log.e(TAG, "interstitial ad:  null  " +activity.getLocalClassName());
        }

    }

    public void showBanner(LinearLayout layAd) {

        // Locate the Banner Ad in activity xml
        if (adView.getParent() != null) {
            ViewGroup tempVg = (ViewGroup) adView.getParent();
            tempVg.removeView(adView);
            Log.e(TAG, "banner ad: already loaded : shown" );
        }
        else {
            Log.e(TAG, " banner ad: null");
            //loadBannerAgain();
        }
        layAd.addView(adView);
    }

}
