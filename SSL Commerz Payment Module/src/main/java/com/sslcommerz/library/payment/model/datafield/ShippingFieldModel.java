package com.sslcommerz.library.payment.model.datafield;

import java.io.Serializable;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public class ShippingFieldModel implements Serializable {

    private String shipName = new String();
    private String shipAddress1 = new String();
    private String shipAddress2 = new String();
    private String shipCity = new String();
    private String shipState = new String();
    private String shipPostCode = new String();
    private String shipCountry = new String();

    public ShippingFieldModel(String shipName, String shipAddress1, String shipAddress2, String shipCity, String shipState, String shipPostCode, String shipCountry) {
        this.shipName = shipName;
        this.shipAddress1 = shipAddress1;
        this.shipAddress2 = shipAddress2;
        this.shipCity = shipCity;
        this.shipState = shipState;
        this.shipPostCode = shipPostCode;
        this.shipCountry = shipCountry;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress1() {
        return shipAddress1;
    }

    public void setShipAddress1(String shipAddress1) {
        this.shipAddress1 = shipAddress1;
    }

    public String getShipAddress2() {
        return shipAddress2;
    }

    public void setShipAddress2(String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipPostCode() {
        return shipPostCode;
    }

    public void setShipPostCode(String shipPostCode) {
        this.shipPostCode = shipPostCode;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }
}
