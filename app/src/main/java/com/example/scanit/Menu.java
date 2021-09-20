package com.example.scanit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button btnScanCode, btnGenerateCode, btnDeveloper, btnHistory;
    private Intent next_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize widgets.
        btnScanCode = (Button)findViewById(R.id.btnScanCode);
        btnGenerateCode = (Button)findViewById(R.id.btnGenerateCode);
        btnDeveloper = (Button)findViewById(R.id.btnDeveloper);
        btnHistory = (Button)findViewById(R.id.btnViewHistory);

        // Set on click listener reference to current activity.
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
        next_intent = null;
        switch (btn_id)
        {
            case R.id.btnScanCode :
            {
                next_intent = new Intent(getApplicationContext(), ScanCode.class);
                break;
            }
            case R.id.btnGenerateCode :
            {
                next_intent = new Intent(getApplicationContext(), GenerateCode.class);
                break;
            }
            case R.id.btnViewHistory :
            {
                break;
            }
            case R.id.btnDeveloper :
            {
                next_intent = new Intent(getApplicationContext(), DeveloperDetails.class);
                break;
            }
        }
        try
        {
            // Start desired activity
            startActivity(next_intent);
            // Destory current activity.
            this.finish();

        }catch (NullPointerException e)
        {
            Toast.makeText(this, "Could not initialize activity ! ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        /*
         * Input : None
         * Utility: On pressing back button, launch main activity and clear field contents.
         * Output : Launch main activity.
         */
        AlertDialog.Builder alert_dialog = new AlertDialog.Builder(Menu.this);
        View dialog_view = getLayoutInflater().inflate(R.layout.exit_dialog, null);
        Button btnExit = (Button)dialog_view.findViewById(R.id.btnExit);
        Button btnCancel = (Button)dialog_view.findViewById(R.id.btnCancel);

        alert_dialog.setView(dialog_view);
        AlertDialog alertDialog = alert_dialog.create();
        alert_dialog.setCancelable(false);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent launch.
                Menu.this.finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}