package com.sslcommerz.payment.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sslcommerz.library.payment.model.datafield.MandatoryFieldModel;
import com.sslcommerz.library.payment.model.dataset.TransactionInfo;
import com.sslcommerz.library.payment.model.util.BankName;
import com.sslcommerz.library.payment.model.util.CurrencyType;
import com.sslcommerz.library.payment.model.util.ErrorKeys;
import com.sslcommerz.library.payment.model.util.SdkCategory;
import com.sslcommerz.library.payment.model.util.SdkType;
import com.sslcommerz.library.payment.viewmodel.management.PayUsingSSLCommerz;
import com.sslcommerz.library.payment.viewmodel.listener.OnPaymentResultListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button click;
    private TextView summery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        click = (Button) findViewById(R.id.click);
        summery = (TextView) findViewById(R.id.summery);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();
            }
        });
    }

    private void makePayment(){

        /* Mandatory Field*/
        MandatoryFieldModel mandatoryFieldModel = new MandatoryFieldModel("testbox","qwerty","10", "REF123", CurrencyType.BDT, SdkType.TESTBOX, SdkCategory.BANK_LIST);
        //MandatoryFieldModel mandatoryFieldModel = new MandatoryFieldModel("directpaylive","directpaylive38326","100.10", "REF123", CurrencyType.BDT, SdkType.LIVE, SdkCategory.BANK_LIST);
        mandatoryFieldModel.setTokenizeData("f5d66a527ff181486321bf7c3s7f54de","ca04bf91675522debb01117dde6aede0");
        //MandatoryFieldModel mandatoryFieldModel1 = new MandatoryFieldModel("royaltybdlive","royaltybdlive71473","10", "1012", CurrencyType.BDT, SdkType.LIVE, SdkCategory.BANK_LIST/*, BankName.DBBL_VISA*/);

        /* Optional Fields */
        /*CustomerFieldModel customerFieldModel = new CustomerFieldModel("Customer Name","Customer Email Address", "Customer Address 1", "Customer Address 2", "Customer City", "Customer State", "Customer Post Code", "Customer Country", " Customer Phone", "Customer Fax");
        ShippingFieldModel shippingFieldModel = new ShippingFieldModel("Shipping Name", "Shipping Address 1","Shipping Address 2","Shipping City", "Shipping State", "Shipping Post Code", "Shipping Country" );
        AdditionalFieldModel additionalFieldModel = new AdditionalFieldModel();
        additionalFieldModel.setValueA("Value Option 1");
        additionalFieldModel.setValueB("Value Option 1");
        additionalFieldModel.setValueC("Value Option 1");
        additionalFieldModel.setValueD("Value Option 1");*/

        /* Call for the payment */
        PayUsingSSLCommerz.getInstance().setData(this,mandatoryFieldModel,new OnPaymentResultListener() {
            @Override
            public void transactionSuccess(TransactionInfo transactionInfo) {
                // If payment is success and risk label is 0.
                if(transactionInfo.getRiskLevel().equals("0")) {
                    summery.setText("Status: Success \n\n"+new Gson().toJson(transactionInfo));
                }
                else{
                    summery.setText("Status: Risk \n\n"+new Gson().toJson(transactionInfo));
                }
            }

            @Override
            public void transactionFail(String sessionKey) {
                summery.setText("transactionFail -> Session : "+sessionKey);
            }

            @Override
            public void error(int errorCode) {
                switch (errorCode){
                    case ErrorKeys.USER_INPUT_ERROR :
                        summery.setText( "User Input Error" );break;
                    case ErrorKeys.INTERNET_CONNECTION_ERROR :
                        summery.setText("Internet Connection Error" );break;
                    case ErrorKeys.DATA_PARSING_ERROR :
                        summery.setText("Data Parsing Error" );break;
                    case ErrorKeys.CANCEL_TRANSACTION_ERROR :
                        summery.setText("User Cancel The Transaction" );break;
                    case ErrorKeys.SERVER_ERROR :
                        summery.setText("Server Error" );break;
                    case ErrorKeys.NETWORK_ERROR :
                        summery.setText("Network Error" );break;
                }
            }
        });

    }

}
