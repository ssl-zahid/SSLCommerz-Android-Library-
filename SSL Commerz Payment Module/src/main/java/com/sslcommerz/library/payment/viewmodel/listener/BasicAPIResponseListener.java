package com.sslcommerz.library.payment.viewmodel.listener;

import org.json.JSONObject;

public interface BasicAPIResponseListener extends BaseApiListener {
    void apiCallingSuccess(String requestId, JSONObject jsonObject);
    void apiCallingFail(String requestId, String message);
}
