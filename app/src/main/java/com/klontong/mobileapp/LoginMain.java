package com.klontong.mobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.klontong.mobileapp.apihelper.BaseApiService;
import com.klontong.mobileapp.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Rifky on 13-Mar-18.
 */

public class LoginMain extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout ponselSaya, sandiSaya;
    TextInputEditText ponsel, pass;
    Button masuk, lupa, reg;
    ProgressDialog progressDialog;

    Context forContext;
    BaseApiService apiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        forContext = this;
        apiService = UtilsApi.getAPIService();
        ponselSaya = findViewById(R.id.cekSatu);
        sandiSaya = findViewById(R.id.cekDua);
        masuk = findViewById(R.id.login_button);
        masuk.setOnClickListener(this);

        ponsel = findViewById(R.id.telepon_main);
        ponsel.setText(getResources().getString(R.string.kode_indo));
        Selection.setSelection(ponsel.getText(), ponsel.getText().length());
        ponsel.addTextChangedListener(new TextWatcher() {
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
                    ponsel.setText(getResources().getString(R.string.kode_indo));
                    Selection.setSelection(ponsel.getText(), ponsel.getText().length());
                }
                if (ponsel.getText().toString().length() >= 12) {
                    ponselSaya.setError(null);
                    ponselSaya.setErrorEnabled(false);
                }
            }
        });

        pass = findViewById(R.id.password_main);
        pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pass.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        pass.setTransformationMethod(new PasswordTransformationMethod());
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (pass.getText().toString().length() != 0 || pass.getText().toString().length() >= 8) {
                    sandiSaya.setError(null);
                    sandiSaya.setErrorEnabled(false);
                }
            }
        });

        lupa = findViewById(R.id.lupass);
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget = new Intent(LoginMain.this, ResetPassword.class);
                startActivity(forget);
            }
        });

        reg = findViewById(R.id.to_reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regy = new Intent(LoginMain.this, RegisterMain.class);
                startActivity(regy);
                finish();
            }
        });

        sharedPrefManager = new SharedPrefManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPrefManager.getSpAlready()) {
            startActivity(new Intent(LoginMain.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back = new Intent(LoginMain.this, JoinMain.class);
            startActivity(back);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void requestLogin(){
        apiService.loginRequest(ponsel.getText().toString().trim(), pass.getText().toString().trim())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ALREADY, true);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA_WRG, nama);
                                    Intent intent = new Intent(forContext, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(forContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        progressDialog.dismiss();
                        Toast.makeText(forContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        String novice = ponsel.getText().toString().trim();
        String newbie = pass.getText().toString().trim();

        switch (view.getId()) {
            case R.id.login_button:
                if (novice.length() < 12) {
                    ponselSaya.setErrorEnabled(true);
                    ponselSaya.setError("Nomor Ponsel belum valid");
                } else if (newbie.isEmpty()) {
                    sandiSaya.setErrorEnabled(true);
                    sandiSaya.setError("Password tidak boleh kosong");
                } else if (newbie.length() < 8) {
                    sandiSaya.setErrorEnabled(true);
                    sandiSaya.setError("Password minimal 8 karakter");
                } else {
                    Intent move = new Intent(LoginMain.this, MainActivity.class);
                    move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(move);
                    finish();
                }
        }
    }
}
