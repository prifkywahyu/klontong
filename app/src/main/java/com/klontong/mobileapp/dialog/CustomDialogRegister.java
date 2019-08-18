package com.klontong.mobileapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.klontong.mobileapp.LoginMain;
import com.klontong.mobileapp.R;

/**
 * Created by Rifky on 31-Oct-18.
 */

public class CustomDialogRegister extends Dialog implements android.view.View.OnClickListener {

    private Activity mAct;
    private Button goTo;

    public CustomDialogRegister(@NonNull Activity context) {
        super(context);
        // TODO Auto-generated constuctor stub
        this.mAct = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_register);
        goTo = findViewById(R.id.successToLogin);
        goTo.setOnClickListener(this);
        Dialog mDialog = this;
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        if (view == goTo) {
            Intent goBack = new Intent(getContext(), LoginMain.class);
            getContext().startActivity(goBack);
            mAct.finish();
        }
    }
}
