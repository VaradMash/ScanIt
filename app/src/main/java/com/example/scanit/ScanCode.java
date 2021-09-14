package com.example.scanit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import android.os.Bundle;

public class ScanCode extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*
                         *  Utility :   Navigate to the desired URL.
                         */
                        Toast.makeText(ScanCode.this, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         *  Input   :   None
         *  Utility :   Check if App has been granted permission, if not, acquire permission.
         *  Output  :   None
         */
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ScanCode.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 440);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            /*
             *  Input   :   requestCode, resultCode, data.
             *  Utility :   If Camera permission is not granted, route back to Menu.
             *  Output  :   None.
             */
            if (requestCode != resultCode)
            {
                // Initialize intent.
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                // Launch Menu and destroy current activity.
                startActivity(intent);
                ScanCode.this.finish();
            }
        }
    }
}