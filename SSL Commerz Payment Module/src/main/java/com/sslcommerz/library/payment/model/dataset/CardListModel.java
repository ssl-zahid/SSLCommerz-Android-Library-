package com.sslcommerz.library.payment.model.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CardListModel implements Serializable {

    @SerializedName("card_no")
    @Expose
    private String cardNo;
    @SerializedName("card_type")
    @Expose
    private String cardType;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("token_value")
    @Expose
    private String tokenValue;
    @SerializedName("issuer_bank")
    @Expose
    private String issuerBank;
    @SerializedName("design")
    @Expose
    private CardDesignComponent design;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getIssuerBank() {
        return issuerBank;
    }

    public void setIssuerBank(String issuerBank) {
        this.issuerBank = issuerBank;
    }

    public CardDesignComponent getDesign() {
        return design;
    }

    public void setDesign(CardDesignComponent design) {
        this.design = design;
    }


    public class CardDesignComponent implements Serializable {

        @SerializedName("bk_color")
        @Expose
        private String bkColor;
        @SerializedName("font_color")
        @Expose
        private String fontColor;
        @SerializedName("btn_yes_bk_color")
        @Expose
        private String btnYesBkColor;
        @SerializedName("btn_yes_font_color")
        @Expose
        private String btnYesFontColor;
        @SerializedName("btn_no_bk_color")
        @Expose
        private String btnNoBkColor;
        @SerializedName("btn_no_font_color")
        @Expose
        private String btnNoFontColor;

        public String getBkColor() {
            return bkColor;
        }

        public void setBkColor(String bkColor) {
            this.bkColor = bkColor;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }

        public String getBtnYesBkColor() {
            return btnYesBkColor;
        }

        public void setBtnYesBkColor(String btnYesBkColor) {
            this.btnYesBkColor = btnYesBkColor;
        }

        public String getBtnYesFontColor() {
            return btnYesFontColor;
        }

        public void setBtnYesFontColor(String btnYesFontColor) {
            this.btnYesFontColor = btnYesFontColor;
        }

        public String getBtnNoBkColor() {
            return btnNoBkColor;
        }

        public void setBtnNoBkColor(String btnNoBkColor) {
            this.btnNoBkColor = btnNoBkColor;
        }

        public String getBtnNoFontColor() {
            return btnNoFontColor;
        }

        public void setBtnNoFontColor(String btnNoFontColor) {
            this.btnNoFontColor = btnNoFontColor;
        }
    }

}
