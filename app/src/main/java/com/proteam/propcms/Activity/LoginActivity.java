package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.proteam.propcms.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_continue;
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
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_continue:
                Intent intentLogin = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intentLogin);
                break;
        }
    }
}