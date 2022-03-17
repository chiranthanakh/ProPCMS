package com.proteam.propcms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Utils.OnClick;

import java.util.ArrayList;

public class VerifyCostTransferAdapter extends RecyclerView.Adapter<VerifyCostTransferAdapter.ViewHolder> {

    private VerifyCostTransferModel[] listdata;
    private Context context;

    public VerifyCostTransferAdapter(VerifyCostTransferModel[] listdata) {

        this.context = context;
        this.listdata = listdata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.verify_cost_transfer_layout, parent, false);
        VerifyCostTransferAdapter.ViewHolder viewHolder = new VerifyCostTransferAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final VerifyCostTransferModel verifyCostTransferModel = listdata[position];

        holder.tv_vct_ctn.setText(listdata[position].getVctCtn());
        holder.tv_vct_month.setText(listdata[position].getVctMonth());
        holder.tv_vct_expenseType.setText(listdata[position].getVctExpenseType());
        holder.tv_vct_from_pc_code.setText(listdata[position].getVctFromPcCode());
        holder.tv_vct_to_pc_code.setText(listdata[position].getVctToPcCode());
        holder.tv_vct_transfer_cost.setText(listdata[position].getVctTransferCost());
        holder.tv_vct_remarks.setText(listdata[position].getVctRemarks());

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_vct_ctn,tv_vct_month,tv_vct_expenseType,tv_vct_from_pc_code,tv_vct_to_pc_code,tv_vct_transfer_cost
                ,tv_vct_remarks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_vct_ctn = (TextView) itemView.findViewById(R.id.tv_vct_ctn);
            this.tv_vct_month = (TextView) itemView.findViewById(R.id.tv_vct_month);
            this.tv_vct_expenseType = (TextView) itemView.findViewById(R.id.tv_vct_expenseType);
            this.tv_vct_from_pc_code = (TextView) itemView.findViewById(R.id.tv_vct_from_pc_code);
            this.tv_vct_to_pc_code = (TextView) itemView.findViewById(R.id.tv_vct_to_pc_code);
            this.tv_vct_transfer_cost = (TextView) itemView.findViewById(R.id.tv_vct_transfer_cost);
            this.tv_vct_remarks = (TextView) itemView.findViewById(R.id.tv_vct_remarks);


        }
    }
}
