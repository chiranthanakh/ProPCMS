package com.proteam.propcms.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.proteam.propcms.Adapters.CtnrListAdapter;
import com.proteam.propcms.Adapters.IrfcListAdapter;
import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InvoiceRequestForCancellationsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mToolbar;
    EditText edt_from_irfc;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_irfc;
    TextView temp_btn_irfc;
    RecyclerView rv_irfc_Data_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_request_for_cancellations);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        initialize();
        sp_all_project_irfc.setOnItemSelectedListener(OnCatSpinnerCL);

        IrfcDataModel[] irfcDataModels = new IrfcDataModel[]{
                new IrfcDataModel("CTN/0009/21-22","PTSB/1701/21-22","14-03-2022","Brigade","Fixed Assets Verification","Pro-Team Solutions","Bangalore","6763783gd7","Kind Attention","South","Bangalore","29AADCH8879C1Z5","AADCH8879C","1,25,040.00","18%","2021-10","Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021","Hsn/Sac","Particulars","State Of Supply Code","Transaction Type","Invoice With Whom"),
                new IrfcDataModel("CTN/0009/21-22","PTSB/1701/21-22","14-03-2022","Brigade","Fixed Assets Verification","Pro-Team Solutions","Bangalore","6763783gd7","Kind Attention","South","Bangalore","29AADCH8879C1Z5","AADCH8879C","1,25,040.00","18%","2021-10","Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021","Hsn/Sac","Particulars","State Of Supply Code","Transaction Type","Invoice With Whom"),
                new IrfcDataModel("CTN/0009/21-22","PTSB/1701/21-22","14-03-2022","Brigade","Fixed Assets Verification","Pro-Team Solutions","Bangalore","6763783gd7","Kind Attention","South","Bangalore","29AADCH8879C1Z5","AADCH8879C","1,25,040.00","18%","2021-10","Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021","Hsn/Sac","Particulars","State Of Supply Code","Transaction Type","Invoice With Whom"),
                new IrfcDataModel("CTN/0009/21-22","PTSB/1701/21-22","14-03-2022","Brigade","Fixed Assets Verification","Pro-Team Solutions","Bangalore","6763783gd7","Kind Attention","South","Bangalore","29AADCH8879C1Z5","AADCH8879C","1,25,040.00","18%","2021-10","Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021","Hsn/Sac","Particulars","State Of Supply Code","Transaction Type","Invoice With Whom"),
                new IrfcDataModel("CTN/0009/21-22","PTSB/1701/21-22","14-03-2022","Brigade","Fixed Assets Verification","Pro-Team Solutions","Bangalore","6763783gd7","Kind Attention","South","Bangalore","29AADCH8879C1Z5","AADCH8879C","1,25,040.00","18%","2021-10","Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021","Hsn/Sac","Particulars","State Of Supply Code","Transaction Type","Invoice With Whom"),
                new IrfcDataModel("CTN/0009/21-22","PTSB/1701/21-22","14-03-2022","Brigade","Fixed Assets Verification","Pro-Team Solutions","Bangalore","6763783gd7","Kind Attention","South","Bangalore","29AADCH8879C1Z5","AADCH8879C","1,25,040.00","18%","2021-10","Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021","Hsn/Sac","Particulars","State Of Supply Code","Transaction Type","Invoice With Whom"),



        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
        IrfcListAdapter adapter = new IrfcListAdapter(irfcDataModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InvoiceRequestForCancellationsActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        rv_irfc_Data_list=findViewById(R.id.rv_irfc_Data_list);
        temp_btn_irfc=findViewById(R.id.temp_btn_irfc);
        temp_btn_irfc.setOnClickListener(this);
        edt_from_irfc=findViewById(R.id.edt_from_irfc);
        edt_from_irfc.setOnClickListener(this);
        sp_all_project_irfc=findViewById(R.id.sp_all_project_irfc);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.edt_from_irfc:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(InvoiceRequestForCancellationsActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_irfc.setText(sdf.format(mcurrentDate.getTime()));
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
                // monthDatePickerDialog.getDatePicker().setMaxDate(mcurrentDate.getTimeInMillis());
                // monthDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                break;
            case R.id.temp_btn_irfc:
                opengcadminDialog();
                break;
        }
    }
    private void opengcadminDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_irfc_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView ic_d_irfc_viewInvoice = dialog.findViewById(R.id.ic_d_irfc_viewInvoice);
        ImageView tv_d_irfc_requestDetails = dialog.findViewById(R.id.tv_d_irfc_requestDetails);
        ImageView iv_d_irfc_upload = dialog.findViewById(R.id.iv_d_irfc_upload);
        ImageView iv_d_irfc_status = dialog.findViewById(R.id.iv_d_irfc_status);

        TextView tv_d_irfc_pcCode = dialog.findViewById(R.id.tv_d_irfc_pcCode);
        TextView tv_d_irfc_InvoiceNo = dialog.findViewById(R.id.tv_d_irfc_InvoiceNo);
        TextView tv_d_irfc_InvoiceDate = dialog.findViewById(R.id.tv_d_irfc_InvoiceDate);
        TextView tv_d_irfc_group = dialog.findViewById(R.id.tv_d_irfc_group);
        TextView tv_d_irfc_assignment = dialog.findViewById(R.id.tv_d_irfc_assignment);
        TextView tv_d_irfc_billTo = dialog.findViewById(R.id.tv_d_irfc_billTo);
        TextView tv_d_irfc_BillingAddress = dialog.findViewById(R.id.tv_d_irfc_BillingAddress);
        TextView tv_d_irfc_referenceNo = dialog.findViewById(R.id.tv_d_irfc_referenceNo);
        TextView tv_d_irfc_kindAttention = dialog.findViewById(R.id.tv_d_irfc_kindAttention);
        TextView tv_d_irfc_region = dialog.findViewById(R.id.tv_d_irfc_region);
        TextView tv_d_irfc_place = dialog.findViewById(R.id.tv_d_irfc_place);
        TextView tv_d_irfc_gstNo = dialog.findViewById(R.id.tv_d_irfc_gstNo);
        TextView tv_d_irfc_panOfCustomer = dialog.findViewById(R.id.tv_d_irfc_panOfCustomer);
        TextView tv_d_irfc_taxableAmount = dialog.findViewById(R.id.tv_d_irfc_taxableAmount);
        TextView tv_d_irfc_gstRate = dialog.findViewById(R.id.tv_d_irfc_gstRate);
        TextView tv_d_irfc_forMonth = dialog.findViewById(R.id.tv_d_irfc_forMonth);
        TextView tv_d_irfc_description = dialog.findViewById(R.id.tv_d_irfc_description);
        TextView tv_d_irfc_hsn_sac = dialog.findViewById(R.id.tv_d_irfc_hsn_sac);
        TextView tv_d_irfc_Particular = dialog.findViewById(R.id.tv_d_irfc_Particular);
        TextView tv_d_irfc_stateOfSupply = dialog.findViewById(R.id.tv_d_irfc_stateOfSupply);
        TextView tv_d_irfc_transactionType = dialog.findViewById(R.id.tv_d_irfc_transactionType);
        TextView tv_d_irfc_InvoiceWithWhom = dialog.findViewById(R.id.tv_d_irfc_InvoiceWithWhom);

        Button btn_d_irfc_ctn_approve = dialog.findViewById(R.id.btn_d_irfc_ctn_approve);
        Button btn_d_irfc_ctn_reject = dialog.findViewById(R.id.btn_d_irfc_ctn_reject);



    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(14);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(14);
        }
    };
}