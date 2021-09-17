package com.example.scanit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ScanResult extends AppCompatActivity implements View.OnClickListener {

    private String result, code_type;
    private Button btnShare, btnCopy, btnAction;
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
        btnCopy = (Button)findViewById(R.id.btnCopy);
        btnAction = (Button)findViewById(R.id.btnAction);

        // Match regular expression with result and determine action
        if (Patterns.EMAIL_ADDRESS.matcher(result).matches())
        {
            code_type = "Send Email";
        }
        else if (Patterns.PHONE.matcher(result).matches())
        {
            code_type = "Contact";
        }
        else if (Patterns.WEB_URL.matcher(result).matches())
        {
            code_type = "Visit URL";
        }
        else
        {
            code_type="Text";
        }
        if (code_type.equals("Text"))
        {
            // Disable button
            btnAction.setVisibility(View.GONE);
        }
        else
        {
            // Set text of button according to code type.
            btnAction.setText(code_type);
        }


        String display_text = "Data :\n\n" + result;
        tvData.setText(display_text);
        btnShare.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnAction.setOnClickListener(this);
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

    @SuppressLint("NonConstantResourceId")
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
            case R.id.btnAction :
            {
                try
                {
                    if (code_type.equals("Send Email"))
                    {
                        String mail_string = "mailto:" + result;
                        Intent mail_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mail_string));
                        startActivity(mail_intent);
                    }
                    else if (code_type.equals("Contact"))
                    {
                        String dial_string = "tel:" + result;
                        Intent dial_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dial_string));
                        startActivity(dial_intent);
                    }
                    else if (code_type.equals("Visit URL"))
                    {
                        if(!result.startsWith("https:\\"))
                        {
                            result = "https:\\" + result;
                        }
                        Intent web_intent = new Intent(Intent.CATEGORY_BROWSABLE, Uri.parse(result));
                        web_intent.setAction(Intent.ACTION_VIEW);
                        startActivity(web_intent);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "Could not initialize intent !", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btnCopy :
            {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ScanIt:result", result);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }
}