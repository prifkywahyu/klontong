package com.klontong.mobileapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.TtsSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.klontong.mobileapp.dialog.CustomDialogCancel;

import java.util.concurrent.TimeUnit;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 21-Oct-18.
 */

public class KodeOTP extends AppCompatActivity {

    private int SEND_SMS_CODE = 1;
    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    TextView nomor, warung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kode_verifikasi);
        requestSmsPermission();
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Verifikasi OTP");

        changeToolbarFont((Toolbar) findViewById(R.id.toolbar_main), this);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        editText = findViewById(R.id.kodeOtp);
        nomor = findViewById(R.id.testTwo);
        warung = findViewById(R.id.testOne);

        Intent intent = getIntent();
        String two = intent.getStringExtra("namaWarung");
        String one = intent.getStringExtra("nomorPonsel");
        warung.setText(two);
        nomor.setText(one);

        String phonenumber = getIntent().getStringExtra("nomorPonsel");
        sendVerificationCode(phonenumber);

        findViewById(R.id.verif_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {
                    Toast.makeText(KodeOTP.this, "Kode verifikasi harus benar", Toast.LENGTH_LONG).show();
                } else {
                    verifyCode(code);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        CustomDialogCancel cancelOne = new CustomDialogCancel(KodeOTP.this);
        cancelOne.show();
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String toSavePonsel = nomor.getText().toString();
                        String toSaveWarung = warung.getText().toString();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(KodeOTP.this, OnboardingMain.class);
                            intent.putExtra("toNomorPonsel", toSavePonsel);
                            intent.putExtra("toNamaWarung", toSaveWarung);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(KodeOTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(KodeOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Meminta Izin")
                    .setMessage("Izin dibutuhkan untuk mengirim SMS ke Nomor Ponsel")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(KodeOTP.this,
                                    new String[] {android.Manifest.permission.SEND_SMS}, SEND_SMS_CODE);
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.SEND_SMS}, SEND_SMS_CODE);
        }
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
}
