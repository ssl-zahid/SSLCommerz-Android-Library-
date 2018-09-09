package com.sslcommerz.library.payment.model.config;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sslcommerz.library.payment.model.util.ErrorKeys;
import com.sslcommerz.library.payment.view.activity.R;

import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public abstract class ApiHandler {

    private Context context;

    public ApiHandler(Context context) {
        this.context = context;
    }

    public void httpRequest(final String baseUrl,final String path,final String requestType,final String requestId,final Map hashMap){
        httpRequest(baseUrl,path,requestType,requestId,hashMap,null);
    }

    public void httpRequest(final String baseUrl,final String path,final String requestType,final String requestId,final Map mainMap, Map additionalGetMap ) {
        try {
            startApiCall(requestId);
            Call<ResponseBody> bodyToCall = null;
            if (requestType.toLowerCase().equals("get"))
                bodyToCall = ApiClient.callApi(baseUrl).getRequest(path, mainMap);
            else if (requestType.toLowerCase().equals("post"))
                bodyToCall = ApiClient.callApi(baseUrl).postRequest(path, mainMap);
            else if (requestType.toLowerCase().equals("post&get"))
                bodyToCall = ApiClient.callApi(baseUrl).postGetRequest(path, mainMap,additionalGetMap);
            else if (requestType.toLowerCase().equals("img"))
                bodyToCall = ApiClient.callApi(baseUrl).sendDocuments(path, mainMap);
            bodyToCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    endApiCall(requestId);
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("status").equals("SUCCESS") || jsonObject.getString("status").equals("VALID")|| jsonObject.getString("status").equals("VALIDATED"))
                                successResponse(requestId, jsonObject, baseUrl, path, requestType, mainMap);
                            else
                                failResponse(requestId, ErrorKeys.FAIL_RESPONSE, jsonObject.getString(jsonObject.getString("failedreason")));
                        } catch (Exception e) {
                            failResponse(requestId, ErrorKeys.DATA_PARSING_ERROR, context.getString(R.string.invalid_json_response));
                        }
                    } else {
                        failResponse(requestId, ErrorKeys.SERVER_ERROR, context.getString(R.string.server_not_responding));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    endApiCall(requestId);
                    if (throwable instanceof HttpException)
                        failResponse(requestId, ErrorKeys.NETWORK_ERROR, context.getString(R.string.invalid_request));
                    else if (throwable instanceof UnknownHostException)
                        failResponse(requestId, ErrorKeys.NETWORK_ERROR, context.getString(R.string.server_not_responding));
                    else if (throwable instanceof IOException)
                        failResponse(requestId, ErrorKeys.NETWORK_ERROR, context.getString(R.string.connectivity_error));
                    else
                        failResponse(requestId, ErrorKeys.NETWORK_ERROR, context.getString(R.string.unknown_error));
                }
            });
        } catch (Exception e) {
            failResponse(requestId, ErrorKeys.DATA_PARSING_ERROR, context.getString(R.string.unknown_error));
        }
    }

    public abstract void startApiCall(String requestId);
    public abstract void endApiCall(String requestId);
    public abstract void successResponse(String requestId, JSONObject response, String baseUrl, String path, String requestType, Map hashMap);
    public abstract void failResponse(String requestId, int responseCode, String message);
}
