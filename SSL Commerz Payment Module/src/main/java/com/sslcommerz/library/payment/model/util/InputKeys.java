package com.sslcommerz.library.payment.model.util;

/**
 * Created by Zahidul Islam_2 on 2/14/2016.
 */
public class InputKeys {

    /*Mandatory Fields*/
    public static final String STORE_ID = "store_id";
    public static final String STORE_PASSWORD = "store_passwd";
    public static final String TOTAL_AMOUNT = "total_amount";
    public static final String CURRENCY = "currency";
    public static final String TRANSACTION_ID = "tran_id";
    public static final String MULTI_CARD_NAME = "multi_card_name";
    public static final String TOKEN_KEY = "token_key";
    private static final String SUCCESS_URL = "success_url";
    private static final String FAIL_URL = "fail_url";
    private static final String CANCEL_URL = "cancel_url";
    public static final String SDK_TYPE = "sdk_type";
    public static final String SDK_CATEGORY = "sdk_category";
    public static final String BANK_NAME = "bank_name";
    public static final String SDK_SPECIFIC_BANK_PAGE = "bank_page";
    public static final String SDK_BANK_LIST = "bank_list";


    /*Optional Field For Customer*/
    public static final String CUSTOMER_NAME = "cus_name";
    public static final String CUSTOMER_EMAIL = "cus_email";
    public static final String CUSTOMER_ADDRESS_1 = "cus_add1";
    public static final String CUSTOMER_ADDRESS_2 = "cus_add2";
    public static final String CUSTOMER_CITY = "cus_city";
    public static final String CUSTOMER_STATE = "cus_state";
    public static final String CUSTOMER_POSTCODE = "cus_postcode";
    public static final String CUSTOMER_COUNTRY = "cus_country";
    public static final String CUSTOMER_PHONE = "cus_phone";
    public static final String CUSTOMER_FAX = "cus_fax";


    /*Optional Field For Shipment*/
    public static final String SHIP_NAME = "ship_name";
    public static final String SHIP_ADDRESS_1 = "ship_add1";
    public static final String SHIP_ADDRESS_2 = "ship_add2";
    public static final String SHIP_CITY = "ship_city";
    public static final String SHIP_STATE = "ship_state";
    public static final String SHIP_POSTCODE = "ship_postcode";
    public static final String SHIP_COUNTRY = "ship_country";


    /*Optional Parameter*/
    public static final String VALUE_A = "value_a";
    public static final String VALUE_B = "value_b";
    public static final String VALUE_C = "value_c";
    public static final String VALUE_D = "value_d";

}
