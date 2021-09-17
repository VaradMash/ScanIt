package com.example.scanit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScanCode extends AppCompatActivity {
    private static final int RC_PERMISSION = 10;
    private CodeScanner mCodeScanner;
    private boolean mPermissionGranted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        mCodeScanner = new CodeScanner(this, findViewById(R.id.scanner_view));
            mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
                /*
                 *  Input   :   Result of Scan
                 *  Utility :   Navigate to result activity
                 *  Output  :   Activity Launch
                 */
                Intent intent = new Intent(getApplicationContext(), ScanResult.class);
                intent.putExtra("result", result.getText());
                startActivity(intent);
                // Destroy current activity
                ScanCode.this.finish();
                Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
    }));

        mCodeScanner.setErrorCallback(error -> runOnUiThread(
                () -> Toast.makeText(this, "An error occurred !", Toast.LENGTH_LONG).show()));
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mPermissionGranted = false;
            requestPermissions(new String[] {Manifest.permission.CAMERA}, RC_PERMISSION);
        } else {
            mPermissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
                mCodeScanner.startPreview();
            } else {
                mPermissionGranted = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionGranted) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         *  Input   :   Back Button Pressed.
         *  Utility :   Navigate to Menu activity.
         *  Output  :   Activity Launch
         */
        // Initialize intent
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        // Destroy current activity.
        ScanCode.this.finish();
    }
}