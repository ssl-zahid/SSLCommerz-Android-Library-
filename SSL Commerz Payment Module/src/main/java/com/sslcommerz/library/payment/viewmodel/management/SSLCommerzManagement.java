package com.sslcommerz.library.payment.viewmodel.management;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sslcommerz.library.payment.model.config.ApiClient;
import com.sslcommerz.library.payment.model.config.ApiHandler;
import com.sslcommerz.library.payment.model.config.BasicConfig;
import com.sslcommerz.library.payment.model.datamodel.AllInformation;
import com.sslcommerz.library.payment.model.dataset.BasicInformationModel;
import com.sslcommerz.library.payment.model.dataset.TransactionInfo;
import com.sslcommerz.library.payment.viewmodel.encryption.EncryptionHandler;
import com.sslcommerz.library.payment.viewmodel.listener.BankInformationListener;
import com.sslcommerz.library.payment.viewmodel.listener.BasicAPIResponseListener;
import com.sslcommerz.library.payment.viewmodel.listener.TransactionInformationListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Md. Zahidul Islam on 9/8/2018.
 */

public class SSLCommerzManagement {

    private Context context;
    private ApiHandler apiHandler;
    public String BANK_LIST_REQUEST_ID,DELETE_CARD_REQUEST_ID,CONFIRM_PAYMENT_REQUEST_ID,BASIC_RESPONSE_REQUEST_ID,TRANSACTION_INFORMATION_REQUEST_ID;
    private BankInformationListener bankInformationListener;
    private BasicAPIResponseListener basicAPIResponseListener;
    private TransactionInformationListener transactionInformationListener;

    public SSLCommerzManagement(Context context){
        this.context = context;
        apiHandler = new ApiHandler(context) {
            @Override
            public void startApiCall(String requestId) {
                if(requestId.equals(BANK_LIST_REQUEST_ID)){
                    bankInformationListener.startLoading(requestId);
                } else if(requestId.equals(BASIC_RESPONSE_REQUEST_ID)){
                    basicAPIResponseListener.startLoading(requestId);
                } else if(requestId.equals(TRANSACTION_INFORMATION_REQUEST_ID)){
                    transactionInformationListener.startLoading(requestId);
                }
            }

            @Override
            public void endApiCall(String requestId) {
                if(requestId.equals(BANK_LIST_REQUEST_ID)){
                    bankInformationListener.endLoading(requestId);
                } else if(requestId.equals(BASIC_RESPONSE_REQUEST_ID)){
                    basicAPIResponseListener.endLoading(requestId);
                } else if(requestId.equals(TRANSACTION_INFORMATION_REQUEST_ID)){
                    transactionInformationListener.endLoading(requestId);
                }
            }

            @Override
            public void successResponse(String requestId, JSONObject response, String baseUrl, String path, String requestType, Map hashMap) {
                Gson gson = new Gson();
                if(requestId.equals(BANK_LIST_REQUEST_ID)){
                    bankInformationListener.getSuccessInformation(gson.fromJson(response.toString(), BasicInformationModel.class));
                } else if(requestId.equals(BASIC_RESPONSE_REQUEST_ID)){
                    basicAPIResponseListener.apiCallingSuccess(requestId,response);
                } else if(requestId.equals(TRANSACTION_INFORMATION_REQUEST_ID)){
                    transactionInformationListener.successTransactionInfo(gson.fromJson(response.toString(), TransactionInfo.class));
                }
            }

            @Override
            public void failResponse(String requestId, int responseCode, String message) {
                if(requestId.equals(BANK_LIST_REQUEST_ID)){
                    bankInformationListener.getErrorInformation(responseCode, message);
                } else if(requestId.equals(BASIC_RESPONSE_REQUEST_ID)){
                    basicAPIResponseListener.apiCallingFail(requestId, message);
                } else if(requestId.equals(TRANSACTION_INFORMATION_REQUEST_ID)){
                    transactionInformationListener.failTransactionInfo(responseCode, message);
                }
            }
        };
    }

    public void getPrimaryTransactionInfo(Map map, boolean isLive, BankInformationListener bankInformationListener){
        this.bankInformationListener = bankInformationListener;
        apiHandler.httpRequest(isLive ? AllInformation.BANK_LIST_URL : AllInformation.BANK_LIST_URL_SANDBOX,"api.php","post",BANK_LIST_REQUEST_ID = BasicConfig.getInstance().getRequestId(),map);
    }

    public void deleteCard(boolean isLive, String sessionId, String token, BasicAPIResponseListener basicAPIResponseListener){
        this.basicAPIResponseListener = basicAPIResponseListener;
        HashMap getMap = new HashMap();
        HashMap postMap = new HashMap();
        getMap.put("Q", "DELETETOKEN");
        getMap.put("SESSIONKEY", sessionId);
        postMap.put("token_data", token);
        apiHandler.httpRequest(isLive ? AllInformation.BANK_LIST_URL : AllInformation.BANK_LIST_URL_SANDBOX,"gw.php","post&get",BASIC_RESPONSE_REQUEST_ID = DELETE_CARD_REQUEST_ID = BasicConfig.getInstance().getRequestId(),postMap,getMap);
    }

    public void confirmPayUsingCard(boolean isLive, String sessionId, String token, BasicAPIResponseListener basicAPIResponseListener){
        this.basicAPIResponseListener = basicAPIResponseListener;
        HashMap getMap = new HashMap();
        HashMap postMap = new HashMap();
        getMap.put("Q", "TOKENIZE");
        getMap.put("SESSIONKEY", sessionId);
        postMap.put("token_data", token);
        apiHandler.httpRequest(isLive ? AllInformation.BANK_LIST_URL : AllInformation.BANK_LIST_URL_SANDBOX,"gw.php","post&get",BASIC_RESPONSE_REQUEST_ID = CONFIRM_PAYMENT_REQUEST_ID = BasicConfig.getInstance().getRequestId(),postMap,getMap);
    }

    public void getTransactionInformation(String sessionKey, String storeId, String storePassword, boolean isLive, TransactionInformationListener transactionInformationListener){
        this.transactionInformationListener = transactionInformationListener;
        HashMap hashMap = new HashMap();
        hashMap.put("sessionkey",sessionKey);
        hashMap.put("Store_Id",storeId);
        hashMap.put("Store_Passwd",storePassword);
        apiHandler.httpRequest(isLive ? AllInformation.TRANSACTION_ALL_INFO : AllInformation.TRANSACTION_ALL_INFO_SANDBOX,"merchantTransIDvalidationAPI.php","get",TRANSACTION_INFORMATION_REQUEST_ID = BasicConfig.getInstance().getRequestId(),hashMap);
    }


































}
