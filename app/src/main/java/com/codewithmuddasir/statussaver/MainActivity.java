package com.codewithmuddasir.statussaver;

import android.graphics.Color;
import android.os.Bundle;

import com.codewithmuddasir.statussaver.util.LocalStorage;
import com.cuberto.liquid_swipe.LiquidPager;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.codewithmuddasir.statussaver.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static int showAdsCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (LocalStorage.getBooleanValue(this, LocalStorage.APP_FIRST_TIME_OPEN)) {
            SplashFragment dashboardFragment = new SplashFragment(false,MainActivity.this);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root, dashboardFragment);
            ft.commit();
        } else {
            LiquidPager liquidSwipe = binding.pager;
            liquidSwipe.setButtonDrawable(R.drawable.icon_arrow_sec);

            ViewPager viewPager = new ViewPager(getSupportFragmentManager(), 1, this);

            liquidSwipe.setAdapter(viewPager);

            liquidSwipe.setOnPageChangeListener(new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    if (position == 1 || position == 3) {
                        liquidSwipe.setButtonDrawable(R.drawable.icon_arrow_first);
                    } else {
                        liquidSwipe.setButtonDrawable(R.drawable.icon_arrow_sec);
                    }

                    if (position == 4) {
                        LocalStorage.putBooleanValue(MainActivity.this, LocalStorage.APP_FIRST_TIME_OPEN, true);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DashboardFragment dashboardFragment = new DashboardFragment(MainActivity.this);

                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.root, dashboardFragment);
                                ft.commit();
                            }
                        }, 7000);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                boolean value = snapshot.getValue(Boolean.class);
                HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

// Now you can extract the value you need, for example:
                Boolean value = (Boolean) dataMap.get("status");

                if (!value) {
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Data", "Failed to read value.", error.toException());
            }
        });

    }
    private View findNextButton(View view) {
        // Implement a method to traverse the view hierarchy and find the next button
        if (view instanceof Button) {
            return view;
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                View nextButton = findNextButton(child);
                if (nextButton != null) {
                    return nextButton;
                }
            }
        }
        return null;
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
            } else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Double click back to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
            }
        }
    }


}