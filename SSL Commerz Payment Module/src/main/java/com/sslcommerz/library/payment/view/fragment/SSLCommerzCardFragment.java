package com.sslcommerz.library.payment.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sslcommerz.library.payment.model.dataset.CardListModel;
import com.sslcommerz.library.payment.view.activity.R;
import com.sslcommerz.library.payment.viewmodel.listener.OnCardButtonClickListener;

public class SSLCommerzCardFragment extends Fragment implements View.OnClickListener {

    private CardListModel cardListModel;
    private TextView bankName,cardNumber,cardOwnerName;
    private ImageView cardTypeIcon,deleteCard;
    private CardView mainLayout;
    private View view;
    private int position;
    private OnCardButtonClickListener onCardButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_card_layout, null, false);

        bankName = view.findViewById(R.id.bankName);
        cardNumber = view.findViewById(R.id.cardNumber);
        cardOwnerName = view.findViewById(R.id.cardOwnerName);
        cardTypeIcon = view.findViewById(R.id.cardTypeIcon);
        deleteCard = view.findViewById(R.id.delete_card);
        mainLayout = view.findViewById(R.id.cardView);

        bankName.setText(cardListModel.getIssuerBank());
        cardNumber.setText(cardListModel.getCardNo());
        cardOwnerName.setText(cardListModel.getName());
        try{
            int cardTypeIconId = -1;
            if(cardListModel.getCardType().toLowerCase().contains("visa"))
                cardTypeIconId = R.drawable.visa_card;
            else if(cardListModel.getCardType().toLowerCase().contains("master"))
                cardTypeIconId = R.drawable.master_card;
            else if(cardListModel.getCardType().toLowerCase().contains("amex"))
                cardTypeIconId = R.drawable.amex_card;

            Picasso.get().load(cardTypeIconId).into(cardTypeIcon);
        }catch (Exception e){}

        try{
            mainLayout.setCardBackgroundColor(Color.parseColor("#"+cardListModel.getDesign().getBkColor()));
            cardNumber.setTextColor(Color.parseColor("#"+cardListModel.getDesign().getFontColor()));
            bankName.setTextColor(Color.parseColor("#"+cardListModel.getDesign().getFontColor()));
            cardOwnerName.setTextColor(Color.parseColor("#"+cardListModel.getDesign().getFontColor()));
        }catch (Exception e){}

        deleteCard.setOnClickListener(this);
        view.setOnClickListener(this);

        deleteCard.setColorFilter(Color.parseColor("#"+cardListModel.getDesign().getBtnYesFontColor()));

        return view;
    }

    public void setData(CardListModel data) {
        this.cardListModel = data;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        if(v==deleteCard){
            onCardButtonClickListener.getDeletePosition(position);
        } else if(v==view){
            onCardButtonClickListener.getItemPosition(position);
        }
    }

    public void setOnCardButtonClickListener(OnCardButtonClickListener onCardButtonClickListener) {
        this.onCardButtonClickListener = onCardButtonClickListener;
    }
}
