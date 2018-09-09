package com.sslcommerz.library.payment.view.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.sslcommerz.library.payment.model.config.BasicConfig;
import com.sslcommerz.library.payment.model.dataset.BasicInformationModel;
import com.sslcommerz.library.payment.view.activity.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by zahidul.islam on 7/11/2017.
 */

public class SSLCommerzBankAdapter extends RecyclerView.Adapter<SSLCommerzBankAdapter.SSLCommerzBankViewHolder> {

    private Context context;
    private ArrayList<BasicInformationModel.AllBankList> bankList;
    private ArrayList<Boolean> check;
    public int prePos;
    public String bankName;
    public String bankUrl;
    private int width;
    private int height;


    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    public SSLCommerzBankAdapter(Context context, ArrayList<BasicInformationModel.AllBankList> bankList, int width, int height){
        this.bankList = bankList;
        this.context = context;
        /*ShareInfo.dpToPx(70);*/
        this.width = (width-(BasicConfig.getInstance().dpToPx(70)))/4;
        this.height = height;
        prePos = -1;
        check = new ArrayList<>();
        for (int x=0;x<bankList.size();x++) {
            check.add(false);
        }
    }

    @Override
    public SSLCommerzBankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ssl_commerz_bank_adapter, null, false);
        return new SSLCommerzBankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SSLCommerzBankViewHolder holder, final int position) {
        try{
            setFadeAnimation(holder.itemView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(prePos == -1){
                        check.set(position,true);
                        notifyItemChanged(position);
                        bankName = bankList.get(position).getName();
                        bankUrl = bankList.get(position).getRedirectGatewayURL();
                        prePos = position;
                    } else {
                        check.set(position,true);
                        check.set(prePos,false);
                        notifyItemChanged(prePos);
                        notifyItemChanged(position);
                        if(prePos != position){
                            bankName = bankList.get(position).getName();
                            bankUrl = bankList.get(position).getRedirectGatewayURL();
                        } else {
                            bankName = null;
                            bankUrl = null;
                        }
                        prePos = position;
                    }
                }
            });

            if(check.get(position)) {
                holder.selectedContentArea.setVisibility(View.VISIBLE);
            } else{
                holder.selectedContentArea.setVisibility(View.GONE);
            }

            holder.mainContentArea.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteLight));
            holder.bankLogo.getLayoutParams().width = width;
            holder.bankLogo.getLayoutParams().height = width;
            holder.selectedContentArea.getLayoutParams().width = width;
            holder.selectedContentArea.getLayoutParams().height = width;


            if((position+1)%4 == 2 || (position+1)%4 == 3) {
                holder.mainContentArea.setGravity(Gravity.CENTER);
            } else if((position+1)%4 == 1) {
                holder.mainContentArea.setGravity(Gravity.LEFT);
            } else if((position+1)%4 == 0) {
                holder.mainContentArea.setGravity(Gravity.RIGHT);
            }
            holder.bankLogo.requestLayout();

            Picasso.get().load(bankList.get(position).getLogo().toString()).into(holder.bankLogo);
        }catch (Exception e){
            Log.e(TAG, "onBindViewHolder: "+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class SSLCommerzBankViewHolder extends RecyclerView.ViewHolder {

        ImageView bankLogo;
        RelativeLayout mainContentArea;
        ImageView selectedContentArea;

        public SSLCommerzBankViewHolder(View itemView) {
            super(itemView);
            bankLogo = itemView.findViewById(R.id.bankLogo);
            mainContentArea = itemView.findViewById(R.id.mainContentArea);
            selectedContentArea = itemView.findViewById(R.id.selectedContentArea);
        }
    }
}
