package com.codewithmuddasir.statussaver.splash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codewithmuddasir.statussaver.R;

public class ThirdScreen extends Fragment {

    Activity activity;

    public ThirdScreen(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        if (activity instanceof AppCompatActivity) {
//            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
//
//            FourthScreen fourthScreen = new FourthScreen(activity);
//
//            FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.root, fourthScreen);
//            ft.commit();
//        }


        return inflater.inflate(R.layout.fragment_thrid_screen, container, false);
    }
}