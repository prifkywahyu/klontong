package com.klontong.mobileapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 03-May-18.
 */

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText gantiSandi;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeToolbarFont((Toolbar) findViewById(R.id.toolbar_main), this);

        reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(this);

        gantiSandi = findViewById(R.id.email_reset);
        gantiSandi.setText(getResources().getString(R.string.kode_indo));
        Selection.setSelection(gantiSandi.getText(), gantiSandi.getText().length());
        gantiSandi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().startsWith("+62")) {
                    gantiSandi.setText(getResources().getString(R.string.kode_indo));
                    Selection.setSelection(gantiSandi.getText(), gantiSandi.getText().length());
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        if(view==reset) {
            Toast.makeText(ResetPassword.this, "Reset Password belum berfungsi", Toast.LENGTH_SHORT).show();
        }
    }
}
