package com.klontong.mobileapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.klontong.mobileapp.apihelper.BaseApiService;
import com.klontong.mobileapp.apihelper.UtilsApi;
import com.klontong.mobileapp.dialog.CustomDialogCancel;
import com.klontong.mobileapp.dialog.CustomDialogExist;
import com.klontong.mobileapp.dialog.CustomDialogRegister;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 01-Nov-18.
 */

public class OnboardingMain extends AppCompatActivity {

    TextInputLayout sma, smk;
    TextInputEditText one, two, newPass, typePass;
    Button send;
    ProgressDialog dialog;

    Context mContext;
    BaseApiService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_activity);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lengkapi Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeToolbarFont((Toolbar) findViewById(R.id.toolbar_main), this);

        Intent a = getIntent();
        String yaman = a.getStringExtra("toNomorPonsel");
        one = findViewById(R.id.oneParse);
        one.setText(yaman);
        Selection.setSelection(one.getText(), one.getText().length());
        one.addTextChangedListener(new TextWatcher() {
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
                Intent intent = getIntent();
                String ponsel = intent.getStringExtra("toNomorPonsel");

                if (!editable.toString().startsWith(ponsel)) {
                    one.setText(ponsel);
                    one.setKeyListener(null);
                    one.setEnabled(false);
                    Selection.setSelection(one.getText(), one.getText().length());
                }
            }
        });

        mContext = this;
        service = UtilsApi.getAPIService();
        sma = findViewById(R.id.parsePassword);
        smk = findViewById(R.id.parseRetype);

        Intent b = getIntent();
        String yemen = b.getStringExtra("toNamaWarung");
        two = findViewById(R.id.twoParse);
        two.setText(yemen);
        Selection.setSelection(two.getText(), two.getText().length());
        two.addTextChangedListener(new TextWatcher() {
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
                Intent intel = getIntent();
                String warung1 = intel.getStringExtra("toNamaWarung");

                if (!editable.toString().startsWith(warung1)) {
                    two.setText(warung1);
                    two.setKeyListener(null);
                    two.setEnabled(false);
                    Selection.setSelection(two.getText(), two.getText().length());
                }
            }
        });

        newPass = findViewById(R.id.threeParse);
        newPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPass.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        newPass.setTransformationMethod(new PasswordTransformationMethod());
        newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newPass.getText().toString().length() != 0 || newPass.getText().toString().length() >= 8) {
                    sma.setError(null);
                    sma.setErrorEnabled(false);
                }
            }
        });

        typePass = findViewById(R.id.fourParse);
        typePass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        typePass.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        typePass.setTransformationMethod(new PasswordTransformationMethod());
        typePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ariana = newPass.getText().toString().trim();
                String taylor = typePass.getText().toString().trim();

                if (Objects.equals(ariana, taylor)) {
                    smk.setError(null);
                    smk.setErrorEnabled(false);
                }
            }
        });

        send = findViewById(R.id.btnKirim);
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String marker = newPass.getText().toString().trim();
                String steven = typePass.getText().toString().trim();

                if (marker.isEmpty()) {
                    sma.setErrorEnabled(true);
                    sma.setError("Password tidak boleh kosong");
                } else if (marker.length() < 8) {
                    sma.setErrorEnabled(true);
                    sma.setError("Password minimal 8 karakter");
                } else if (!Objects.equals(marker, steven)) {
                    smk.setErrorEnabled(true);
                    smk.setError("Password harus sama, periksa kembali");
                } else {
                    dialog = ProgressDialog.show(mContext, null, "Sedang memuat", true, false);
                    requestRegister();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
        CustomDialogCancel cancelTwo = new CustomDialogCancel(OnboardingMain.this);
        cancelTwo.show();
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

    private void requestRegister(){
        service.registerRequest(two.getText().toString().trim(),
                one.getText().toString().trim(),
                newPass.getText().toString().trim())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            dialog.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")) {
                                    CustomDialogRegister register = new CustomDialogRegister(OnboardingMain.this);
                                    register.show();
                                } else {
                                    CustomDialogExist exist = new CustomDialogExist(OnboardingMain.this);
                                    exist.show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        dialog.dismiss();
                        Toast.makeText(mContext, "Gagal terhubung ke jaringan", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
