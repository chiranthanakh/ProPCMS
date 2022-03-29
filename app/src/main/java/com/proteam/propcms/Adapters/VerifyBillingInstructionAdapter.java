package com.proteam.propcms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.Models.VerifyBillingInstructionModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Utils.OnClick;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VerifyBillingInstructionAdapter extends RecyclerView.Adapter<VerifyBillingInstructionAdapter.ViewHolder>{

    private ArrayList<VerifyBillingInstructionModel> listdata;
    private Context context;
    List list = new ArrayList();
    Boolean check;
    private OnClick mClick;
    Map map = new HashMap();
    public VerifyBillingInstructionAdapter(ArrayList<VerifyBillingInstructionModel> listdata, OnClick listner, Boolean check) {

        this.mClick = listner;
        this.check= check;
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.division_verify_billing_instruction_layout, parent, false);
        VerifyBillingInstructionAdapter.ViewHolder viewHolder = new VerifyBillingInstructionAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerifyBillingInstructionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        final VerifyBillingInstructionModel verifyBillingInstructionModel = listdata.get(position);

        holder.tv_BI_pcCode.setText(listdata.get(position).getBIPcCode());

        holder.tv_BI_billTO.setText(listdata.get(position).getBIbillTO());

        holder.tv_BI_Reference_number.setText(listdata.get(position).getBIrefrenceNumber());
        holder.tv_BI_kindAttention.setText(listdata.get(position).getBIkindAttention());


        float amount = Float.parseFloat(listdata.get(position).getBItaxableAmount());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);


        holder.tv_BI_taxableAmount.setText(moneyString);
        holder.tv_BI_GstRate.setText(listdata.get(position).getBIgstRate());
        holder.tv_BI_for_month.setText(listdata.get(position).getBIforMonth());


        if(check){
            holder.ch_BI_check_data.setChecked(true);
        }else {
            holder.ch_BI_check_data.setChecked(false);
        }

        holder.iv_BI_ALl_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClickitem(String.valueOf(position),1,listdata.get(position).getId());
            }
        });

        holder.ch_BI_check_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    map.put(position,listdata.get(position).getId());
                    mClick.onClickitem(String.valueOf(position),2,listdata.get(position).getId());
                }else {
                    map.remove(position);
                    mClick.onClickitem(String.valueOf(position),3,listdata.get(position).getId());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_BI_pcCode,tv_BI_group,tv_BI_assignment,tv_BI_billTO,tv_BI_billing_address,tv_BI_Reference_number,
                tv_BI_kindAttention,tv_BI_Region,tv_BI_place,tv_BI_gstIn_no,tv_BI_panOf_Customer,tv_BI_taxableAmount,
                tv_BI_GstRate,tv_BI_for_month,tv_BI_description,tv_BI_hsn_sac,tv_BI_particulars,tv_BI_stateOf_supply,
                tv_BI_transaction_type;

        public ImageView iv_BI_ALl_data_view;
        public CheckBox ch_BI_check_data;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ch_BI_check_data = itemView.findViewById(R.id.ch_BI_check_data);
            iv_BI_ALl_data_view=(ImageView) itemView.findViewById(R.id.iv_BI_ALl_data_view);

            this.tv_BI_pcCode = (TextView) itemView.findViewById(R.id.tv_BI_pcCode);
            this.tv_BI_group = (TextView) itemView.findViewById(R.id.tv_BI_group);
            this.tv_BI_assignment = (TextView) itemView.findViewById(R.id.tv_BI_assignment);
            this.tv_BI_billTO = (TextView) itemView.findViewById(R.id.tv_BI_billTO);
            this.tv_BI_billing_address = (TextView) itemView.findViewById(R.id.tv_BI_billing_address);
            this.tv_BI_Reference_number = (TextView) itemView.findViewById(R.id.tv_BI_Reference_number);
            this.tv_BI_kindAttention = (TextView) itemView.findViewById(R.id.tv_BI_kindAttention);
            this.tv_BI_Region = (TextView) itemView.findViewById(R.id.tv_BI_Region);
            this.tv_BI_place = (TextView) itemView.findViewById(R.id.tv_BI_place);
            this.tv_BI_gstIn_no = (TextView) itemView.findViewById(R.id.tv_BI_gstIn_no);
            this.tv_BI_panOf_Customer = (TextView) itemView.findViewById(R.id.tv_BI_panOf_Customer);
            this.tv_BI_taxableAmount = (TextView) itemView.findViewById(R.id.tv_BI_taxableAmount);
            this.tv_BI_GstRate = (TextView) itemView.findViewById(R.id.tv_BI_GstRate);
            this.tv_BI_for_month = (TextView) itemView.findViewById(R.id.tv_BI_for_month);
            this.tv_BI_description = (TextView) itemView.findViewById(R.id.tv_BI_description);
            this.tv_BI_hsn_sac = (TextView) itemView.findViewById(R.id.tv_BI_hsn_sac);
            this.tv_BI_particulars = (TextView) itemView.findViewById(R.id.tv_BI_particulars);
            this.tv_BI_stateOf_supply = (TextView) itemView.findViewById(R.id.tv_BI_stateOf_supply);
            this.tv_BI_transaction_type = (TextView) itemView.findViewById(R.id.tv_BI_transaction_type);

        }
    }
}
