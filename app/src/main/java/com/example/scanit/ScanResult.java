package com.example.scanit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScanResult extends AppCompatActivity implements View.OnClickListener {

    private String result;
    private Button btnShare;
    private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        // Initialize result.
        Intent intent = getIntent();
        result = intent.getStringExtra("result");

        // Initialize widgets
        btnShare = (Button)findViewById(R.id.btnShare);
        tvData = (TextView)findViewById(R.id.tvData);
        String display_text = "Data :\n\n" + result;
        tvData.setText(display_text);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         *  Input   :   Back Button Press
         *  Utility :  Navigate to code scanning activity.
         *  Output  :   Activity Launch
         */
        // Initialize intent
        Intent intent = new Intent(getApplicationContext(), ScanCode.class);
        startActivity(intent);
        ScanResult.this.finish();
    }

    @Override
    public void onClick(View v) {
        /*
         *  Input   :   View v
         *  Utility :   Take required action based on the clicked button.
         *  Output  :   Variable
         */
        int id = v.getId();
        switch (id)
        {
            case R.id.btnShare :
            {
                String share_text = "Content : \n" + result + "\n\nScanned from ScanIt";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share_text);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
            }
        }

    }
}