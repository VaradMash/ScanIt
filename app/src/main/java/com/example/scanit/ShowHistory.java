package com.example.scanit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

public class ShowHistory extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private ListView svHistory;
    private ProgressBar pbHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        // Initialize widgets
        svHistory = (ListView)findViewById(R.id.svHistory);
        pbHistory = (ProgressBar)findViewById(R.id.pbHistory);

        dbHandler = new DatabaseHandler(this);
        try
        {
            List<HistoryElement> historyElementList = dbHandler.getHistory();
            HistoryItemView adapter = new HistoryItemView(this, historyElementList);
            svHistory.setAdapter(adapter);
            pbHistory.setVisibility(View.GONE);
            if(historyElementList.isEmpty())
                Toast.makeText(this, "No history found!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            pbHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         *  Input   :   Back Button Press
         *  Utility :   Navigate back to menu activity.
         *  Output  :   Activity launch.
         */
        // Initialize intent.
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        // Destroy current intent.
        ShowHistory.this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         *  Input   :   None
         *  Utility :   Check for past history in table.
         *  Output  :   Render to screen.
         */

    }
}