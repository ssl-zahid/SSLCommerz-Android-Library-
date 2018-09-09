package com.sslcommerz.library.payment.model.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Zahidul Islam_2 on 2/13/2016.
 */
public class BasicInformationModel implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("failedreason")
    @Expose
    private String failedreason;
    @SerializedName("sessionkey")
    @Expose
    private String sessionkey;
    @SerializedName("gw")
    @Expose
    private BankTypes gw;
    @SerializedName("redirectGatewayURL")
    @Expose
    private String redirectGatewayURL;
    @SerializedName("redirectGatewayURLFailed")
    @Expose
    private String redirectGatewayURLFailed;
    @SerializedName("GatewayPageURL")
    @Expose
    private String gatewayPageURL;
    @SerializedName("storeBanner")
    @Expose
    private String storeBanner;
    @SerializedName("storeLogo")
    @Expose
    private String storeLogo;
    @SerializedName("desc")
    @Expose
    private ArrayList<AllBankList> desc;
    @SerializedName("tran_id")
    @Expose
    private String tranId;
    @SerializedName("is_direct_pay_enable")
    @Expose
    private String isDirectPayEnable;
    @SerializedName("existingCards")
    @Expose
    private String existingCards;
    @SerializedName("deleteCardURL")
    @Expose
    private String deleteCardURL;
    @SerializedName("cardAuthorizeURL")
    @Expose
    private String cardAuthorizeURL;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailedreason() {
        return failedreason;
    }

    public void setFailedreason(String failedreason) {
        this.failedreason = failedreason;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getRedirectGatewayURL() {
        return redirectGatewayURL;
    }

    public void setRedirectGatewayURL(String redirectGatewayURL) {
        this.redirectGatewayURL = redirectGatewayURL;
    }

    public String getStoreBanner() {
        return storeBanner;
    }

    public void setStoreBanner(String storeBanner) {
        this.storeBanner = storeBanner;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public BankTypes getGw() {
        return gw;
    }

    public void setGw(BankTypes gw) {
        this.gw = gw;
    }

    public String getRedirectGatewayURLFailed() {
        return redirectGatewayURLFailed;
    }

    public void setRedirectGatewayURLFailed(String redirectGatewayURLFailed) {
        this.redirectGatewayURLFailed = redirectGatewayURLFailed;
    }

    public String getGatewayPageURL() {
        return gatewayPageURL;
    }

    public void setGatewayPageURL(String gatewayPageURL) {
        this.gatewayPageURL = gatewayPageURL;
    }

    public ArrayList<AllBankList> getDesc() {
        return desc;
    }

    public void setDesc(ArrayList<AllBankList> desc) {
        this.desc = desc;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getIsDirectPayEnable() {
        return isDirectPayEnable;
    }

    public void setIsDirectPayEnable(String isDirectPayEnable) {
        this.isDirectPayEnable = isDirectPayEnable;
    }

    public String getExistingCards() {
        return existingCards;
    }

    public void setExistingCards(String existingCards) {
        this.existingCards = existingCards;
    }

    public String getDeleteCardURL() {
        return deleteCardURL;
    }

    public void setDeleteCardURL(String deleteCardURL) {
        this.deleteCardURL = deleteCardURL;
    }

    public String getCardAuthorizeURL() {
        return cardAuthorizeURL;
    }

    public void setCardAuthorizeURL(String cardAuthorizeURL) {
        this.cardAuthorizeURL = cardAuthorizeURL;
    }

    public class BankTypes implements Serializable {

        @SerializedName("visa")
        @Expose
        private String visa;
        @SerializedName("master")
        @Expose
        private String master;
        @SerializedName("amex")
        @Expose
        private String amex;
        @SerializedName("othercards")
        @Expose
        private String othercards;
        @SerializedName("internetbanking")
        @Expose
        private String internetbanking;
        @SerializedName("mobilebanking")
        @Expose
        private String mobilebanking;

        public String getVisa() {
            return visa;
        }

        public void setVisa(String visa) {
            this.visa = visa;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public String getAmex() {
            return amex;
        }

        public void setAmex(String amex) {
            this.amex = amex;
        }

        public String getOthercards() {
            return othercards;
        }

        public void setOthercards(String othercards) {
            this.othercards = othercards;
        }

        public String getInternetbanking() {
            return internetbanking;
        }

        public void setInternetbanking(String internetbanking) {
            this.internetbanking = internetbanking;
        }

        public String getMobilebanking() {
            return mobilebanking;
        }

        public void setMobilebanking(String mobilebanking) {
            this.mobilebanking = mobilebanking;
        }
    }

    public class AllBankList implements Serializable{
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("gw")
        @Expose
        private String gw;
        @SerializedName("redirectGatewayURL")
        @Expose
        private String redirectGatewayURL;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getGw() {
            return gw;
        }

        public void setGw(String gw) {
            this.gw = gw;
        }

        public String getRedirectGatewayURL() {
            return redirectGatewayURL;
        }

        public void setRedirectGatewayURL(String redirectGatewayURL) {
            this.redirectGatewayURL = redirectGatewayURL;
        }
    }

}
