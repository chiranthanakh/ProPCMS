package com.proteam.propcms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.R;

public class CtnrListAdapter extends RecyclerView.Adapter<CtnrListAdapter.ViewHolder> {


    private CtrnDataModel[] listdata;
    private Context context;

    public CtnrListAdapter(CtrnDataModel[] listdata) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CtrnDataModel ctrnDataModel = listdata[position];
        holder.iv_ctnr_ALl_data_view.setImageResource(listdata[position].getCtnrAllDetails());
        holder.iv_ctnr_action_edit.setImageResource(listdata[position].getCtnrActionEdit());
        holder.iv_ctnr_action_delete.setImageResource(listdata[position].getCtnrActionDelete());
        holder.iv_ctnr_upload.setImageResource(listdata[position].getCtnrUpload());
        holder.iv_ctnr_status_modification.setImageResource(listdata[position].getCtnrStatusModification());

        holder.tv_ctnr_ctn.setText(listdata[position].getCtnrCtn());
        holder.tv_ctnr_month.setText(listdata[position].getCtnrMonth());
        holder.tv_ctnr_expense_type.setText(listdata[position].getCtnrExpenseType());
        holder.tv_ctnr_fromPc_code.setText(listdata[position].getCtnrFromPcCode());
        holder.tv_ctnr_toPc_code.setText(listdata[position].getCtnrToPcCode());
        holder.tv_ctnr_transfer_cost.setText(listdata[position].getCtnrTransferCost());
        holder.tv_ctnr_remarks.setText(listdata[position].getCtnrRemarks());




    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_ctnr_ALl_data_view,iv_ctnr_status_modification,iv_ctnr_upload,iv_ctnr_action_delete,iv_ctnr_action_edit;
        public TextView tv_ctnr_ctn,tv_ctnr_month,tv_ctnr_expense_type,tv_ctnr_fromPc_code,tv_ctnr_toPc_code,
                tv_ctnr_transfer_cost,tv_ctnr_remarks;


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


        }
    }
}
