package com.sslcommerz.library.payment.view.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by zahidul.islam on 7/9/2017.
 */

public class CardNumberFontTextView extends android.support.v7.widget.AppCompatTextView {


    public CardNumberFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CardNumberFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardNumberFontTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/customfont/kredit-font.ttf");
        setTypeface(tf);
    }

}
