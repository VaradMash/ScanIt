package com.example.scanit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateCode extends AppCompatActivity {

    private EditText etCodeText;
    private Button btnShareCode;
    private Bitmap qrBits;
    private ImageView qrPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        // Initialize widgets
        etCodeText = (EditText)findViewById(R.id.etCodeText);
        btnShareCode = (Button)findViewById(R.id.btnShareCode);
        qrPlaceholder = (ImageView)findViewById(R.id.qrPlaceHolder);

        etCodeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*
                 *  Input   :   Changed text
                 *  Utility :   Generate QRCode for text
                 *  Output  :   QRCode rendered to screen.
                 */
                if(s.toString().isEmpty())
                {
                    btnShareCode.setVisibility(View.GONE);
                }
                QRGEncoder qrgEncoder = new QRGEncoder(s.toString(), null, QRGContents.Type.TEXT, 500);
                try
                {
                    // Display the share button.
                    btnShareCode.setVisibility(View.VISIBLE);
                    // Get image from encoder.
                    qrBits = qrgEncoder.getBitmap();
                    // Render to screen.
                    qrPlaceholder.setImageBitmap(qrBits);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debug", s.toString());

            }
        });

        btnShareCode.setOnClickListener(v -> {
            /*
             *  Input   :   QRCode Image
             *  Utility :   Share generated QRCode.
             *  Output  :   Share intent.
             */
            String data = etCodeText.getText().toString();
            if(data.isEmpty())
            {
                etCodeText.setError("Field cannot be empty !");
                etCodeText.requestFocus();
            }
            else
            {
                try
                {
                    Uri bitmap_uri;
                    bitmap_uri = saveImage(qrBits);
                    shareImageUri(bitmap_uri);
                }
                catch (Exception e)
                {
                    Toast.makeText(GenerateCode.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         *  Input   :   Back button press.
         *  Utility :   Navigate back to menu activity.
         *  Output  :   Activity Launch.
         */
        // Initialize intent.
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        // Destroy activity
        GenerateCode.this.finish();
    }

    // Utilities for Bitmap image sharing
    private Uri saveImage(Bitmap image) {
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.mydomain.fileprovider", file);

        } catch (IOException e) {
            Log.d("Debug", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    private void shareImageUri(Uri uri){
        /*
         *  Input   :   Image Uri
         *  Utility :   Launch Share intent for sharing QR code.
         *  Output  :   Intent Launch.
         */
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, "Generated from ScanIt");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setType("image/png");
        startActivity(intent);
    }

}