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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.proteam.propcms.Adapters.VerifyCostTransferAdapter;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VerifyCostTransferActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mToolbar;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_vct;
    RecyclerView rv_vct_Data_list;
    EditText edt_from_vct;
    TextView temp_btn_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_cost_transfer);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        initialize();
        sp_all_project_vct.setOnItemSelectedListener(OnCatSpinnerCL);

        VerifyCostTransferModel[] verifyCostTransferModels = new VerifyCostTransferModel[]{
                new VerifyCostTransferModel("CTN/0009/21-22","2021-04","Direct Expenses","SAS-ALB001","SAS-ALB002","20,000.00","Remarks list"),
                new VerifyCostTransferModel("CTN/0009/21-22","2021-04","Direct Expenses","SAS-ALB001","SAS-ALB002","20,000.00","Remarks list"),
                new VerifyCostTransferModel("CTN/0009/21-22","2021-04","Direct Expenses","SAS-ALB001","SAS-ALB002","20,000.00","Remarks list"),


        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_vct_Data_list);
        VerifyCostTransferAdapter adapter = new VerifyCostTransferAdapter(verifyCostTransferModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VerifyCostTransferActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {

        edt_from_vct=findViewById(R.id.edt_from_vct);
        edt_from_vct.setOnClickListener(this);
        rv_vct_Data_list=findViewById(R.id.rv_vct_Data_list);
        sp_all_project_vct=findViewById(R.id.sp_all_project_vct);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.edt_from_vct:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(VerifyCostTransferActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_vct.setText(sdf.format(mcurrentDate.getTime()));
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


        }
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

    private void opengAllDataDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextView tv_dia_vct_ctn = dialog.findViewById(R.id.tv_dia_vct_ctn);
        TextView tv_dia_vct_month = dialog.findViewById(R.id.tv_dia_vct_month);
        TextView tv_dia_vct_directExpense = dialog.findViewById(R.id.tv_dia_vct_directExpense);
        TextView tv_dia_vct_fromPcCode = dialog.findViewById(R.id.tv_dia_vct_fromPcCode);
        TextView tv_dia_vct_TpPcCode = dialog.findViewById(R.id.tv_dia_vct_TpPcCode);
        TextView tv_dia_vct_transferCost = dialog.findViewById(R.id.tv_dia_vct_transferCost);
        TextView tv_dia_vct_Remarks = dialog.findViewById(R.id.tv_dia_vct_Remarks);

        ImageView back_toolbar_vct = dialog.findViewById(R.id.back_toolbar_vct);
        ImageView iv_dia_vct_edit = dialog.findViewById(R.id.iv_dia_vct_edit);
        ImageView iv_dia_vct_delete = dialog.findViewById(R.id.iv_dia_vct_delete);
        ImageView iv_dia_vct_upload = dialog.findViewById(R.id.iv_dia_vct_upload);
        ImageView iv_dia_vct_status = dialog.findViewById(R.id.iv_dia_vct_status);
    }

    private void openEditDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_edit_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextView edt_edit_ctn = dialog.findViewById(R.id.edt_edit_ctn);
        TextView edt_edit_month = dialog.findViewById(R.id.edt_edit_month);
        TextView sp_edit_fromPcCode = dialog.findViewById(R.id.sp_edit_fromPcCode);
        TextView sp_edit_ToPcCode = dialog.findViewById(R.id.sp_edit_ToPcCode);
        TextView sp_edit_expenseType = dialog.findViewById(R.id.sp_edit_expenseType);
        TextView edt_edit_transferCost = dialog.findViewById(R.id.edt_edit_transferCost);
        TextView edt_edit_remarks = dialog.findViewById(R.id.edt_edit_remarks);

    }

}