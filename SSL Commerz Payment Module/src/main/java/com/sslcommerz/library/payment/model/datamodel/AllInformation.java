package com.sslcommerz.library.payment.model.datamodel;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sslcommerz.library.payment.viewmodel.encryption.EncryptionHandler;
import com.sslcommerz.library.payment.viewmodel.management.PayUsingSSLCommerz;
import com.sslcommerz.library.payment.model.datafield.AdditionalFieldModel;
import com.sslcommerz.library.payment.model.datafield.CustomerFieldModel;
import com.sslcommerz.library.payment.model.datafield.MandatoryFieldModel;
import com.sslcommerz.library.payment.model.datafield.ShippingFieldModel;
import com.sslcommerz.library.payment.model.util.ErrorKeys;
import com.sslcommerz.library.payment.model.util.InputKeys;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public class AllInformation {

    public static final String BANK_LIST_URL = "https://securepay.sslcommerz.com/gwprocess/v3/";
    public static final String BANK_LIST_URL_SANDBOX = "https://sandbox.sslcommerz.com/gwprocess/v3/";
    public static final String TRANSACTION_ALL_INFO = "https://securepay.sslcommerz.com/validator/api/";
    public static final String TRANSACTION_ALL_INFO_SANDBOX = "https://sandbox.sslcommerz.com/validator/api/";
    public static final String TRANSACTION_END_POINT = "https://securepay.sslcommerz.com/gw/apps/result.php";

    public static HashMap getMapToSend(Context context, MandatoryFieldModel mandatoryFieldModel, CustomerFieldModel customerFieldModel, ShippingFieldModel shippingFieldModel, AdditionalFieldModel additionalFieldModel){
        HashMap<String,String> getMap = new HashMap<>();
        try {
            getMap.put(InputKeys.STORE_ID, mandatoryFieldModel.getStoreId());
            getMap.put(InputKeys.STORE_PASSWORD, mandatoryFieldModel.getStorePassword());
            getMap.put(InputKeys.TOTAL_AMOUNT, mandatoryFieldModel.getTotalAmount());
            getMap.put(InputKeys.CURRENCY, mandatoryFieldModel.getCurrency());
            getMap.put(InputKeys.TRANSACTION_ID, mandatoryFieldModel.getTranId());
            getMap.put(InputKeys.MULTI_CARD_NAME, mandatoryFieldModel.getMultiCardName());
            if(!TextUtils.isEmpty(mandatoryFieldModel.getTokenKey()) && !TextUtils.isEmpty(mandatoryFieldModel.getSecretKey())){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token_ref", mandatoryFieldModel.getTokenKey());
                getMap.put(InputKeys.TOKEN_KEY, EncryptionHandler.getInstance().encryptData(mandatoryFieldModel.getSecretKey(), jsonObject.toString()));
            }
            getMap.put("success_url", TRANSACTION_END_POINT);
            getMap.put("fail_url", TRANSACTION_END_POINT);
            getMap.put("cancel_url", TRANSACTION_END_POINT);
            getMap.put("app_id", context.getPackageName().toString());
            getMap.put("source_details", sourceDetails(context));

            if (customerFieldModel != null){
                getMap.put(InputKeys.CUSTOMER_NAME, customerFieldModel.getCustomerName());
                getMap.put(InputKeys.CUSTOMER_EMAIL, customerFieldModel.getCustomerEmail());
                getMap.put(InputKeys.CUSTOMER_ADDRESS_1, customerFieldModel.getCustomerAddress1());
                getMap.put(InputKeys.CUSTOMER_ADDRESS_2, customerFieldModel.getCustomerAddress2());
                getMap.put(InputKeys.CUSTOMER_CITY, customerFieldModel.getCustomerCity());
                getMap.put(InputKeys.CUSTOMER_STATE, customerFieldModel.getCustomerState());
                getMap.put(InputKeys.CUSTOMER_POSTCODE, customerFieldModel.getCustomerPostCode());
                getMap.put(InputKeys.CUSTOMER_COUNTRY, customerFieldModel.getCustomerCountry());
                getMap.put(InputKeys.CUSTOMER_PHONE, customerFieldModel.getCustomerPhone());
                getMap.put(InputKeys.CUSTOMER_FAX, customerFieldModel.getCustomerFax());
            }
            if (shippingFieldModel != null){
                getMap.put(InputKeys.SHIP_NAME, shippingFieldModel.getShipName());
                getMap.put(InputKeys.SHIP_ADDRESS_1, shippingFieldModel.getShipAddress1());
                getMap.put(InputKeys.SHIP_ADDRESS_2, shippingFieldModel.getShipAddress2());
                getMap.put(InputKeys.SHIP_CITY, shippingFieldModel.getShipCity());
                getMap.put(InputKeys.SHIP_STATE, shippingFieldModel.getShipState());
                getMap.put(InputKeys.SHIP_POSTCODE, shippingFieldModel.getShipPostCode());
                getMap.put(InputKeys.SHIP_COUNTRY, shippingFieldModel.getShipCountry());
            }
            if (additionalFieldModel != null){
                getMap.put(InputKeys.VALUE_A, additionalFieldModel.getValueA());
                getMap.put(InputKeys.VALUE_B, additionalFieldModel.getValueB());
                getMap.put(InputKeys.VALUE_C, additionalFieldModel.getValueC());
                getMap.put(InputKeys.VALUE_D, additionalFieldModel.getValueD());
            }
        }
        catch (Exception e){
            return null;
        }
        return getMap;
    }

    private static String sourceDetails(Context context){
        ArrayList<String> additionalInformation = new ArrayList<>();
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            additionalInformation.add("device_type : "+getDeviceName());
            additionalInformation.add("app_name : "+ context.getApplicationInfo().loadLabel(context.getPackageManager()).toString());
            additionalInformation.add("app_version : "+ pInfo.versionName.toString());
            additionalInformation.add("app_code : "+ pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {}
        return new Gson().toJson(additionalInformation);
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            return "HTC " + model;
        }
        return capitalize(manufacturer) + " " + model;
    }
    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

}
