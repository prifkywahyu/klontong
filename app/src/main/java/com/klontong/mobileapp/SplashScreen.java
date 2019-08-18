package com.klontong.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 15-Apr-18.
 */

public class SplashScreen extends AppCompatActivity {

    private boolean internetCheck = true;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        spinner = findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, android.R.color.white),
                PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.VISIBLE);

        PostDelayedMethod();
    }

    public void DialogAppear() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_network, null);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Button goOut = view.findViewById(R.id.toOut);
        goOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button goRetry = view.findViewById(R.id.toRetry);
        goRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                recreate();
            }
        });
    }

    protected boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return internetCheck;
                }
            }
        }
        return false;
    }

    public void PostDelayedMethod() {
        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean InternetResult = checkConnection();
                if (InternetResult) {
                    Intent intent = new Intent(SplashScreen.this, JoinMain.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    spinner.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);

                    DialogAppear();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    public boolean checkConnection() {
        if (isOnline()) {
            return internetCheck;
        }
        else {
            internetCheck = false;
            return false;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
