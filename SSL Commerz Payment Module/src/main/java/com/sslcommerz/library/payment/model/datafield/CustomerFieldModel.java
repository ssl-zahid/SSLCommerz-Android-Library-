package com.sslcommerz.library.payment.model.datafield;

import java.io.Serializable;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public class CustomerFieldModel implements Serializable {

    private String customerName = new String();
    private String customerEmail = new String();
    private String customerAddress1 = new String();
    private String customerAddress2 = new String();
    private String customerCity = new String();
    private String customerState = new String();
    private String customerPostCode = new String();
    private String customerCountry = new String();
    private String customerPhone = new String();
    private String customerFax = new String();

    public CustomerFieldModel(String customerName, String customerEmail, String customerAddress1, String customerAddress2, String customerCity, String customerState, String customerPostCode, String customerCountry, String customerPhone, String customerFax) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress1 = customerAddress1;
        this.customerAddress2 = customerAddress2;
        this.customerCity = customerCity;
        this.customerState = customerState;
        this.customerPostCode = customerPostCode;
        this.customerCountry = customerCountry;
        this.customerPhone = customerPhone;
        this.customerFax = customerFax;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return customerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerPostCode() {
        return customerPostCode;
    }

    public void setCustomerPostCode(String customerPostCode) {
        this.customerPostCode = customerPostCode;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerFax() {
        return customerFax;
    }

    public void setCustomerFax(String customerFax) {
        this.customerFax = customerFax;
    }
}
