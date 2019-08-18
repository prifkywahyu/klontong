package com.klontong.mobileapp;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
 * Created by Rifky on 18-Apr-18.
 */

public class RegisterMain extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout ponselku, warungku;
    TextInputEditText telepon, warung;
    Button log, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        forStatement();
        register = findViewById(R.id.register_button);
        register.setOnClickListener(this);
        ponselku = findViewById(R.id.forPonsel);
        warungku = findViewById(R.id.forWarung);

        telepon = findViewById(R.id.telepon_back);
        telepon.setText(getResources().getString(R.string.kode_indo));
        Selection.setSelection(telepon.getText(), telepon.getText().length());
        telepon.addTextChangedListener(new TextWatcher() {
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
                    telepon.setText(getResources().getString(R.string.kode_indo));
                    Selection.setSelection(telepon.getText(), telepon.getText().length());
                }
                if (telepon.getText().toString().length() >= 12) {
                    ponselku.setError(null);
                    ponselku.setErrorEnabled(false);
                }
            }
        });

        warung = findViewById(R.id.warung_back);
        warung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (warung.getText().toString().length() != 0 || warung.getText().toString().length() >= 8) {
                    warungku.setError(null);
                    warungku.setErrorEnabled(false);
                }
            }
        });

        log = findViewById(R.id.to_log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logy = new Intent(RegisterMain.this, LoginMain.class);
                startActivity(logy);
                finish();
            }
        });
    }

    private void forStatement() {
        TextView textView = findViewById(R.id.pernyataan);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Spannable wordOne = new SpannableString("Dengan mendaftar, saya telah menyetujui tentang ");
        wordOne.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBottom)), 0, wordOne.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordOne);

        final Spannable wordTwo = new SpannableString("Syarat dan Ketentuan");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent snk = new Intent(RegisterMain.this, TermConds.class);
                startActivity(snk);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(true);
                super.updateDrawState(ds);
            }
        };
        wordTwo.setSpan(clickableSpan, 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);

        Spannable wordThree = new SpannableString(" yang ditetapkan Klontong");
        wordThree.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBottom)), 0, wordThree.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordThree);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back = new Intent(RegisterMain.this, JoinMain.class);
            startActivity(back);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        String nomor = telepon.getText().toString().trim();
        String shopName = warung.getText().toString().trim();

        switch (view.getId()) {
            case R.id.register_button:
                if (nomor.length() < 12) {
                    ponselku.setErrorEnabled(true);
                    ponselku.setError("Nomor Ponsel belum valid");
                } else if (shopName.isEmpty()) {
                    warungku.setErrorEnabled(true);
                    warungku.setError("Nama Warung tidak boleh kosong");
                } else if (shopName.length() < 8) {
                    warungku.setErrorEnabled(true);
                    warungku.setError("Masukkan minimal 8 karakter");
                } else {
                    Intent toOtp = new Intent(RegisterMain.this, KodeOTP.class);
                    toOtp.putExtra("nomorPonsel", nomor);
                    toOtp.putExtra("namaWarung", shopName);
                    startActivity(toOtp);
                    finish();
                }
        }
    }
}
