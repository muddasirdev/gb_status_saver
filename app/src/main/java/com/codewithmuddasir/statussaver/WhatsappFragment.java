package com.codewithmuddasir.statussaver;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.codewithmuddasir.statussaver.adapter.MyPagerAdapter;
import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.LocalStorage;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

public class WhatsappFragment extends Fragment implements OnUserEarnedRewardListener {

    private Activity activity;
    private AppName appName;

    public  WhatsappFragment(Activity activity, AppName appName) {
        this.activity = activity;
        this.appName = appName;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_whatsapp, container, false);

        RewardedInterstitialAd.load(view.getContext(), view.getContext().getString(R.string.reward_ads_id), new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                rewardedInterstitialAd.show(activity, WhatsappFragment.this);
            }

        });

        TabLayout tabLayout = view.findViewById(R.id.topNav);
        ViewPager2 viewPager = view.findViewById(R.id.viewer);

        TextView formTitle = view.findViewById(R.id.formTitle);

        if (appName.equals(AppName.WHATSAPP)) {
            whatsapp(tabLayout, viewPager);
            formTitle.setText("Whatsapp Status");
        } else if (appName.equals(AppName.WHATSAPP_BUSINESS)) {
            whatsappBusiness(tabLayout, viewPager);
            formTitle.setText("Whatsapp Business Status");
        } else {
            ownApp(tabLayout, viewPager);
            formTitle.setText("Downloaded Status");
        }

        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(v -> {
            DashboardFragment dashboardFragment = new DashboardFragment(activity);

            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root, dashboardFragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        return view;
    }

    private void whatsapp(TabLayout tabLayout, ViewPager2 viewPager) {

        if (LocalStorage.getBooleanValue(getContext(), LocalStorage.PREF_KEY_WP_PERMISSION_GRANTED)) {

            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;

            MyPagerAdapter pagerAdapter = new MyPagerAdapter(appName, appCompatActivity);
            viewPager.setAdapter(pagerAdapter);


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.getTabAt(position).select();
                }
            });


        } else {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);

            PermissionFragment fragment = new PermissionFragment(appName);

            if (activity instanceof AppCompatActivity) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formLoad, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    private void whatsappBusiness(TabLayout tabLayout, ViewPager2 viewPager) {

        if (LocalStorage.getBooleanValue(getContext(), LocalStorage.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED)) {

            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;

            MyPagerAdapter pagerAdapter = new MyPagerAdapter(appName, appCompatActivity);
            viewPager.setAdapter(pagerAdapter);


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.getTabAt(position).select();
                }
            });


        } else {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);

            PermissionFragment fragment = new PermissionFragment(appName);

            if (activity instanceof AppCompatActivity) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formLoad, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    private void ownApp(TabLayout tabLayout, ViewPager2 viewPager) {

        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(appName, appCompatActivity);
        viewPager.setAdapter(pagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
    }
}