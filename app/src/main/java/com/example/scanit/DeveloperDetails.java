package com.example.scanit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeveloperDetails extends AppCompatActivity {

    private Button btnEmail, btnLinkedIn, btnGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_details);

        // Initialize widgets
        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnLinkedIn = (Button)findViewById(R.id.btnLinkedIn);
        btnGithub = (Button)findViewById(R.id.btnGithub);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Input   :   None
                 *  Utility :   Launch developer Github profile.
                 *  Output  :   Intent launch
                 */
                String mail = "mailto:varadmash2201@gmail.com";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mail));
                startActivity(intent);
            }
        });

        btnLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Input   :   None
                 *  Utility :   Launch developer LinkedIn profile.
                 *  Output  :   Intent launch
                 */
                String linkedIn = "https://www.linkedin.com/in/varad-mashalkar-2237521b5/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedIn));
                startActivity(intent);
            }
        });

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Input   :   None
                 *  Utility :   Launch developer Github profile.
                 *  Output  :   Intent launch
                 */
                String github = "https://www.github.com/VaradMash";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(github));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         *  Input   :   Back button pressed.
         *  Utility :   Navigate back to menu activity.
         *  Output  :   Activity Launch
         */
        // Initialize Intent
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        // Destroy current activity.
        DeveloperDetails.this.finish();
    }
}