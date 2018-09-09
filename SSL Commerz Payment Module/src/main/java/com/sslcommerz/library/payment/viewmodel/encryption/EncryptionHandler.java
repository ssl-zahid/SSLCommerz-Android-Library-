package com.sslcommerz.library.payment.viewmodel.encryption;

import android.util.Base64;


import com.google.gson.Gson;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class EncryptionHandler {

    private static final EncryptionHandler encryptionHandler = new EncryptionHandler();

    private EncryptionHandler() {}

    public static EncryptionHandler getInstance() {
        return encryptionHandler;
    }

    public String encryptData(String secretKey, String data){
        String encryptedData;
        String keyData = secretKey;
        String ivValue = EncryptionController.getIvString(16);
        try {
            String encryptionValue = EncryptionController.encrypt(ivValue, keyData, data);
            encryptedData = Base64.encodeToString((ivValue + "|||" + encryptionValue).getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
            encryptedData = null;
        }
        return encryptedData;
    }

    public String decryptData(String secretKey, String data){
        String decryptedData;
        String[] parseResponseValue = new String(Base64.decode(data, Base64.DEFAULT)).split("\\|\\|\\|");
        try {
            decryptedData = EncryptionController.decrypt(parseResponseValue[0], secretKey, parseResponseValue[1]);
            return decryptedData;
        } catch (Exception e) {
            return null;
        }
    }

}
