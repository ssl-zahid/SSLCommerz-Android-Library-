package com.sslcommerz.library.payment.view.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sslcommerz.library.payment.view.activity.R;
import com.sslcommerz.library.payment.viewmodel.listener.OnCardButtonClickListener;
import com.sslcommerz.library.payment.model.dataset.BasicInformationModel;

import java.util.ArrayList;

/**
 * Created by SSL_ZAHID on 10/27/2016.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListViewHolder> {

    private Context context;
    private ArrayList<BasicInformationModel.AllBankList> bankList;
    private OnCardButtonClickListener itemClickListener;

    public BankListAdapter(Context context,ArrayList<BasicInformationModel.AllBankList> bankList){
        this.context = context;
        this.bankList = bankList;
    }

    @Override
    public BankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ssl_commerz_bank_list_item_adapter,null,false);
        return new BankListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankListViewHolder holder, int position) {

        if(position == bankList.size()-1) holder.bottomArea.setVisibility(View.VISIBLE);
        else holder.bottomArea.setVisibility(View.GONE);

        try{
            holder.bankName.setText(bankList.get(position).getName().toString());
            //Picasso.get().load(bankList.get(position).getLogo().toString()).placeholder(R.drawable.card_placeholder).into(holder.bankLogo);
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public void setOnItemClickListener(OnCardButtonClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class BankListViewHolder extends RecyclerView.ViewHolder {

        private ImageView bankLogo;
        private TextView bankName;
        private LinearLayout bottomArea;

        public BankListViewHolder(View itemView) {
            super(itemView);
            bankLogo = (ImageView) itemView.findViewById(R.id.bankLogo);
            bankName = (TextView) itemView.findViewById(R.id.bankName);
            bottomArea = (LinearLayout) itemView.findViewById(R.id.bottomArea);

        }
    }
}
