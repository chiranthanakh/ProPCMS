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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.propcms.R;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProfileResponse;
import com.proteam.propcms.Request.Updateuserrequest;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

public class ProfileActivity extends AppCompatActivity implements OnResponseListener {
    ImageView mToolbar;
    EditText edt_conform_pass,edt_password,edt_phone,edt_email_profile,edt_second_name,edt_first_name;
    TextView tv_name;
    Button btn_profile_submit;
    ProgressDialog progressDialog;
    String user;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = sharedPreferences.getString("userid", null);

        initilization();
        callprofileApi();

    }

    private void initilization() {

        edt_conform_pass = findViewById(R.id.edt_conform_pass);
        edt_password = findViewById(R.id.edt_password);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email_profile = findViewById(R.id.edt_email_profile);
        edt_second_name = findViewById(R.id.edt_second_name);
        edt_first_name = findViewById(R.id.edt_first_name);
        tv_name = findViewById(R.id.tv_nav_name);
        btn_profile_submit= findViewById(R.id.btn_profile_submit);
        btn_profile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });
    }

    private void callprofileApi() {

        progressDialog=new ProgressDialog(ProfileActivity.this);
        if(progressDialog!=null)
        {
            if(!progressDialog.isShowing())
            {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                UserIdRequest userIdRequest = new UserIdRequest(user);
                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(ProfileActivity.this);
                webServices.profileapi( WebServices.ApiType.profile,userIdRequest );
            }
            else {

            }
        }

    }

    private void updateDetails() {

        if (edt_phone.getText().toString().trim().equals("") || edt_email_profile.getText().toString().trim().equals("") ||
                edt_first_name.getText().toString().trim().equals("") || edt_password.getText().toString().trim().equals("")
                || edt_conform_pass.getText().toString().trim().equals("")) {

            Toast.makeText(this,"please enter all fields", Toast.LENGTH_SHORT).show();

        } else {
            if(edt_conform_pass.getText().toString().trim().equalsIgnoreCase(edt_password.getText().toString().trim())){

                callupdateprofileapi();

            }else {
                Toast.makeText(this,"Password is not matching", Toast.LENGTH_SHORT).show();
            }

    }
    }

    private void callupdateprofileapi() {

        progressDialog = new ProgressDialog(ProfileActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                Updateuserrequest updateuserrequest = new Updateuserrequest(user,edt_email_profile.getText().toString(),edt_password.getText().toString(),
                        edt_conform_pass.getText().toString(),edt_first_name.getText().toString(),edt_second_name.getText().toString(),edt_phone.getText().toString());
                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(ProfileActivity.this);
                webServices.profileupdateapi(WebServices.ApiType.profileupdate, updateuserrequest);
            } else {

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {
        switch (URL) {
            case profile:

                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    ProfileResponse profileResponse = (ProfileResponse) response;

                    if(response!=null){

                      edt_first_name.setText(profileResponse.getFirst_name());
                      edt_second_name.setText(profileResponse.getLast_name());
                      edt_email_profile.setText(profileResponse.getEmail());
                      edt_phone.setText(profileResponse.getPhone());
                      tv_name.setText(profileResponse.getUsername());

                    }else{
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }

                break;

            case profileupdate:

                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    GenerealResponse generealResponse = (GenerealResponse) response;

                    if(response!=null){

                        if(generealResponse.getReason().isEmpty()){
                            Toast.makeText(this, generealResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, generealResponse.getReason(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }
}