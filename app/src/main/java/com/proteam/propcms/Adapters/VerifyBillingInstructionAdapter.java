package com.proteam.propcms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.VerifyBillingInstructionModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;

public class VerifyBillingInstructionAdapter extends RecyclerView.Adapter<VerifyBillingInstructionAdapter.ViewHolder>{

    private VerifyBillingInstructionModel[] listdata;
    private Context context;

    public VerifyBillingInstructionAdapter(VerifyBillingInstructionModel[] listdata) {

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
    public void onBindViewHolder(@NonNull VerifyBillingInstructionAdapter.ViewHolder holder, int position)
    {
        final VerifyBillingInstructionModel verifyBillingInstructionModel = listdata[position];

        holder.tv_BI_pcCode.setText(listdata[position].getBIPcCode());
        holder.tv_BI_group.setText(listdata[position].getBIgroup());
        holder.tv_BI_assignment.setText(listdata[position].getBIassigmnent());
        holder.tv_BI_billTO.setText(listdata[position].getBIbillTO());
        holder.tv_BI_billing_address.setText(listdata[position].getBIbillingAdress());
        holder.tv_BI_Reference_number.setText(listdata[position].getBIrefrenceNumber());
        holder.tv_BI_kindAttention.setText(listdata[position].getBIkindAttention());
        holder.tv_BI_Region.setText(listdata[position].getBIregion());
        holder.tv_BI_place.setText(listdata[position].getBIplace());
        holder.tv_BI_gstIn_no.setText(listdata[position].getBIgstinNo());
        holder.tv_BI_panOf_Customer.setText(listdata[position].getBIpanOfCustomer());
        holder.tv_BI_taxableAmount.setText(listdata[position].getBItaxableAmount());
        holder.tv_BI_GstRate.setText(listdata[position].getBIgstRate());
        holder.tv_BI_for_month.setText(listdata[position].getBIforMonth());
        holder.tv_BI_description.setText(listdata[position].getBIdescription());
        holder.tv_BI_hsn_sac.setText(listdata[position].getBIhsnSac());
        holder.tv_BI_particulars.setText(listdata[position].getBIparticulars());
        holder.tv_BI_stateOf_supply.setText(listdata[position].getBIstateOfSupplyCode());
        holder.tv_BI_transaction_type.setText(listdata[position].getBItransactionType());


    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_BI_pcCode,tv_BI_group,tv_BI_assignment,tv_BI_billTO,tv_BI_billing_address,tv_BI_Reference_number,
                tv_BI_kindAttention,tv_BI_Region,tv_BI_place,tv_BI_gstIn_no,tv_BI_panOf_Customer,tv_BI_taxableAmount,
                tv_BI_GstRate,tv_BI_for_month,tv_BI_description,tv_BI_hsn_sac,tv_BI_particulars,tv_BI_stateOf_supply,
                tv_BI_transaction_type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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
