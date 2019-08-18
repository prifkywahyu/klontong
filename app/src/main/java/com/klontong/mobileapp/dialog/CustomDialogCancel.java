package com.klontong.mobileapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.klontong.mobileapp.JoinMain;
import com.klontong.mobileapp.R;

/**
 * Created by Rifky on 31-Oct-18.
 */

public class CustomDialogCancel extends Dialog implements android.view.View.OnClickListener {

    private Activity tesAct;

    public CustomDialogCancel(@NonNull Activity context) {
        super(context);
        // TODO Auto-generated constuctor stub
        this.tesAct = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_cancel);
        Button batal = findViewById(R.id.forBatal);
        batal.setOnClickListener(this);
        Button yakin = findViewById(R.id.forYakin);
        yakin.setOnClickListener(this);
        Dialog mDialog = this;
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forBatal:
                dismiss();
                break;
            case R.id.forYakin:
                Intent forSure = new Intent(getContext(), JoinMain.class);
                getContext().startActivity(forSure);
                tesAct.finish();
                break;
        }
        dismiss();
    }
}
