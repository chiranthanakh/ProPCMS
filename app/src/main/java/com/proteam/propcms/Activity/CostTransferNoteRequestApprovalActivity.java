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

import com.proteam.propcms.Adapters.CtnrListAdapter;
import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CostTransferNoteRequestApprovalActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mToolbar;
    EditText edt_from;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_ctnra;
    RecyclerView rv_ctrn_Data_list;
    TextView temp_btn_ctnr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_transfer_note_request_approval);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());


        initialize();
        sp_all_project_ctnra.setOnItemSelectedListener(OnCatSpinnerCL);


        CtrnDataModel[] ctrnDataModels = new CtrnDataModel[]{
                new CtrnDataModel("CTN/0009/21-22","2021-04","Direct Expenses","SAS-ALB001","SAS-ALB002","20,000.00","Remarks list"),
                new CtrnDataModel("CTN/0009/21-22","2021-04","Direct Expenses","SAS-ALB001","SAS-ALB002","20,000.00","Remarks list"),
                new CtrnDataModel("CTN/0009/21-22","2021-04","Direct Expenses","SAS-ALB001","SAS-ALB002","20,000.00","Remarks list"),


        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ctrn_Data_list);
        CtnrListAdapter adapter = new CtnrListAdapter(ctrnDataModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CostTransferNoteRequestApprovalActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        temp_btn_ctnr=findViewById(R.id.temp_btn_ctnr);
        temp_btn_ctnr.setOnClickListener(this);
        edt_from=findViewById(R.id.edt_from);
        edt_from.setOnClickListener(this);
        sp_all_project_ctnra=findViewById(R.id.sp_all_project_ctnra);
        rv_ctrn_Data_list=findViewById(R.id.rv_ctrn_Data_list);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.temp_btn_ctnr:
                opengcadminDialog();
                break;
            case R.id.edt_from:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(CostTransferNoteRequestApprovalActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from.setText(sdf.format(mcurrentDate.getTime()));
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

        }
    }
    private void opengcadminDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_ctnr_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView iv_dia_edit = dialog.findViewById(R.id.iv_dia_edit);
        ImageView iv_dia_delete = dialog.findViewById(R.id.iv_dia_delete);
        ImageView iv_dia_upload = dialog.findViewById(R.id.iv_dia_upload);
        ImageView iv_dia_status = dialog.findViewById(R.id.iv_dia_status);

        TextView tv_dia_ctn = dialog.findViewById(R.id.tv_dia_ctn);
        TextView tv_dia_month = dialog.findViewById(R.id.tv_dia_month);
        TextView tv_dia_directExpense = dialog.findViewById(R.id.tv_dia_directExpense);
        TextView tv_dia_fromPcCode = dialog.findViewById(R.id.tv_dia_fromPcCode);
        TextView tv_dia_TpPcCode = dialog.findViewById(R.id.tv_dia_TpPcCode);
        TextView tv_dia_transferCost = dialog.findViewById(R.id.tv_dia_transferCost);
        TextView tv_dia_Remarks = dialog.findViewById(R.id.tv_dia_Remarks);
        Button btn_dia_approve = dialog.findViewById(R.id.btn_dia_approve);
        Button btn_dia_reject = dialog.findViewById(R.id.btn_dia_reject);



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