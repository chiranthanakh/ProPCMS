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

public class VerifyCostTransferAdapter extends RecyclerView.Adapter<VerifyCostTransferAdapter.ViewHolder> {

    private ArrayList<VerifyCostTransferModel> listdata;
    List list = new ArrayList();
    private Context context;
    Boolean check;
    private OnClick mClick;
    Map map = new HashMap();

    public VerifyCostTransferAdapter(ArrayList<VerifyCostTransferModel> listdata, OnClick listner, Boolean check) {

        this.mClick = listner;
        this.check= check;
        this.context = context;
        this.listdata = listdata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.division_verify_cost_transfer_layout, parent, false);
        VerifyCostTransferAdapter.ViewHolder viewHolder = new VerifyCostTransferAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final VerifyCostTransferModel verifyCostTransferModel = listdata.get(position);

        if(check){
            holder.ch_vct_check_data.setChecked(true);
        }

        holder.tv_vct_ctn.setText(listdata.get(position).getVctCtn());
        holder.tv_vct_month.setText(listdata.get(position).getVctmonth());
        holder.tv_vct_expenseType.setText(listdata.get(position).getVctexpenseTypeName());
        holder.tv_vct_from_pc_code.setText(listdata.get(position).getVctfromPcCodeName());
        holder.tv_vct_to_pc_code.setText(listdata.get(position).getVcttoPcCodeName());

        float amount = Float.parseFloat(listdata.get(position).getVctamount());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);

        holder.tv_vct_transfer_cost.setText(moneyString);
        holder.tv_vct_remarks.setText(listdata.get(position).getVctremarks());

        if(check){
            holder.ch_vct_check_data.setChecked(true);
        }else {
            holder.ch_vct_check_data.setChecked(false);
        }

        holder.iv_vct_ALl_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClickitem(String.valueOf(position),1,listdata.get(position).getId());
            }
        });

        holder.ch_vct_check_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        public TextView tv_vct_ctn,tv_vct_month,tv_vct_expenseType,tv_vct_from_pc_code,tv_vct_to_pc_code,tv_vct_transfer_cost
                ,tv_vct_remarks;
        public ImageView iv_vct_ALl_data_view;
        public CheckBox ch_vct_check_data;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_vct_ALl_data_view=(ImageView)itemView.findViewById(R.id.iv_vct_ALl_data_view);
            this.ch_vct_check_data=itemView.findViewById(R.id.ch_vct_check_data);
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
