package com.klontong.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 16-Aug-18.
 */

public class JoinMain extends AppCompatActivity {

    Button masuk, daftar;
    private LinearLayout dotsLayout;
    SharedPrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        ViewPager pager = findViewById(R.id.slidePager);
        dotsLayout = findViewById(R.id.dots);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        pager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        pager.addOnPageChangeListener(listener);
        prefManager = new SharedPrefManager(this);

        masuk = findViewById(R.id.to_login_button);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(JoinMain.this, LoginMain.class);
                startActivity(login);
                finish();
            }
        });

        daftar = findViewById(R.id.to_register_button);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegis = new Intent(JoinMain.this, RegisterMain.class);
                startActivity(toRegis);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (prefManager.getSpAlready()) {
            startActivity(new Intent(JoinMain.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    public void addDotsIndicator(int position) {
        TextView[] mDots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(32);
            mDots[i].setTextColor(getResources().getColor(R.color.colorWhite));

            dotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorButton));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
