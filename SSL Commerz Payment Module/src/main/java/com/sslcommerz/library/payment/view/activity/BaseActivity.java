package com.sslcommerz.library.payment.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sslcommerz.library.payment.viewmodel.management.PayUsingSSLCommerz;
import com.sslcommerz.library.payment.viewmodel.listener.PermissionListener;
import com.sslcommerz.library.payment.model.util.ErrorKeys;

import java.util.Map;

/**
 * Created by SSL_ZAHID on 4/17/2016.
 */
abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";
    private PermissionListener permissionListener;
    private static final int reqCode = 580;
    public ProgressDialog progressDialog;
    public DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
        }catch (Exception e){}
        progressDialog = new ProgressDialog(this);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initialize();
        viewRelatedTask();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initialize();
        viewRelatedTask();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }


    ImageView toolbarIndicator;
    TextView toolbarTitle;
    protected void setToolbar(String title, boolean hasBackButton){
        toolbarIndicator = (ImageView) findViewById(R.id.toolbarIndicator);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);
        if (hasBackButton) {
            toolbarIndicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    protected void showToast(String message){
        showToast(message, true);
    }

    protected void showToast(String message,boolean isLong){
        Toast.makeText(this, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        PayUsingSSLCommerz.paymentResultListener.error(ErrorKeys.CANCEL_TRANSACTION_ERROR);
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showProgressDialog(String message,boolean isCancelable){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();
    }

    public void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    protected void checkPermission(String permission, PermissionListener permissionListener){
        try {
            this.permissionListener = permissionListener;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, reqCode);
                } else {
                    this.permissionListener.permissionGranted();
                }
            } else {
                this.permissionListener.permissionGranted();
            }
        }catch (Exception e){this.permissionListener.permissionDenied();}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case reqCode : {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        this.permissionListener.permissionGranted();
                    } else {
                        this.permissionListener.permissionDenied();
                    }
                }
            }
        }catch (Exception e){}
    }

    protected abstract void initialize();

    protected abstract void viewRelatedTask();

    protected void error(int errorCode) {
        PayUsingSSLCommerz.paymentResultListener.error(errorCode);
        finish();
    }

}