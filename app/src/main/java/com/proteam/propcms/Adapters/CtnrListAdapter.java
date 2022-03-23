package com.proteam.propcms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Utils.OnClick;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CtnrListAdapter extends RecyclerView.Adapter<CtnrListAdapter.ViewHolder> {


    //private CtrnDataModel[] listdata;
    private Context context;
    private  Boolean check;
    private ArrayList<CtrnDataModel> listdata;
    private OnClick mClick;
    Map map = new HashMap();

    public CtnrListAdapter(ArrayList<CtrnDataModel> listdata,OnClick listner,Boolean check) {
        this.mClick = listner;
        this.check = check;
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.ctnr_data_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CtrnDataModel ctrnDataModel = listdata.get(position);


        holder.tv_ctnr_ctn.setText(listdata.get(position).getCtnrCtn());
        holder.tv_ctnr_month.setText(listdata.get(position).getCtnrMonth());
        holder.tv_ctnr_expense_type.setText(listdata.get(position).getCtnrExpenseType());
        holder.tv_ctnr_fromPc_code.setText(listdata.get(position).getCtnrFromPcCode());
        holder.tv_ctnr_toPc_code.setText(listdata.get(position).getCtnrToPcCode());

        float amount = Float.parseFloat(listdata.get(position).getCtnrTransferCost());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);

        holder.tv_ctnr_transfer_cost.setText(moneyString);
        holder.tv_ctnr_remarks.setText(listdata.get(position).getCtnrRemarks());

        if(check){
            holder.ch_ctnr_check_data.setChecked(true);
        }else {
            holder.ch_ctnr_check_data.setChecked(false);
        }

        holder.iv_ctnr_ALl_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClickitem(String.valueOf(position),1,"0");
            }
        });

        holder.iv_ctnr_action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClickitem(String.valueOf(position),2,"0");
            }
        });

        holder.ch_ctnr_check_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    map.put(position,listdata.get(position).getId());
                    mClick.onClickitem(String.valueOf(position),3,listdata.get(position).getId());
                }else {
                    map.remove(position);
                    mClick.onClickitem(String.valueOf(position),4,listdata.get(position).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_ctnr_ALl_data_view,iv_ctnr_status_modification,iv_ctnr_upload,iv_ctnr_action_delete,iv_ctnr_action_edit;
        public TextView tv_ctnr_ctn,tv_ctnr_month,tv_ctnr_expense_type,tv_ctnr_fromPc_code,tv_ctnr_toPc_code,
                tv_ctnr_transfer_cost,tv_ctnr_remarks;
        public CheckBox ch_ctnr_check_data;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_ctnr_ALl_data_view = (ImageView) itemView.findViewById(R.id.iv_ctnr_ALl_data_view);
            this.iv_ctnr_status_modification = (ImageView) itemView.findViewById(R.id.iv_ctnr_status_modification);
            this.iv_ctnr_upload = (ImageView) itemView.findViewById(R.id.iv_ctnr_upload);
            this.iv_ctnr_action_delete = (ImageView) itemView.findViewById(R.id.iv_ctnr_action_delete);
            this.iv_ctnr_action_edit = (ImageView) itemView.findViewById(R.id.iv_ctnr_action_edit);

            this.tv_ctnr_ctn = (TextView) itemView.findViewById(R.id.tv_ctnr_ctn);
            this.tv_ctnr_month = (TextView) itemView.findViewById(R.id.tv_ctnr_month);
            this.tv_ctnr_expense_type = (TextView) itemView.findViewById(R.id.tv_ctnr_expense_type);
            this.tv_ctnr_fromPc_code = (TextView) itemView.findViewById(R.id.tv_ctnr_fromPc_code);
            this.tv_ctnr_toPc_code = (TextView) itemView.findViewById(R.id.tv_ctnr_toPc_code);
            this.tv_ctnr_transfer_cost = (TextView) itemView.findViewById(R.id.tv_ctnr_transfer_cost);
            this.tv_ctnr_remarks = (TextView) itemView.findViewById(R.id.tv_ctnr_remarks);
            this.ch_ctnr_check_data = itemView.findViewById(R.id.ch_ctnr_check_data);


        }
    }
}
