package com.example.scanit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button btnScanCode, btnGenerateCode, btnDeveloper, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnScanCode = (Button)findViewById(R.id.btnScanCode);
        btnGenerateCode = (Button)findViewById(R.id.btnGenerateCode);
        btnDeveloper = (Button)findViewById(R.id.btnDeveloper);
        btnHistory = (Button)findViewById(R.id.btnViewHistory);

        btnScanCode.setOnClickListener(this);
        btnGenerateCode.setOnClickListener(this);
        btnDeveloper.setOnClickListener(this);
        btnHistory.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        /*
         *  Input   :   View
         *  Utility :   launch activity based on the clicked view.
         *  Output  :   Activity Launch
         */
        int btn_id = v.getId();
        Intent intent = null;
        switch (btn_id)
        {
            case R.id.btnScanCode :
            {
                intent = new Intent(getApplicationContext(), ScanCode.class);
                break;
            }
            case R.id.btnGenerateCode :
            {
                break;
            }
            case R.id.btnViewHistory :
            {
                break;
            }
            case R.id.btnDeveloper : {
                break;
            }
        }
        try
        {
            // Start desired activity
            startActivity(intent);
            // Destory current activity.
            this.finish();

        }catch (NullPointerException e)
        {
            Toast.makeText(this, "Could not initialize activity ! ", Toast.LENGTH_SHORT).show();
        }
    }
}