package com.proteam.propcms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Utils.OnClick;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class IrfcListAdapter extends RecyclerView.Adapter<IrfcListAdapter.ViewHolder> {


    private ArrayList<IrfcDataModel> listdata;
    private Context context;
    private OnClick mClick;
    private  Boolean check;
    Map map = new HashMap();
    List list = new ArrayList();

    public IrfcListAdapter(ArrayList<IrfcDataModel> listdata,OnClick listner,Boolean check) {
        this.listdata = listdata;
        this.context = context;
        this.check = check;
        this.mClick = listner;
    }

    @NonNull
    @Override
    public IrfcListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View listItem= layoutInflater.inflate(R.layout.irfc_data_layout, parent, false);
        IrfcListAdapter.ViewHolder viewHolder = new IrfcListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final IrfcDataModel irfcDataModel = listdata.get(position);

        if(check){
            holder.ch_irfc_check_data.setChecked(true);
            map.put(position,listdata.get(position).getId());
            mClick.onClickitem(String.valueOf(position),3,listdata.get(position).getId());
        }else {
            map.remove(position);
            mClick.onClickitem(String.valueOf(position),4,listdata.get(position).getId());
        }

        holder.tv_irfc_pc_code.setText(listdata.get(position).getIrfcPcCode());
        holder.tv_irfc_invoiceNo.setText(listdata.get(position).getIrfcInvoiceNo());
        holder.tv_irfc_InvoiceDate.setText(listdata.get(position).getIrfcInvoiceDate());
        holder.tv_irfc_group.setText(listdata.get(position).getIrfcGroup());
        holder.tv_irfc_assignment.setText(listdata.get(position).getIrfcAssignment());
        holder.tv_irfc_billTo.setText(listdata.get(position).getIrfcBillTo());
        holder.tv_irfc_billingAddress.setText(listdata.get(position).getIrfcBillingAddress());
        holder.tv_irfc_referenceNumber.setText(listdata.get(position).getIrfcReferenceNumber());
        holder.tv_irfc_kindAttention.setText(listdata.get(position).getIrfcKindAttention());
        holder.tv_irfc_region.setText(listdata.get(position).getIrfcRegion());
        holder.tv_irfc_place.setText(listdata.get(position).getIrfcPlace());
        holder.tv_irfc_gstinNo.setText(listdata.get(position).getIrfcGstinNo());
        holder.tv_irfc_panOfCustomer.setText(listdata.get(position).getIrfcPanOfCustomer());

        float amount = Float.parseFloat(listdata.get(position).getIrfcTaxableAmount());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);
        holder.tv_irfc_taxableAmount.setText(moneyString);
        holder.tv_irfc_gstRate.setText(listdata.get(position).getIrfcGstRate());
        holder.tv_irfc_forMonth.setText(listdata.get(position).getIrfcForMonth());
        holder.tv_irfc_description.setText(listdata.get(position).getIrfcDescription());
        holder.tv_irfc_hsn_Sac.setText(listdata.get(position).getIrfcHsnSac());
        holder.tv_irfc_particulars.setText(listdata.get(position).getIrfcParticulars());
        holder.tv_irfc_stateOf_supplyCode.setText(listdata.get(position).getIrfcStateOfSupplyCode());
        holder.tv_irfc_transactionType.setText(listdata.get(position).getIrfcTransactionType());
        holder.tv_irfc_invoiceWith_whom.setText(listdata.get(position).getIrfcInvoiceWithWhom());

        holder.iv_irfc_ALl_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mClick.onClickitem(String.valueOf(position),1,"0");
            }
        });

        holder.iv_irfc_action_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClickitem(String.valueOf(position),2,"0");
            }
        });

        holder.iv_irfc_action_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://pcmsdemo.proteam.co.in//upload/bi_docs/6868e91d7d7f14d22.pdf"));
                context.startActivity(viewIntent);
            }
        });

        holder.ch_irfc_check_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        public ImageView iv_irfc_ALl_data_view,iv_irfc_action_invoice,iv_irfc_action_view,iv_irfc_upload,iv_irfc_status_modification;
        public TextView tv_irfc_pc_code,tv_irfc_invoiceNo,tv_irfc_InvoiceDate,tv_irfc_group,tv_irfc_assignment,
                tv_irfc_billTo,tv_irfc_billingAddress,tv_irfc_referenceNumber,tv_irfc_kindAttention,
                tv_irfc_region,tv_irfc_place,tv_irfc_gstinNo,tv_irfc_panOfCustomer,tv_irfc_taxableAmount,
                tv_irfc_gstRate,tv_irfc_forMonth,tv_irfc_description,tv_irfc_hsn_Sac,tv_irfc_particulars,
                tv_irfc_stateOf_supplyCode,tv_irfc_transactionType,tv_irfc_invoiceWith_whom;
        CheckBox ch_irfc_check_data;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_irfc_ALl_data_view=itemView.findViewById(R.id.iv_irfc_ALl_data_view);
            this.iv_irfc_action_invoice=itemView.findViewById(R.id.iv_irfc_action_invoice);
            this.iv_irfc_action_view=itemView.findViewById(R.id.iv_irfc_action_view);

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
            this.ch_irfc_check_data = itemView.findViewById(R.id.ch_irfc_check_data);

        }
    }
}
