package com.codewithmuddasir.statussaver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.Constants;
import com.codewithmuddasir.statussaver.util.LocalStorage;
import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class FragmentChat extends Fragment {

    Activity activity;

    public FragmentChat(Activity activity) {
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
        view = inflater.inflate(R.layout.fragment_chat, container, false);


        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(v -> {
            DashboardFragment dashboardFragment = new DashboardFragment(activity);

            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root, dashboardFragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        EditText message = view.findViewById(R.id.message);
        EditText phoneNo = view.findViewById(R.id.mobileNo);
        CountryCodePicker country = view.findViewById(R.id.country);

        MaterialButton sendMessage = view.findViewById(R.id.sendMessage);

        sendMessage.setOnClickListener(v -> {
            if (phoneNo.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            } else {
                String phoneNumber = country.getSelectedCountryCode() + phoneNo.getText().toString();

                Intent openWebsiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=" + phoneNumber + "&text=" + message.getText()));
                startActivity(openWebsiteIntent);

            }
        });


        return view;
    }
}
