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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klontong.mobileapp.R;
import com.klontong.mobileapp.SharedPrefManager;
import com.klontong.mobileapp.UnderDevelopment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 14-Apr-18.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    String resultUser;
    TextView user;
    CardView isi_air, pesan_galon, pesan_gas;
    SharedPrefManager prefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page,container,false);

        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Klontong");
        changeToolbarFont((Toolbar) view.findViewById(R.id.toolbar_main), getActivity());
        prefManager = new SharedPrefManager(activity);

        user = view.findViewById(R.id.textUser);
        resultUser = prefManager.getSpNamaWrg();
        user.setText(resultUser);

        isi_air = view.findViewById(R.id.card_refill);
        isi_air.setOnClickListener(this);
        pesan_galon = view.findViewById(R.id.card_galon);
        pesan_galon.setOnClickListener(this);
        pesan_gas = view.findViewById(R.id.card_gas);
        pesan_gas.setOnClickListener(this);

        return view;
    }

    Context newBase = (Context) getContext();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);

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
            case R.id.nav_lokasi:
                Intent lokasi = new Intent(getContext(), UnderDevelopment.class);
                startActivity(lokasi);
                return true;
            case R.id.navigation_help_one:
                Intent bantuSatu = new Intent(getContext(), UnderDevelopment.class);
                startActivity(bantuSatu);
                return true;
            case R.id.navigation_faq_one:
                Intent faqSatu = new Intent(getContext(), UnderDevelopment.class);
                startActivity(faqSatu);
                return true;
            case R.id.navigation_setting_one:
                Intent setSatu = new Intent(getContext(), UnderDevelopment.class);
                startActivity(setSatu);
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
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_bold.otf"));
        tv.setTextColor(ColorStateList.valueOf(Color.WHITE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_refill:
                Intent refill = new Intent(getContext(), UnderDevelopment.class);
                startActivity(refill);
                break;
            case R.id.card_galon:
                Intent galon = new Intent(getContext(), UnderDevelopment.class);
                startActivity(galon);
                break;
            case R.id.card_gas:
                Intent gas = new Intent(getContext(), UnderDevelopment.class);
                startActivity(gas);
                break;
        }
    }
}
