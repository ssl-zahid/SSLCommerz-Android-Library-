package com.sslcommerz.library.payment.viewmodel.listener;

import com.sslcommerz.library.payment.model.dataset.TransactionInfo;

/**
 * Created by SSL_ZAHID on 10/25/2016.
 */

public interface OnPaymentResultListener {
    void transactionSuccess(TransactionInfo transactionInfo);
    void transactionFail(String sessionKey);
    void error(int errorCode);
}
