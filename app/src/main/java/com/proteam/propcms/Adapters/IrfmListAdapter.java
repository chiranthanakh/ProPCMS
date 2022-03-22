package com.proteam.propcms.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Utils.OnClick;

import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class IrfmListAdapter extends RecyclerView.Adapter<IrfmListAdapter.ViewHolder>  {

   // private IrfmDataModel[] listdata;
    private Context context;
    private ArrayList<IrfmDataModel> listdata;
    Boolean check;
    private OnClick mClick;
    Map map = new HashMap();
    List list = new ArrayList();

    public IrfmListAdapter(ArrayList<IrfmDataModel> listdata,OnClick listner,Boolean check) {
        this.mClick = listner;
        this.check= check;
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.irfm_data_layout, parent, false);
        IrfmListAdapter.ViewHolder viewHolder = new IrfmListAdapter.ViewHolder(listItem);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final IrfmDataModel irfmDataModel = listdata.get(position);
//        holder.iv_irfm_ALl_data_view.setImageResource(listdata[position].getIrfmAlldata());
//        holder.iv_irfm_action_invoice.setImageResource(listdata[position].getIrfmImageActionInvoice());
//        holder.iv_irfm_action_view.setImageResource(listdata[position].getIrfmImageActionView());
//        holder.iv_irfm_upload.setImageResource(listdata[position].getIrfmImageUpload());
//        holder.iv_irfm_status_modification.setImageResource(listdata[position].getIrfmImageStatusModification());

        holder.tv_irfm_pc_code.setText(listdata.get(position).getIrfmPcCode());

        holder.tv_irfm_invoice_no.setText(listdata.get(position).getIrfmInvoiceNo());
        holder.tv_irfm_process_owner.setText( StringUtils.capitalize(listdata.get(position).getProcessowner().toLowerCase().trim()));
        holder.tv_irfm_requestFor_change.setText(listdata.get(position).getIrfmRequestForChange());
        holder.tv_irfm_group.setText(listdata.get(position).getIrfmGroup());
        holder.tv_irfm_assignment.setText(listdata.get(position).getIrfmAssignment());
        holder.tv_irfm_region.setText(listdata.get(position).getIrfmRegion());
        holder.tv_irfm_place.setText(listdata.get(position).getIrfmPlace());
        holder.tv_irfm_gstin_no.setText(listdata.get(position).getIrfmGstinNo());
        holder.tv_irfm_panOf_customer.setText(listdata.get(position).getIrfmPanOfCustomer());
        holder.tv_irfm_gst_rate.setText(listdata.get(position).getIrfmGstRate()+"%");
        holder.tv_irfm_forMonth.setText(listdata.get(position).getIrfmForMonth());
        holder.tv_irfm_description.setText(listdata.get(position).getIrfmDescription());

        float amount = Float.parseFloat(listdata.get(position).getIrfmTaxableAmount());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);
        holder.tv_irfm_taxable_amount.setText(moneyString);

        if(check){
            holder.ch_irfm_check_data.setChecked(true);
        }else {
            holder.ch_irfm_check_data.setChecked(false);
        }

        holder.iv_irfm_ALl_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClickitem(String.valueOf(position),1,"0");
            }
        });

        holder.iv_irfm_action_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mClick.onClickitem(String.valueOf(position),2,"0");
            }
        });
        holder.iv_irfm_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://pcmsdemo.proteam.co.in//upload/bi_docs/6868e91d7d7f14d22.pdf"));
                               context.startActivity(viewIntent);
            }
        });

        holder.ch_irfm_check_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        public ImageView iv_irfm_ALl_data_view,iv_irfm_action_invoice,iv_irfm_action_view,iv_irfm_upload,iv_irfm_status_modification;
        public TextView tv_irfm_pc_code,tv_irfm_invoice_no,tv_irfm_process_owner,tv_irfm_requestFor_change,tv_irfm_group,
        tv_irfm_assignment,tv_irfm_region,tv_irfm_place,tv_irfm_gstin_no,tv_irfm_panOf_customer,tv_irfm_taxable_amount,tv_irfm_gst_rate,
                tv_irfm_forMonth,tv_irfm_description;
        CheckBox ch_irfm_check_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_irfm_ALl_data_view = (ImageView) itemView.findViewById(R.id.iv_irfm_ALl_data_view);
           // this.iv_irfm_action_invoice = (ImageView) itemView.findViewById(R.id.iv_irfm_action_invoice);
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
            this.ch_irfm_check_data = (CheckBox) itemView.findViewById(R.id.ch_irfm_check_data);


        }
    }
}
