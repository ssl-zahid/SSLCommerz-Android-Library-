package com.sslcommerz.library.payment.model.datafield;

import java.io.Serializable;

/**
 * Created by SSL_ZAHID on 10/26/2016.
 */

public class AdditionalFieldModel implements Serializable {

    private String valueA = new String();
    private String valueB = new String();
    private String valueC = new String();
    private String valueD = new String();

    public String getValueA() {
        return valueA;
    }

    public void setValueA(String valueA) {
        this.valueA = valueA;
    }

    public String getValueB() {
        return valueB;
    }

    public void setValueB(String valueB) {
        this.valueB = valueB;
    }

    public String getValueC() {
        return valueC;
    }

    public void setValueC(String valueC) {
        this.valueC = valueC;
    }

    public String getValueD() {
        return valueD;
    }

    public void setValueD(String valueD) {
        this.valueD = valueD;
    }
}
