package com.sslcommerz.library.payment.viewmodel.management;

import android.content.Context;
import android.content.Intent;

import com.sslcommerz.library.payment.model.datafield.AdditionalFieldModel;
import com.sslcommerz.library.payment.model.datafield.CustomerFieldModel;
import com.sslcommerz.library.payment.model.datafield.MandatoryFieldModel;
import com.sslcommerz.library.payment.model.datafield.ShippingFieldModel;
import com.sslcommerz.library.payment.model.datamodel.AllInformation;
import com.sslcommerz.library.payment.model.util.SdkCategory;
import com.sslcommerz.library.payment.view.activity.SSLCommerzHomePageActivity;
import com.sslcommerz.library.payment.view.activity.BankPageActivity;
import com.sslcommerz.library.payment.viewmodel.listener.*;

import java.util.HashMap;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public class PayUsingSSLCommerz {

    private static String TAG = "PayUsingSSLCommerz";

    /*For Singleton*/
    private static PayUsingSSLCommerz payUsingSSLCommerz = new PayUsingSSLCommerz();
    public static PayUsingSSLCommerz getInstance(){
        return payUsingSSLCommerz;
    }
    public PayUsingSSLCommerz(){}

    /*CallBack Listener*/
    public static OnPaymentResultListener paymentResultListener;

    public void setData(Context context, MandatoryFieldModel mandatoryFieldModel, OnPaymentResultListener paymentResultListener){
        setData(context,mandatoryFieldModel,null,null,null,paymentResultListener);
    }

    public void setData(Context context, MandatoryFieldModel mandatoryFieldModel, CustomerFieldModel customerFieldModel, OnPaymentResultListener paymentResultListener){
        setData(context,mandatoryFieldModel,customerFieldModel,null,null,paymentResultListener);
    }

    public void setData(Context context, MandatoryFieldModel mandatoryFieldModel, ShippingFieldModel shippingFieldModel, OnPaymentResultListener paymentResultListener){
        setData(context,mandatoryFieldModel,null,shippingFieldModel,null,paymentResultListener);
    }

    public void setData(Context context, MandatoryFieldModel mandatoryFieldModel, CustomerFieldModel customerFieldModel, ShippingFieldModel shippingFieldModel, OnPaymentResultListener paymentResultListener){
        setData(context,mandatoryFieldModel,customerFieldModel,shippingFieldModel,null,paymentResultListener);
    }

    public void setData(Context context, MandatoryFieldModel mandatoryFieldModel, CustomerFieldModel customerFieldModel, ShippingFieldModel shippingFieldModel, AdditionalFieldModel additionalFieldModel, OnPaymentResultListener paymentResultListener){
        /*set listener*/
        PayUsingSSLCommerz.paymentResultListener = paymentResultListener;

        /*Make Map for request*/
        HashMap<String, String> map = AllInformation.getMapToSend(context, mandatoryFieldModel, customerFieldModel, shippingFieldModel, additionalFieldModel);
        if (mandatoryFieldModel.getSdkCategory().equals(SdkCategory.BANK_PAGE)) {
            Intent intent = new Intent(context, BankPageActivity.class);
            intent.putExtra("map_info", map);
            intent.putExtra("mandatory_field", mandatoryFieldModel);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, SSLCommerzHomePageActivity.class);
            intent.putExtra("map_info", map);
            intent.putExtra("mandatory_field", mandatoryFieldModel);
            context.startActivity(intent);
        }
    }

}
