package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.propcms.Adapters.IrfcListAdapter;
import com.proteam.propcms.Adapters.IrfmListAdapter;
import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvoiceRequestForModificationsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener {
    ImageView mToolbar;
    EditText edt_from_irfm;
    int mMonth, mDay, mYear;
    Spinner sp_all_project_irfm;
    TextView temp_btn;
    RecyclerView rv_irfm_Data_list;

    List projectList = new ArrayList();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_request_for_modifications);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        initialize();
        sp_all_project_irfm.setOnItemSelectedListener(OnCatSpinnerCL);


        IrfmDataModel[] irfmDataModels = new IrfmDataModel[]{
                new IrfmDataModel(R.drawable.eye, R.drawable.ic_invoice, R.drawable.ic_send, R.drawable.ic_upload, R.drawable.modification, "FAS-UDN001", "PTSB/1701/21-22", "Sunil m das", "Need the Invoice date to be modified to 17th November 2021", "Udaan", "Fixed Assets Verification", "South", "Bangalore", "29AADCH8879C1Z5", "AADCH8879C", "75,000.00", "18%", "2021-10", "Professional Charges towards Fixed Assets Reco Support Services for the month of October 2021"),
                new IrfmDataModel(R.drawable.eye, R.drawable.ic_invoice, R.drawable.ic_send, R.drawable.ic_upload, R.drawable.modification, "FAS-BEL001", "PTSB/1700/21-22", "Sunil m das", "This invoice needs to be split into 3 invoices as per below calculation Invoice 1 - Rs. 55000 + GST ", "Brigade", "Fixed Assets Verification", "South", "Bangalore", "29AAACB7459F1ZI", "AAACB7459F", "1,25,040.00", "18%", "2021-10", "Professional Charges towards Fixed Assets Verification conducted at Brigade Utopia sites during the month of October 2021"),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfm_Data_list);
        IrfmListAdapter adapter = new IrfmListAdapter(irfmDataModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }


    private void callProjectListApi() {

        progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                ProjectListModel projectListModel = new ProjectListModel("21");
                WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(InvoiceRequestForModificationsActivity.this);
                webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InvoiceRequestForModificationsActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize() {
        rv_irfm_Data_list = findViewById(R.id.rv_irfm_Data_list);
        temp_btn = findViewById(R.id.temp_btn);
        temp_btn.setOnClickListener(this);
        edt_from_irfm = findViewById(R.id.edt_from_irfm);
        edt_from_irfm.setOnClickListener(this);
        sp_all_project_irfm = findViewById(R.id.sp_all_project_irfm);
        callProjectListApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_from_irfm:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(InvoiceRequestForModificationsActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year);
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_irfm.setText(sdf.format(mcurrentDate.getTime()));
                        mDay = dayOfMonth;
                        mMonth = month;
                        mYear = year;

                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DATE)) {
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                    }
                };
                monthDatePickerDialog.setTitle("Select Month And Year");
                monthDatePickerDialog.show();
                // monthDatePickerDialog.getDatePicker().setMaxDate(mcurrentDate.getTimeInMillis());
                // monthDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                break;

            case R.id.temp_btn:
                opengcadminDialog();
                break;
        }
    }


    private void opengcadminDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_irfm_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {

        switch (URL) {

            case projectlist:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {

                        List list = new ArrayList();
                        ProjectListResponse projectListResponse = (ProjectListResponse) response;

                        list = projectListResponse.getProject_list();

                        for (int i = 0; i < list.size(); i++) {

                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name());
                        }


                        ArrayAdapter adapter = new ArrayAdapter(InvoiceRequestForModificationsActivity.this, android.R.layout.simple_list_item_1, projectList);
                        sp_all_project_irfm.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;
        }
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