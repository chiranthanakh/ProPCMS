package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.proteam.propcms.Adapters.VerifyBillingInstructionAdapter;
import com.proteam.propcms.Adapters.VerifyCostTransferAdapter;
import com.proteam.propcms.Models.VerifyBillingInstructionModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VerifyBillingInstructionsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mToolbar;
    Spinner sp_all_project_verify_bi;
    int mMonth,mDay,mYear;
    EditText edt_from_verify_BI;
    RecyclerView rv_verify_BI_Data_list;
    ImageView temp_btn_Bi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_billing_instructions);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());
        initialize();
        sp_all_project_verify_bi.setOnItemSelectedListener(OnCatSpinnerCL);

        VerifyBillingInstructionModel[] verifyBillingInstructionModels = new VerifyBillingInstructionModel[]{
              new VerifyBillingInstructionModel("SAS-ALB001","GE ITC","Commercial Helpdesk","GE India Industrial Private Limited","Plot.No.122, EPIP, Hoodi Village, Whitefield Road, Bangalore - 560 066.","PO - 100040329","Anu Anand","South","Karnataka","29AAACG4901D1Z0","AAACG4901D","50,000.00","18%","2021-02","Towards fees for managing the commercial helpdesk for the month of March 2020","998311","Management Consultancy Services","10","Interstate"),


        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_verify_BI_Data_list);
        VerifyBillingInstructionAdapter adapter = new VerifyBillingInstructionAdapter(verifyBillingInstructionModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VerifyBillingInstructionsActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {

     /*   temp_btn_Bi=findViewById(R.id.temp_btn_Bi);
        temp_btn_Bi.setOnClickListener(this);*/
        rv_verify_BI_Data_list=findViewById(R.id.rv_verify_BI_Data_list);
        edt_from_verify_BI=findViewById(R.id.edt_from_verify_BI);
        edt_from_verify_BI.setOnClickListener(this);
        sp_all_project_verify_bi=findViewById(R.id.sp_all_project_verify_bi);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.edt_from_verify_BI:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(VerifyBillingInstructionsActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_verify_BI.setText(sdf.format(mcurrentDate.getTime()));
                        mDay = dayOfMonth;
                        mMonth = month;
                        mYear = year;

                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DATE)){
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
                    }
                };
                monthDatePickerDialog.setTitle("Select Month And Year");
                monthDatePickerDialog.show();
                break;
     /*       case R.id.temp_btn_Bi:
                openBIallDataDialog();
                break;*/

        }
    }


    private void openBIallDataDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_bi_verification);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView BI_back_toolbar = dialog.findViewById(R.id.BI_back_toolbar);
        ImageView iv_dia_BI_edit = dialog.findViewById(R.id.iv_dia_BI_edit);
        ImageView tv_dia_BI_delete = dialog.findViewById(R.id.tv_dia_BI_delete);
        ImageView iv_dia_BI_upload = dialog.findViewById(R.id.iv_dia_BI_upload);
        ImageView iv_dia_BI_status = dialog.findViewById(R.id.iv_dia_BI_status);

        TextView tv_dia_BI_pcCode = dialog.findViewById(R.id.tv_dia_BI_pcCode);
        TextView tv_dia_BI_group = dialog.findViewById(R.id.tv_dia_BI_group);
        TextView tv_dia_BI_assignment = dialog.findViewById(R.id.tv_dia_BI_assignment);
        TextView tv_dia_BI_billTo = dialog.findViewById(R.id.tv_dia_BI_billTo);
        TextView tv_dia_BI_billingAddress = dialog.findViewById(R.id.tv_dia_BI_billingAddress);
        TextView tv_dia_BI_referenceNum = dialog.findViewById(R.id.tv_dia_BI_referenceNum);
        TextView tv_dia_BI_kindAttention = dialog.findViewById(R.id.tv_dia_BI_kindAttention);
        TextView tv_dia_BI_region = dialog.findViewById(R.id.tv_dia_BI_region);
        TextView tv_dia_BI_place = dialog.findViewById(R.id.tv_dia_BI_place);
        TextView tv_dia_BI_gstinNO = dialog.findViewById(R.id.tv_dia_BI_gstinNO);
        TextView tv_dia_BI_panOfCustomer = dialog.findViewById(R.id.tv_dia_BI_panOfCustomer);
        TextView tv_dia_BI_taxableAmount = dialog.findViewById(R.id.tv_dia_BI_taxableAmount);
        TextView tv_dia_BI_gstRate = dialog.findViewById(R.id.tv_dia_BI_gstRate);
        TextView tv_dia_BI_forMonth = dialog.findViewById(R.id.tv_dia_BI_forMonth);
        TextView tv_dia_BI_description = dialog.findViewById(R.id.tv_dia_BI_description);
        TextView tv_dia_BI_hsnSac = dialog.findViewById(R.id.tv_dia_BI_hsnSac);
        TextView tv_dia_BI_particulars = dialog.findViewById(R.id.tv_dia_BI_particulars);
        TextView tv_dia_BI_stateOfSupply = dialog.findViewById(R.id.tv_dia_BI_stateOfSupply);
        TextView tv_dia_BI_transactionType = dialog.findViewById(R.id.tv_dia_BI_transactionType);

        BI_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        iv_dia_BI_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditBIDialog();
            }
        });


    }

    private void openEditBIDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_bi_edit);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        ImageView BI_edit_back_toolbar =dialog.findViewById(R.id.BI_edit_back_toolbar);
        Spinner sp_Bi_edit_ProjectCode = dialog.findViewById(R.id.sp_Bi_edit_ProjectCode);
        Spinner sp_Bi_edit_division = dialog.findViewById(R.id.sp_Bi_edit_division);
        Spinner sp_Bi_edit_region = dialog.findViewById(R.id.sp_Bi_edit_region);

        EditText ed_Bi_edit_invoiceDate = dialog.findViewById(R.id.ed_Bi_edit_invoiceDate);
        EditText ed_Bi_edit_group = dialog.findViewById(R.id.ed_Bi_edit_group);
        EditText ed_Bi_edit_billTo = dialog.findViewById(R.id.ed_Bi_edit_billTo);
        EditText ed_Bi_edit_assignment = dialog.findViewById(R.id.ed_Bi_edit_assignment);
        EditText ed_Bi_edit_referenceNo = dialog.findViewById(R.id.ed_Bi_edit_referenceNo);
        EditText ed_Bi_edit_kindAttention = dialog.findViewById(R.id.ed_Bi_edit_kindAttention);
        EditText ed_Bi_edit_gstinNo = dialog.findViewById(R.id.ed_Bi_edit_gstinNo);
        EditText ed_Bi_edit_panOfCustomer = dialog.findViewById(R.id.ed_Bi_edit_panOfCustomer);
        EditText ed_Bi_edit_place = dialog.findViewById(R.id.ed_Bi_edit_place);
        EditText ed_Bi_edit_forMonth = dialog.findViewById(R.id.ed_Bi_edit_forMonth);
        EditText ed_Bi_edit_supplyCode = dialog.findViewById(R.id.ed_Bi_edit_supplyCode);
        EditText ed_Bi_edit_transactionType = dialog.findViewById(R.id.ed_Bi_edit_transactionType);
        EditText ed_Bi_edit_amount = dialog.findViewById(R.id.ed_Bi_edit_amount);
        EditText ed_Bi_edit_gstPercentage = dialog.findViewById(R.id.ed_Bi_edit_gstPercentage);
        EditText ed_Bi_edit_hsnSac = dialog.findViewById(R.id.ed_Bi_edit_hsnSac);
        EditText ed_Bi_edit_particulars = dialog.findViewById(R.id.ed_Bi_edit_particulars);
        EditText ed_Bi_edit_billingAddress = dialog.findViewById(R.id.ed_Bi_edit_billingAddress);
        EditText ed_Bi_edit_description = dialog.findViewById(R.id.ed_Bi_edit_description);

        BI_edit_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);
        }
    };


}