package com.codewithmuddasir.statussaver.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.codewithmuddasir.statussaver.WhatsappStatusFragment;
import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.FileType;

import java.io.File;

public class MyPagerAdapter extends FragmentStateAdapter {

    private AppName appName;

    public MyPagerAdapter(AppName appName, @NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.appName = appName;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0 :
                return new WhatsappStatusFragment(FileType.IMAGE_FILE, appName);
            case 1 :
                return new WhatsappStatusFragment(FileType.VIDEO_FILE, appName);
            default:
                return new WhatsappStatusFragment(FileType.IMAGE_FILE, appName);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
