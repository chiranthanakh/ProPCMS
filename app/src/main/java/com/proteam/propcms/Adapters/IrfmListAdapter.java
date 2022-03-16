package com.proteam.propcms.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.R;

import java.util.ArrayList;

public class IrfmListAdapter extends RecyclerView.Adapter<IrfmListAdapter.ViewHolder> {

    private IrfmDataModel[] listdata;
    private Context context;

    public IrfmListAdapter(IrfmDataModel[] listdata) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.irfm_data_layout, parent, false);
        IrfmListAdapter.ViewHolder viewHolder = new IrfmListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final IrfmDataModel irfmDataModel = listdata[position];
        holder.iv_irfm_ALl_data_view.setImageResource(listdata[position].getIrfmAlldata());
        holder.iv_irfm_action_invoice.setImageResource(listdata[position].getIrfmImageActionInvoice());
        holder.iv_irfm_action_view.setImageResource(listdata[position].getIrfmImageActionView());
        holder.iv_irfm_upload.setImageResource(listdata[position].getIrfmImageUpload());
        holder.iv_irfm_status_modification.setImageResource(listdata[position].getIrfmImageStatusModification());

        holder.tv_irfm_pc_code.setText(listdata[position].getIrfmPcCode());

        holder.tv_irfm_invoice_no.setText(listdata[position].getIrfmInvoiceNo());
        holder.tv_irfm_process_owner.setText(listdata[position].getIrfmProcessOwner());
        holder.tv_irfm_requestFor_change.setText(listdata[position].getIrfmRequestForChange());
        holder.tv_irfm_group.setText(listdata[position].getIrfmGroup());
        holder.tv_irfm_assignment.setText(listdata[position].getIrfmAssignment());
        holder.tv_irfm_region.setText(listdata[position].getIrfmRegion());
        holder.tv_irfm_place.setText(listdata[position].getIrfmPlace());
        holder.tv_irfm_gstin_no.setText(listdata[position].getIrfmGstinNo());
        holder.tv_irfm_panOf_customer.setText(listdata[position].getIrfmPanOfCustomer());
        holder.tv_irfm_taxable_amount.setText(listdata[position].getIrfmTaxableAmount());
        holder.tv_irfm_gst_rate.setText(listdata[position].getIrfmGstRate());
        holder.tv_irfm_forMonth.setText(listdata[position].getIrfmForMonth());
        holder.tv_irfm_description.setText(listdata[position].getIrfmDescription());

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_irfm_ALl_data_view,iv_irfm_action_invoice,iv_irfm_action_view,iv_irfm_upload,iv_irfm_status_modification;
        public TextView tv_irfm_pc_code,tv_irfm_invoice_no,tv_irfm_process_owner,tv_irfm_requestFor_change,tv_irfm_group,
        tv_irfm_assignment,tv_irfm_region,tv_irfm_place,tv_irfm_gstin_no,tv_irfm_panOf_customer,tv_irfm_taxable_amount,tv_irfm_gst_rate,
                tv_irfm_forMonth,tv_irfm_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_irfm_ALl_data_view = (ImageView) itemView.findViewById(R.id.iv_irfm_ALl_data_view);
            this.iv_irfm_action_invoice = (ImageView) itemView.findViewById(R.id.iv_irfm_action_invoice);
            this.iv_irfm_action_view = (ImageView) itemView.findViewById(R.id.iv_irfm_action_view);
            this.iv_irfm_upload = (ImageView) itemView.findViewById(R.id.iv_irfm_upload);
            this.iv_irfm_status_modification = (ImageView) itemView.findViewById(R.id.iv_irfm_status_modification);

            this.tv_irfm_pc_code = (TextView) itemView.findViewById(R.id.tv_irfm_pc_code);
            this.tv_irfm_invoice_no = (TextView) itemView.findViewById(R.id.tv_irfm_invoice_no);
            this.tv_irfm_process_owner = (TextView) itemView.findViewById(R.id.tv_irfm_process_owner);
            this.tv_irfm_requestFor_change = (TextView) itemView.findViewById(R.id.tv_irfm_requestFor_change);
            this.tv_irfm_group = (TextView) itemView.findViewById(R.id.tv_irfm_group);
            this.tv_irfm_assignment = (TextView) itemView.findViewById(R.id.tv_irfm_assignment);
            this.tv_irfm_region = (TextView) itemView.findViewById(R.id.tv_irfm_region);
            this.tv_irfm_place = (TextView) itemView.findViewById(R.id.tv_irfm_place);
            this.tv_irfm_gstin_no = (TextView) itemView.findViewById(R.id.tv_irfm_gstin_no);
            this.tv_irfm_panOf_customer = (TextView) itemView.findViewById(R.id.tv_irfm_panOf_customer);
            this.tv_irfm_taxable_amount = (TextView) itemView.findViewById(R.id.tv_irfm_taxable_amount);
            this.tv_irfm_gst_rate = (TextView) itemView.findViewById(R.id.tv_irfm_gst_rate);
            this.tv_irfm_forMonth = (TextView) itemView.findViewById(R.id.tv_irfm_forMonth);
            this.tv_irfm_description = (TextView) itemView.findViewById(R.id.tv_irfm_description);

        }
    }
}
