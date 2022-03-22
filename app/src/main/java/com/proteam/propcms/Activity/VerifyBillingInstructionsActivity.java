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
    TextView temp_btn_Bi;
    RecyclerView rv_verify_BI_Data_list;
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
        temp_btn_Bi=findViewById(R.id.temp_btn_Bi);
        temp_btn_Bi.setOnClickListener(this);
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
            case R.id.temp_btn_Bi:
                opengcadminDialog();
                break;
        }
    }


    private void opengcadminDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_bi_verification);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();





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