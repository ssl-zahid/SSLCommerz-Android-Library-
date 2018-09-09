package com.sslcommerz.library.payment.viewmodel.listener;

import com.sslcommerz.library.payment.model.dataset.TransactionInfo;

public interface TransactionInformationListener extends BaseApiListener {
    void successTransactionInfo(TransactionInfo transactionInfo);
    void failTransactionInfo(int responseCode, String message);
}
