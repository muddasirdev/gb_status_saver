package com.codewithmuddasir.statussaver;

import static com.codewithmuddasir.statussaver.MainActivity.showAdsCounter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.Util;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class DashboardFragment extends Fragment {

    Activity activity;

    public DashboardFragment(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashbaord, container, false);

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = view.findViewById(R.id.homeScreenAds);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        LinearLayout whatsapp = view.findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(v -> {
            if (Util.isWhatsAppInstalled("com.whatsapp", activity.getPackageManager())) {
                WhatsappFragment fragment = new WhatsappFragment(activity, AppName.WHATSAPP);
                if (activity instanceof AppCompatActivity) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                    FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.root, fragment);
                    ft.addToBackStack(null);  // Optional: Add the transaction to the back stack
                    ft.commit();
                }
            } else {
                Toast.makeText(activity, "Whatsapp not install on this device", Toast.LENGTH_LONG).show();;
            }
        });

        LinearLayout whatsappBusiness = view.findViewById(R.id.whatsappBusiness);

        whatsappBusiness.setOnClickListener(v -> {
            if (Util.isWhatsAppInstalled("com.whatsapp.w4b", activity.getPackageManager())) {
                WhatsappFragment fragment = new WhatsappFragment(activity, AppName.WHATSAPP_BUSINESS);
                if (activity instanceof AppCompatActivity) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                    FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.root, fragment);
                    ft.addToBackStack(null);  // Optional: Add the transaction to the back stack
                    ft.commit();
                }
            } else {
                Toast.makeText(activity, "Whatsapp Business not install on this device", Toast.LENGTH_LONG).show();
            }
        });


        LinearLayout chat = view.findViewById(R.id.chat);

        chat.setOnClickListener(v -> {
            FragmentChat fragment = new FragmentChat(activity);
            if (activity instanceof AppCompatActivity) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.root, fragment);
                ft.addToBackStack(null);  // Optional: Add the transaction to the back stack
                ft.commit();
            }
        });


        LinearLayout downloadedStatus = view.findViewById(R.id.downloadedStatus);

        downloadedStatus.setOnClickListener(v -> {
            WhatsappFragment fragment = new WhatsappFragment(activity, AppName.OWN_APP);
            if (activity instanceof AppCompatActivity) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.root, fragment);
                ft.addToBackStack(null);  // Optional: Add the transaction to the back stack
                ft.commit();
            }
        });


        LinearLayout shareWithFriendStatus = view.findViewById(R.id.share);

        shareWithFriendStatus.setOnClickListener(v -> {
            shareWithFriend();
        });

        if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5000);
        }



        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
//        AdRequest adRequest = new AdRequest.Builder().build();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 5000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
            }
        }
    }

    private void shareWithFriend() {
        // Create an Intent with ACTION_SEND
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        // Set the content to be shared (you can change this as needed)
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool app! Download it from: https://example.com/app");

        // Set the MIME type
        sendIntent.setType("text/plain");

        // Start the chooser dialog to let the user pick an app for sharing
        startActivity(Intent.createChooser(sendIntent, "Share with Friend"));
    }
}