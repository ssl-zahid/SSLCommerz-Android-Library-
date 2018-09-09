package com.sslcommerz.library.payment.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sslcommerz.library.payment.model.dataset.CardListModel;
import com.sslcommerz.library.payment.model.dataset.TransactionInfo;
import com.sslcommerz.library.payment.model.util.SdkType;
import com.sslcommerz.library.payment.view.adapter.recycler.BankListAdapter;
import com.sslcommerz.library.payment.view.adapter.recycler.SSLCommerzBankAdapter;
import com.sslcommerz.library.payment.view.adapter.viewpager.SSLCommerzCardSliderAdapter;
import com.sslcommerz.library.payment.viewmodel.encryption.EncryptionHandler;
import com.sslcommerz.library.payment.viewmodel.listener.BankInformationListener;
import com.sslcommerz.library.payment.viewmodel.listener.BasicAPIResponseListener;
import com.sslcommerz.library.payment.viewmodel.management.PayUsingSSLCommerz;
import com.sslcommerz.library.payment.model.datafield.MandatoryFieldModel;
import com.sslcommerz.library.payment.model.dataset.BasicInformationModel;
import com.sslcommerz.library.payment.model.util.ErrorKeys;
import com.sslcommerz.library.payment.viewmodel.management.SSLCommerzManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public class SSLCommerzHomePageActivity extends BaseActivity implements BankInformationListener, View.OnClickListener, BasicAPIResponseListener {

    private HashMap<String,String> requestMap;
    private MandatoryFieldModel mandatoryFieldModel;
    private BankListAdapter bankListAdapter;
    private SSLCommerzBankAdapter sslCommerzBankAdapter;
    private BasicInformationModel model;
    private SSLCommerzManagement sslCommerzManagement;
    private BasicInformationModel basicInformationModel = null;
    private ArrayList<BasicInformationModel.AllBankList> bankList = new ArrayList<>();
    private LinearLayout cardTab,mobileTab,netBankingTab,allTab;
    private RecyclerView bankListShow;
    private ImageView cardIcon,mobileIcon,netBankingIcon,allBankIcon;
    private TextView cardText,mobileText,netBankingText,allBankText,payNow,cancelBtn;
    private View cardDivider,mobileDivider,netBankingDivider,allBankDivider;
    private ViewPager slider;
    private ArrayList<CardListModel> cardListModel;
    private SSLCommerzCardSliderAdapter cardSliderAdapter;
    private Dialog removeDialog;
    private View deleteConfirmation;
    private TextView deleteCardCancelBtn,deleteCardBtn;
    private Dialog confirmDialog;
    private View confirmPayment;
    private EditText cvvNumber;
    private TextView confirmPaymentCancelBtn,confirmPaymentPayBtn;
    private LinearLayout removeCardButtonLayout,removeCardLoadingLayout,confirmCardButtonLayout, confirmCardLoadingLayout;
    private LinearLayout loadingLayout,mainLayout,cardLayout;
    private int BANK_PAGE_ACTIVITY = 305;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sslcommerz_homepage_layout);
    }

    @Override
    protected void initialize() {
        cardTab = findViewById(R.id.cardTab);
        mobileTab = findViewById(R.id.mobileTab);
        netBankingTab = findViewById(R.id.netBankingTab);
        allTab = findViewById(R.id.allTab);
        bankListShow = findViewById(R.id.bankListShow);
        cardIcon = findViewById(R.id.cardIcon);
        mobileIcon = findViewById(R.id.mobileIcon);
        netBankingIcon = findViewById(R.id.netBankingIcon);
        allBankIcon = findViewById(R.id.allBankIcon);
        cardText = findViewById(R.id.cardText);
        mobileText = findViewById(R.id.mobileText);
        netBankingText = findViewById(R.id.netBankingText);
        allBankText = findViewById(R.id.allBankText);
        cardDivider = findViewById(R.id.cardDivider);
        mobileDivider = findViewById(R.id.mobileDivider);
        netBankingDivider = findViewById(R.id.netBankingDivider);
        allBankDivider = findViewById(R.id.allBankDivider);
        slider = findViewById(R.id.slider);
        payNow = findViewById(R.id.payNow);
        cancelBtn = findViewById(R.id.cancelBtn);

        bankListShow.setLayoutManager(new GridLayoutManager(this,4));

        removeDialog = new Dialog(this);
        removeDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        removeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteConfirmation = LayoutInflater.from(SSLCommerzHomePageActivity.this).inflate(R.layout.dialog_card_delete_confirmation_layout, null, false);
        removeDialog.setContentView(deleteConfirmation);

        deleteCardCancelBtn = deleteConfirmation.findViewById(R.id.cancelBtn);
        deleteCardBtn = deleteConfirmation.findViewById(R.id.deleteCardBtn);

        confirmDialog = new Dialog(this);
        confirmDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmPayment = LayoutInflater.from(this).inflate(R.layout.card_payment_confirmation_layout, null, false);
        confirmDialog.setContentView(confirmPayment);

        removeCardButtonLayout = deleteConfirmation.findViewById(R.id.buttonLayout);
        removeCardLoadingLayout = deleteConfirmation.findViewById(R.id.loadingLayout);

        confirmCardButtonLayout = confirmPayment.findViewById(R.id.buttonLayout);
        confirmCardLoadingLayout = confirmPayment.findViewById(R.id.loadingLayout);

        cvvNumber = confirmPayment.findViewById(R.id.cvvNumber);
        confirmPaymentCancelBtn = confirmPayment.findViewById(R.id.cancelBtn);
        confirmPaymentPayBtn = confirmPayment.findViewById(R.id.payNow);

        loadingLayout = findViewById(R.id.loadingLayout);
        mainLayout = findViewById(R.id.mainLayout);
        cardLayout = findViewById(R.id.cardLayout);
    }

    @Override
    protected  void viewRelatedTask() {
        setToolbar(getString(R.string.select_payment_option),true);
        try{
            mandatoryFieldModel = (MandatoryFieldModel) getIntent().getExtras().getSerializable("mandatory_field");
            requestMap = (HashMap<String, String>) getIntent().getExtras().getSerializable("map_info");
        }catch (Exception e){PayUsingSSLCommerz.paymentResultListener.error(ErrorKeys.DATA_PARSING_ERROR);onBackPressed();}

        sslCommerzManagement = new SSLCommerzManagement(this);
        sslCommerzManagement.getPrimaryTransactionInfo(requestMap,mandatoryFieldModel.getSdkType().equals(SdkType.LIVE),this);

        cardTab.setOnClickListener(this);
        mobileTab.setOnClickListener(this);
        netBankingTab.setOnClickListener(this);
        allTab.setOnClickListener(this);

        payNow.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void startLoading(String requestId) {
        if(requestId.equals(sslCommerzManagement.BANK_LIST_REQUEST_ID)){
            loadingLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
        } else if(requestId.equals(sslCommerzManagement.DELETE_CARD_REQUEST_ID)){
            removeCardButtonLayout.setVisibility(View.GONE);
            removeCardLoadingLayout.setVisibility(View.VISIBLE);
        } else if(requestId.equals(sslCommerzManagement.CONFIRM_PAYMENT_REQUEST_ID)){
            confirmCardButtonLayout.setVisibility(View.GONE);
            confirmCardLoadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void endLoading(String requestId) {
        if(requestId.equals(sslCommerzManagement.DELETE_CARD_REQUEST_ID)){
            removeCardButtonLayout.setVisibility(View.VISIBLE);
            removeCardLoadingLayout.setVisibility(View.GONE);
        } else if(requestId.equals(sslCommerzManagement.CONFIRM_PAYMENT_REQUEST_ID)){
            confirmCardButtonLayout.setVisibility(View.VISIBLE);
            confirmCardLoadingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSuccessInformation(BasicInformationModel basicInformationModel) {
        this.basicInformationModel = basicInformationModel;
        cardTab.performClick();

        if(!TextUtils.isEmpty(mandatoryFieldModel.getSecretKey()) && !TextUtils.isEmpty(basicInformationModel.getExistingCards())){
            cardLayout.setVisibility(View.VISIBLE);
            String cardListString = EncryptionHandler.getInstance().decryptData(mandatoryFieldModel.getSecretKey(), basicInformationModel.getExistingCards());
            cardListModel = new Gson().fromJson(cardListString, new TypeToken<List<CardListModel>>(){}.getType());
            if(cardListModel.size()==0) cardLayout.setVisibility(View.GONE);
            else initSliderAdapter();
        } else {
            cardLayout.setVisibility(View.GONE);
        }

        loadingLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getErrorInformation(int responseCode, String message) {
        error(responseCode);
    }

    @Override
    public void apiCallingSuccess(String requestId, JSONObject jsonObject) {
        if(requestId.equals(sslCommerzManagement.DELETE_CARD_REQUEST_ID)){
            sslCommerzManagement.getPrimaryTransactionInfo(requestMap,mandatoryFieldModel.getSdkType().equals(SdkType.LIVE),this);
            removeDialog.dismiss();
        } else if(requestId.equals(sslCommerzManagement.CONFIRM_PAYMENT_REQUEST_ID)){
            try {
                String redirectUrl = jsonObject.getString("redirectURL");
                Intent intent = new Intent(SSLCommerzHomePageActivity.this, BankPageActivity.class);
                intent.putExtra("bank_model",basicInformationModel);
                intent.putExtra("mandatory_field",mandatoryFieldModel);
                intent.putExtra("gateway_name", getString(R.string.card));
                intent.putExtra("gateway_url", redirectUrl);
                startActivityForResult(intent, BANK_PAGE_ACTIVITY);
            } catch (JSONException e) {}
            confirmDialog.dismiss();
        }
    }

    @Override
    public void apiCallingFail(String requestId, String message) {
        showToast(message);
    }

    @Override
    public void onClick(View view) {
        if(view == payNow){
            if(TextUtils.isEmpty(sslCommerzBankAdapter.bankName) || TextUtils.isEmpty(sslCommerzBankAdapter.bankUrl)){
                showToast(getString(R.string.please_select_payment_mode));
            } else {
                Intent intent = new Intent(SSLCommerzHomePageActivity.this, BankPageActivity.class);
                intent.putExtra("bank_model",basicInformationModel);
                intent.putExtra("mandatory_field", mandatoryFieldModel);
                intent.putExtra("gateway_name", sslCommerzBankAdapter.bankName);
                intent.putExtra("gateway_url", sslCommerzBankAdapter.bankUrl);
                startActivityForResult(intent, BANK_PAGE_ACTIVITY);
            }
        } else if(view == cancelBtn){
            onBackPressed();
        } else if(view == cardTab){
            bankTypeButtonStyling(0);
            sslCommerzBankAdapter = new SSLCommerzBankAdapter(SSLCommerzHomePageActivity.this,getCustomBankList(0),displayMetrics.widthPixels,displayMetrics.heightPixels);
            bankListShow.setAdapter(sslCommerzBankAdapter);
        } else if(view == mobileTab){
            bankTypeButtonStyling(1);
            sslCommerzBankAdapter = new SSLCommerzBankAdapter(SSLCommerzHomePageActivity.this,getCustomBankList(1),displayMetrics.widthPixels,displayMetrics.heightPixels);
            bankListShow.setAdapter(sslCommerzBankAdapter);
        } else if(view == netBankingTab){
            bankTypeButtonStyling(2);
            sslCommerzBankAdapter = new SSLCommerzBankAdapter(SSLCommerzHomePageActivity.this,getCustomBankList(2),displayMetrics.widthPixels,displayMetrics.heightPixels);
            bankListShow.setAdapter(sslCommerzBankAdapter);
        } else if(view == allTab){
            bankTypeButtonStyling(3);
            sslCommerzBankAdapter = new SSLCommerzBankAdapter(SSLCommerzHomePageActivity.this, getCustomBankList(3),displayMetrics.widthPixels,displayMetrics.heightPixels);
            bankListShow.setAdapter(sslCommerzBankAdapter);
        }
    }

    private void initSliderAdapter() {
        cardSliderAdapter = new SSLCommerzCardSliderAdapter(getSupportFragmentManager(), cardListModel) {
            @Override
            public void getDeletePosition(final int position) {
                deleteCardCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeDialog.dismiss();
                    }
                });
                deleteCardBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tokenValue = new String();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("token_value", cardListModel.get(position).getTokenValue());
                            jsonObject.put("token_ref", mandatoryFieldModel.getTokenKey());
                            tokenValue = EncryptionHandler.getInstance().encryptData(mandatoryFieldModel.getSecretKey(), jsonObject.toString());
                        }catch (Exception e){}
                        sslCommerzManagement.deleteCard(mandatoryFieldModel.getSdkType().equals(SdkType.LIVE), basicInformationModel.getSessionkey(),tokenValue, SSLCommerzHomePageActivity.this);
                    }
                });
                removeDialog.show();
            }

            @Override
            public void getItemPosition(final int position) {
                confirmPaymentCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();
                    }
                });
                confirmPaymentPayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {String tokenValue = new String();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("token_value", cardListModel.get(position).getTokenValue());
                            jsonObject.put("token_ref", mandatoryFieldModel.getTokenKey());
                            jsonObject.put("token_cvv", cvvNumber.getText().toString());
                            tokenValue = EncryptionHandler.getInstance().encryptData(mandatoryFieldModel.getSecretKey(), jsonObject.toString());
                        }catch (Exception e){}
                        sslCommerzManagement.confirmPayUsingCard(mandatoryFieldModel.getSdkType().equals(SdkType.LIVE), basicInformationModel.getSessionkey(),tokenValue,SSLCommerzHomePageActivity.this);
                    }
                });
                confirmDialog.show();
            }
        };
        slider.setAdapter(cardSliderAdapter);
        slider.setOffscreenPageLimit(0);
        slider.setPageMargin(-20);
        slider.setClipToPadding(false);
        slider.setPadding(30, 0, 30, 0);
    }

    private void bankTypeButtonStyling(int position){
        cardIcon.setColorFilter(position == 0 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        cardText.setTextColor(position == 0 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        cardDivider.setBackgroundColor(position == 0 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        cardDivider.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

        mobileIcon.setColorFilter(position == 1 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        mobileText.setTextColor(position == 1 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        mobileDivider.setBackgroundColor(position == 1 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        mobileDivider.setVisibility(position == 1 ? View.VISIBLE : View.GONE);

        netBankingIcon.setColorFilter(position == 2 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        netBankingText.setTextColor(position == 2 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        netBankingDivider.setBackgroundColor(position == 2 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        netBankingDivider.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

        allBankIcon.setColorFilter(position == 3 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        allBankText.setTextColor(position == 3 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        allBankDivider.setBackgroundColor(position == 3 ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorBlack));
        allBankDivider.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
    }

    private ArrayList<BasicInformationModel.AllBankList> getCustomBankList(int listType){
        if(basicInformationModel != null) {
            if (listType == 0) {
                bankList.removeAll(bankList);
                String []visaGw = basicInformationModel.getGw().getVisa().split(",");
                String []masterGw = basicInformationModel.getGw().getMaster().split(",");
                String []amexGw = basicInformationModel.getGw().getAmex().split(",");
                String []otherCardGw = basicInformationModel.getGw().getOthercards().split(",");
                if(visaGw.length > 0){
                    if(getBankModel(visaGw[visaGw.length-1]) != null){
                        bankList.add(getBankModel(visaGw[visaGw.length-1]));
                    }
                }if(masterGw.length > 0){
                    if(getBankModel(masterGw[masterGw.length-1]) != null){
                        bankList.add(getBankModel(masterGw[masterGw.length-1]));
                    }
                }if(amexGw.length > 0){
                    if(getBankModel(amexGw[amexGw.length-1]) != null){
                        bankList.add(getBankModel(amexGw[amexGw.length-1]));
                    }
                }if(otherCardGw.length > 0){
                    for (String bankName : otherCardGw) {
                        if (getBankModel(bankName) != null){
                            bankList.add(getBankModel(bankName));
                        }
                    }
                }
                return bankList;
            } else if(listType == 1){
                bankList.removeAll(bankList);
                String []mobileBankingGw = basicInformationModel.getGw().getMobilebanking().split(",");
                if(mobileBankingGw.length > 0){
                    for (String bankName : mobileBankingGw) {
                        if (getBankModel(bankName) != null){
                            bankList.add(getBankModel(bankName));
                        }
                    }
                }
                return bankList;
            }  else if(listType == 2){
                bankList.removeAll(bankList);
                String []internetBankingGw = basicInformationModel.getGw().getInternetbanking().split(",");
                if(internetBankingGw.length > 0){
                    for (String bankName : internetBankingGw) {
                        if (getBankModel(bankName) != null){
                            bankList.add(getBankModel(bankName));
                        }
                    }
                }
                return bankList;
            } else if(listType == 3){
                bankList.removeAll(bankList);

                String []visaGw = basicInformationModel.getGw().getVisa().split(",");
                String []masterGw = basicInformationModel.getGw().getMaster().split(",");
                String []amexGw = basicInformationModel.getGw().getAmex().split(",");
                String []otherCardGw = basicInformationModel.getGw().getOthercards().split(",");
                if(visaGw.length > 0){
                    if(getBankModel(visaGw[visaGw.length-1]) != null){
                        bankList.add(getBankModel(visaGw[visaGw.length-1]));
                    }
                } if(masterGw.length > 0){
                    if(getBankModel(masterGw[masterGw.length-1]) != null){
                        bankList.add(getBankModel(masterGw[masterGw.length-1]));
                    }
                } if(amexGw.length > 0){
                    if(getBankModel(amexGw[amexGw.length-1]) != null){
                        bankList.add(getBankModel(amexGw[amexGw.length-1]));
                    }
                } if(otherCardGw.length > 0){
                    for (String bankName : otherCardGw) {
                        if (getBankModel(bankName) != null){
                            bankList.add(getBankModel(bankName));
                        }
                    }
                }

                String []mobileBankingGw = basicInformationModel.getGw().getMobilebanking().split(",");
                if(mobileBankingGw.length > 0){
                    for (String bankName : mobileBankingGw) {
                        if (getBankModel(bankName) != null){
                            bankList.add(getBankModel(bankName));
                        }
                    }
                }

                String []internetBankingGw = basicInformationModel.getGw().getInternetbanking().split(",");
                if(internetBankingGw.length > 0){
                    for (String bankName : internetBankingGw) {
                        if (getBankModel(bankName) != null){
                            bankList.add(getBankModel(bankName));
                        }
                    }
                }
                return bankList;
            }
        }
        return new ArrayList<>();
    }

    private BasicInformationModel.AllBankList getBankModel(String bankName){
        BasicInformationModel.AllBankList bank = null;
        for (BasicInformationModel.AllBankList bankModel : basicInformationModel.getDesc()) {
            if(bankModel.getGw().equals(bankName)) return bankModel;
        }
        return bank;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BANK_PAGE_ACTIVITY){
            if(resultCode == 100){
                PayUsingSSLCommerz.paymentResultListener.transactionSuccess((TransactionInfo) data.getSerializableExtra("result"));
                finish();
            } else if(resultCode == 150){
                PayUsingSSLCommerz.paymentResultListener.transactionFail(basicInformationModel.getSessionkey());
                finish();
            }
        }
    }
}
