package com.proteam.propcms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.R;

import java.util.ArrayList;

public class IrfcListAdapter extends RecyclerView.Adapter<IrfcListAdapter.ViewHolder> {

    private IrfcDataModel[] listdata;
    private Context context;

    public IrfcListAdapter(IrfcDataModel[] listdata) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public IrfcListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.irfc_data_layout, parent, false);
        IrfcListAdapter.ViewHolder viewHolder = new IrfcListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final IrfcDataModel irfcDataModel = listdata[position];
        holder.iv_irfc_ALl_data_view.setImageResource(listdata[position].getIrfcAlldata());
        holder.iv_irfc_action_invoice.setImageResource(listdata[position].getIrfcImageActionInvoice());
        holder.iv_irfc_action_view.setImageResource(listdata[position].getIrfcImageActionView());
        holder.iv_irfc_upload.setImageResource(listdata[position].getIrfcImageUpload());
        holder.iv_irfc_status_modification.setImageResource(listdata[position].getIrfcImageStatusModification());

        holder.tv_irfc_pc_code.setText(listdata[position].getIrfcPcCode());
        holder.tv_irfc_invoiceNo.setText(listdata[position].getIrfcInvoiceNo());
        holder.tv_irfc_InvoiceDate.setText(listdata[position].getIrfcInvoiceDate());
        holder.tv_irfc_group.setText(listdata[position].getIrfcGroup());
        holder.tv_irfc_assignment.setText(listdata[position].getIrfcAssignment());
        holder.tv_irfc_billTo.setText(listdata[position].getIrfcBillTo());
        holder.tv_irfc_billingAddress.setText(listdata[position].getIrfcBillingAddress());
        holder.tv_irfc_referenceNumber.setText(listdata[position].getIrfcReferenceNumber());
        holder.tv_irfc_kindAttention.setText(listdata[position].getIrfcKindAttention());
        holder.tv_irfc_region.setText(listdata[position].getIrfcRegion());
        holder.tv_irfc_place.setText(listdata[position].getIrfcPlace());
        holder.tv_irfc_gstinNo.setText(listdata[position].getIrfcGstinNo());
        holder.tv_irfc_panOfCustomer.setText(listdata[position].getIrfcPanOfCustomer());
        holder.tv_irfc_taxableAmount.setText(listdata[position].getIrfcTaxableAmount());
        holder.tv_irfc_gstRate.setText(listdata[position].getIrfcGstRate());
        holder.tv_irfc_forMonth.setText(listdata[position].getIrfcForMonth());
        holder.tv_irfc_description.setText(listdata[position].getIrfcDescription());
        holder.tv_irfc_hsn_Sac.setText(listdata[position].getIrfcHsnSac());
        holder.tv_irfc_particulars.setText(listdata[position].getIrfcParticulars());
        holder.tv_irfc_stateOf_supplyCode.setText(listdata[position].getIrfcStateOfSupplyCode());
        holder.tv_irfc_transactionType.setText(listdata[position].getIrfcTransactionType());
        holder.tv_irfc_invoiceWith_whom.setText(listdata[position].getIrfcInvoiceWithWhom());
    }



    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_irfc_ALl_data_view,iv_irfc_action_invoice,iv_irfc_action_view,iv_irfc_upload,iv_irfc_status_modification;
        public TextView tv_irfc_pc_code,tv_irfc_invoiceNo,tv_irfc_InvoiceDate,tv_irfc_group,tv_irfc_assignment,
                tv_irfc_billTo,tv_irfc_billingAddress,tv_irfc_referenceNumber,tv_irfc_kindAttention,
                tv_irfc_region,tv_irfc_place,tv_irfc_gstinNo,tv_irfc_panOfCustomer,tv_irfc_taxableAmount,
                tv_irfc_gstRate,tv_irfc_forMonth,tv_irfc_description,tv_irfc_hsn_Sac,tv_irfc_particulars,
                tv_irfc_stateOf_supplyCode,tv_irfc_transactionType,tv_irfc_invoiceWith_whom;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_irfc_ALl_data_view = (ImageView) itemView.findViewById(R.id.iv_irfc_ALl_data_view);
            this.iv_irfc_action_invoice = (ImageView) itemView.findViewById(R.id.iv_irfc_action_invoice);
            this.iv_irfc_action_view = (ImageView) itemView.findViewById(R.id.iv_irfc_action_view);
            this.iv_irfc_upload = (ImageView) itemView.findViewById(R.id.iv_irfc_upload);
            this.iv_irfc_status_modification = (ImageView) itemView.findViewById(R.id.iv_irfc_status_modification);

            this.tv_irfc_pc_code = (TextView) itemView.findViewById(R.id.tv_irfc_pc_code);

            this.tv_irfc_invoiceNo = (TextView) itemView.findViewById(R.id.tv_irfc_invoiceNo);
            this.tv_irfc_InvoiceDate = (TextView) itemView.findViewById(R.id.tv_irfc_InvoiceDate);
            this.tv_irfc_group = (TextView) itemView.findViewById(R.id.tv_irfc_group);
            this.tv_irfc_assignment = (TextView) itemView.findViewById(R.id.tv_irfc_assignment);
            this.tv_irfc_billTo = (TextView) itemView.findViewById(R.id.tv_irfc_billTo);
            this.tv_irfc_billingAddress = (TextView) itemView.findViewById(R.id.tv_irfc_billingAddress);
            this.tv_irfc_referenceNumber = (TextView) itemView.findViewById(R.id.tv_irfc_referenceNumber);
            this.tv_irfc_kindAttention = (TextView) itemView.findViewById(R.id.tv_irfc_kindAttention);
            this.tv_irfc_region = (TextView) itemView.findViewById(R.id.tv_irfc_region);
            this.tv_irfc_place = (TextView) itemView.findViewById(R.id.tv_irfc_place);
            this.tv_irfc_gstinNo = (TextView) itemView.findViewById(R.id.tv_irfc_gstinNo);
            this.tv_irfc_panOfCustomer = (TextView) itemView.findViewById(R.id.tv_irfc_panOfCustomer);
            this.tv_irfc_taxableAmount = (TextView) itemView.findViewById(R.id.tv_irfc_taxableAmount);
            this.tv_irfc_gstRate = (TextView) itemView.findViewById(R.id.tv_irfc_gstRate);
            this.tv_irfc_forMonth = (TextView) itemView.findViewById(R.id.tv_irfc_forMonth);
            this.tv_irfc_description = (TextView) itemView.findViewById(R.id.tv_irfc_description);
            this.tv_irfc_hsn_Sac = (TextView) itemView.findViewById(R.id.tv_irfc_hsn_Sac);
            this.tv_irfc_particulars = (TextView) itemView.findViewById(R.id.tv_irfc_particulars);
            this.tv_irfc_stateOf_supplyCode = (TextView) itemView.findViewById(R.id.tv_irfc_stateOf_supplyCode);
            this.tv_irfc_transactionType = (TextView) itemView.findViewById(R.id.tv_irfc_transactionType);
            this.tv_irfc_invoiceWith_whom = (TextView) itemView.findViewById(R.id.tv_irfc_invoiceWith_whom);

        }
    }
}
