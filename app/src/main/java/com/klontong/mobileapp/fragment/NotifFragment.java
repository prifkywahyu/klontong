package com.klontong.mobileapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klontong.mobileapp.R;
import com.klontong.mobileapp.UnderDevelopment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 14-Apr-18.
 */

public class NotifFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notif_page,container,false);

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_main);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Daftar Langganan");
        changeToolbarFont((Toolbar) view.findViewById(R.id.toolbar_main), getActivity());

        return view;
    }

    Context newBase = (Context) getContext();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.langganan, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_tambah:
                Intent plus = new Intent(getContext(), UnderDevelopment.class);
                startActivity(plus);
                return true;
            case R.id.navigation_help_three:
                Intent bantuTiga = new Intent(getContext(), UnderDevelopment.class);
                startActivity(bantuTiga);
                return true;
            case R.id.navigation_faq_three:
                Intent faqTiga = new Intent(getContext(), UnderDevelopment.class);
                startActivity(faqTiga);
                return true;
            case R.id.navigation_setting_three:
                Intent setTiga = new Intent(getContext(), UnderDevelopment.class);
                startActivity(setTiga);
                return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(CalligraphyContextWrapper.wrap(newBase));

    }

    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.otf"));
        tv.setTextColor(ColorStateList.valueOf(Color.WHITE));
    }
}
