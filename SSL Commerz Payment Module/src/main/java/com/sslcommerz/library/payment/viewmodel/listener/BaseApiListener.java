package com.sslcommerz.library.payment.viewmodel.listener;

public interface BaseApiListener {
    void startLoading(String requestId);
    void endLoading(String requestId);
}
