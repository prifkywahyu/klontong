package com.klontong.mobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;

import com.klontong.mobileapp.fragment.HomeFragment;
import com.klontong.mobileapp.fragment.NotifFragment;
import com.klontong.mobileapp.fragment.OrderFragment;
import com.klontong.mobileapp.fragment.ProfileFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private NotifFragment notifFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView forNav = findViewById(R.id.nav_main);

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        notifFragment = new NotifFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);

        BottomBarHelper.disableShiftMode(forNav);

        forNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.navigation_order:
                        setFragment(orderFragment);
                        return true;

                    case R.id.navigation_notifications:
                        setFragment(notifFragment);
                        return true;

                    case R.id.navigation_profile:
                        setFragment(profileFragment);
                        return true;

                        default:
                        return false;
                }
            }

        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
