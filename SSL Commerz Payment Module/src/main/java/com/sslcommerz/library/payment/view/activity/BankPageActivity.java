package com.sslcommerz.library.payment.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;

import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.sslcommerz.library.payment.viewmodel.listener.BankInformationListener;
import com.sslcommerz.library.payment.viewmodel.listener.TransactionInformationListener;
import com.sslcommerz.library.payment.viewmodel.management.PayUsingSSLCommerz;
import com.sslcommerz.library.payment.model.datafield.MandatoryFieldModel;
import com.sslcommerz.library.payment.model.datamodel.AllInformation;
import com.sslcommerz.library.payment.model.dataset.BasicInformationModel;
import com.sslcommerz.library.payment.model.dataset.TransactionInfo;
import com.sslcommerz.library.payment.model.util.ErrorKeys;
import com.sslcommerz.library.payment.model.util.SdkCategory;
import com.sslcommerz.library.payment.model.util.SdkType;
import com.sslcommerz.library.payment.viewmodel.management.SSLCommerzManagement;

import java.util.HashMap;

/**
 * Created by SSL_ZAHID on 10/27/2016.
 */
public class BankPageActivity extends BaseActivity implements BankInformationListener, TransactionInformationListener {

    /*All View Init*/
    private WebView bankPage;
    private ProgressBar bankPageProgress;

    /*All Information*/
    private MandatoryFieldModel mandatoryFieldModel;
    private HashMap<String,String> requestMap;
    private BasicInformationModel basicInformationModel;
    //private int gatewayName;
    private String gatewayUrl,gatewayName;
    SSLCommerzManagement sslCommerzManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssl_commerz_bank_page_layout);
    }

    @Override
    protected void initialize() {
        bankPage = findViewById(R.id.bankPage);
        bankPageProgress = findViewById(R.id.bankPageProgress);
    }

    @Override
    protected void viewRelatedTask() {
        sslCommerzManagement = new SSLCommerzManagement(this);
        try{
            mandatoryFieldModel = (MandatoryFieldModel) getIntent().getSerializableExtra("mandatory_field");
            requestMap = (HashMap<String, String>) getIntent().getExtras().getSerializable("map_info");

            if(mandatoryFieldModel.getSdkCategory().equals(SdkCategory.BANK_PAGE)){
                setToolbar(mandatoryFieldModel.getBankName().toString(),true);
                sslCommerzManagement.getPrimaryTransactionInfo(requestMap, mandatoryFieldModel.getSdkType().equals(SdkType.LIVE), this);
            }else{
                basicInformationModel = (BasicInformationModel) getIntent().getSerializableExtra("bank_model");
                gatewayName = getIntent().getStringExtra("gateway_name");
                gatewayUrl = getIntent().getStringExtra("gateway_url");
                showTheWebsite(gatewayUrl);
                setToolbar(gatewayName,true);
            }

        }catch (Exception e){error(ErrorKeys.USER_INPUT_ERROR);}
    }

    private void showTheWebsite(String url){
        WebViewClient webViewClient=new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if(url.equals(AllInformation.TRANSACTION_END_POINT)){
                    sslCommerzManagement.getTransactionInformation(basicInformationModel.getSessionkey(),mandatoryFieldModel.getStoreId(),mandatoryFieldModel.getStorePassword(),mandatoryFieldModel.getSdkType().equals(SdkType.LIVE),BankPageActivity.this);
                }
            }
            @SuppressLint("NewApi")
            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BankPageActivity.this);
                builder.setMessage(R.string.notification_error_ssl_cert_invalid);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        bankPage.getSettings().setLoadsImagesAutomatically(true);
        bankPage.getSettings().setJavaScriptEnabled(true);
        bankPage.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        bankPage.setWebViewClient(webViewClient);
        bankPage.loadUrl(url);

        bankPage.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                bankPageProgress.setProgress(newProgress);
            }
        });
    }

    @Override
    public void startLoading(String requestId) {

    }

    @Override
    public void endLoading(String requestId) {

    }

    @Override
    public void getSuccessInformation(BasicInformationModel basicModel) {
        this.basicInformationModel = basicModel;
        showTheWebsite(basicInformationModel.getRedirectGatewayURL().toString()+mandatoryFieldModel.getBankName().toString());
    }

    @Override
    public void getErrorInformation(int responseCode, String message) {
        error(responseCode);
    }

    @Override
    public void successTransactionInfo(TransactionInfo transactionInfo) {
        if(mandatoryFieldModel.getSdkCategory().equals(SdkCategory.BANK_PAGE)){
            PayUsingSSLCommerz.paymentResultListener.transactionSuccess(transactionInfo);
            finish();
        }else {
            Intent intent = getIntent();
            intent.putExtra("result", transactionInfo);
            setResult(100, intent);
            finish();
        }
    }

    @Override
    public void failTransactionInfo(int responseCode, String message) {
        if(mandatoryFieldModel.getSdkCategory().equals(SdkCategory.BANK_PAGE)){
            PayUsingSSLCommerz.paymentResultListener.transactionFail(basicInformationModel.getSessionkey());
            finish();
        }else {
            Intent intent = getIntent();
            setResult(150, intent);
            finish();
        }
    }
}
