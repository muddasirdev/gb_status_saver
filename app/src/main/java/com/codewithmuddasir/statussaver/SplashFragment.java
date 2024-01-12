package com.codewithmuddasir.statussaver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SplashFragment extends Fragment {

    Activity activity;
    boolean firstTime;

    public SplashFragment(boolean firstTime, Activity activity) {
        this.activity = activity;
        this.firstTime = firstTime;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_splash, container, false);

        if (!firstTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (activity instanceof AppCompatActivity) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;

                        DashboardFragment dashboardFragment = new DashboardFragment(activity);

                        FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.root, dashboardFragment);
                        ft.commit();
                    }
                }
            }, 2000);
        }

        return view;
    }
}