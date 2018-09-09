package com.sslcommerz.library.payment.model.config;

import android.content.res.Resources;

import java.util.Random;

public class BasicConfig {
    private static final BasicConfig ourInstance = new BasicConfig();
    public static BasicConfig getInstance() {
        return ourInstance;
    }
    private BasicConfig() { }


    public String getRequestId() {
        Random rand = new Random();
        return String.valueOf(System.currentTimeMillis() + "" + rand.nextInt(100000));
    }

    public int getPxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public int spToPx(float sp) {
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }
}
