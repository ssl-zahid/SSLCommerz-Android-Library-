package com.sslcommerz.library.payment.model.datafield;

import java.io.Serializable;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public class MandatoryFieldModel implements Serializable {

    private String storeId;
    private String storePassword;
    private String totalAmount;
    private String tranId;
    private String currency;
    private String sdkType;
    private String sdkCategory;
    private String multiCardName = new String();
    private String bankName = new String();
    private String tokenKey;
    private String secretKey;

    public MandatoryFieldModel(String storeId, String storePassword, String totalAmount, String tranId, String currency, String sdkType, String sdkCategory, String bankName) {
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.totalAmount = totalAmount;
        this.tranId = tranId;
        this.currency = currency;
        this.sdkType = sdkType;
        this.sdkCategory = sdkCategory;
        this.bankName = bankName;
    }

    public MandatoryFieldModel(String storeId, String storePassword, String totalAmount, String tranId, String currency, String sdkType, String sdkCategory) {
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.totalAmount = totalAmount;
        this.tranId = tranId;
        this.currency = currency;
        this.sdkType = sdkType;
        this.sdkCategory = sdkCategory;
        this.bankName = "";
    }

    public void setTokenizeData(String secretKey, String tokenKey){
        this.secretKey = secretKey;
        this.tokenKey = tokenKey;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSdkType() {
        return sdkType;
    }

    public void setSdkType(String setSdkType) {
        this.sdkType = setSdkType;
    }

    public String getSdkCategory() {
        return sdkCategory;
    }

    public void setSdkCategory(String sdkCategory) {
        this.sdkCategory = sdkCategory;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMultiCardName() {
        return multiCardName;
    }

    public void setMultiCardName(String multiCardName) {
        this.multiCardName = multiCardName;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
