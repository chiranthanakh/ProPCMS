package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proteam.propcms.R;
import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener {

    Button btn_continue;
    EditText edt_email,edt_pass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize()
    {
        btn_continue=findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);
        edt_email = findViewById(R.id.edt_Email);
        edt_pass = findViewById(R.id.edt_Pass);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_continue:

                if(!edt_email.getText().toString().equals("")){

                    if (!edt_pass.getText().toString().equals("")){

                        callLoginapi();

                    }else {
                        Toast.makeText(LoginActivity.this,"Enter your password",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"Enter your email-id",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void callLoginapi() {

            progressDialog=new ProgressDialog(LoginActivity.this);
            if(progressDialog!=null)
            {
                if(!progressDialog.isShowing())
                {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    Loginmodel loginmodel = new Loginmodel(edt_email.getText().toString(),edt_pass.getText().toString());

                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(LoginActivity.this);
                    webServices.login( WebServices.ApiType.login,loginmodel );
                }
                else {

                }
            }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {
        switch (URL) {
            case login:

                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    LoginResponse loginResponse = (LoginResponse) response;

                    if(loginResponse.getStatus().equals("true")){


                        SharedPreferences prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("userid",loginResponse.getUser_id());
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(this, "Please enter correct login details", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }

                break;

        }
    }
}