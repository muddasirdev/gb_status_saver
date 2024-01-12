package com.codewithmuddasir.statussaver;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codewithmuddasir.statussaver.splash.FirstScreen;
import com.codewithmuddasir.statussaver.splash.FourthScreen;
import com.codewithmuddasir.statussaver.splash.SecondScreen;
import com.codewithmuddasir.statussaver.splash.ThirdScreen;

public class ViewPager extends FragmentPagerAdapter {

    Activity activity;

    public ViewPager(@NonNull FragmentManager fm, int behavior, Activity activity) {
        super(fm, behavior);

        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 : return new FirstScreen();
            case 1 : return new SecondScreen();
            case 2 : return new ThirdScreen(activity);
            case 3 : return new FourthScreen(activity);
            case 4 : return new SplashFragment(true, activity);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
