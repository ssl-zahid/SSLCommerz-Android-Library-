package com.sslcommerz.library.payment.view.adapter.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.sslcommerz.library.payment.model.dataset.CardListModel;
import com.sslcommerz.library.payment.view.fragment.SSLCommerzCardFragment;
import com.sslcommerz.library.payment.viewmodel.listener.OnCardButtonClickListener;

import java.util.ArrayList;


public abstract class SSLCommerzCardSliderAdapter extends FragmentStatePagerAdapter implements OnCardButtonClickListener {

    private ArrayList<CardListModel> cardListModel;
    private ArrayList<SSLCommerzCardFragment> fragments;

    public SSLCommerzCardSliderAdapter(FragmentManager fragmentManager, ArrayList<CardListModel> cardListModel) {
        super(fragmentManager);
        this.cardListModel = cardListModel;

        fragments = new ArrayList<>();
        int x=0;
        for (CardListModel cardData : cardListModel) {
            SSLCommerzCardFragment fragment = new SSLCommerzCardFragment();
            fragment.setPosition(x++);
            fragment.setOnCardButtonClickListener(this);
            fragment.setData(cardData);
            fragments.add(fragment);
        }
    }

    @Override
    public int getCount() {
        return cardListModel.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

}