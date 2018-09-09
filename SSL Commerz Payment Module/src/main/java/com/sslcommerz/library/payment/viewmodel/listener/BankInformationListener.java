package com.sslcommerz.library.payment.viewmodel.listener;

import com.sslcommerz.library.payment.model.dataset.BasicInformationModel;

public interface BankInformationListener extends BaseApiListener{
    void getSuccessInformation(BasicInformationModel basicInformationModel);
    void getErrorInformation(int responseCode, String message);
}
