package com.example.scanit;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HistoryItemView extends ArrayAdapter<HistoryElement> {
    private Activity context;
    private List<HistoryElement> historyElements;
    private Intent intent;

    public HistoryItemView(Activity context, List<HistoryElement> itemList)
    {
        /*
         * Constructor for single element in donation list.
         */
        super(context, R.layout.history_item, itemList);
        this.context = context;
        this.historyElements = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
         *  Input   :   Position of history element in list.
         *  Utility :   Render data to card.
         *  Output  :   View
         */
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View historyItemView = layoutInflater.inflate(R.layout.history_item, null, true);
        TextView tvHistoryData = historyItemView.findViewById(R.id.tvHistoryData);
        TextView tvHistoryDate = historyItemView.findViewById(R.id.tvHistoryDate);
        Button btnHistoryCopy = historyItemView.findViewById(R.id.btnHistoryCopy);
        Button btnHistoryShare = historyItemView.findViewById(R.id.btnHistoryShare);
        Button btnHistoryAction = historyItemView.findViewById(R.id.btnHistoryAction);

        // Initializing history item.
        HistoryElement current_item = historyElements.get(position);
        String data = "Data : " + current_item.getData();
        String date = "Date : " + current_item.getTime_stamp();
        tvHistoryData.setText(data);
        tvHistoryDate.setText(date);

        //Deciding visibility for action button.
        intent = null;
        if (Patterns.EMAIL_ADDRESS.matcher(current_item.getData()).matches())
        {
            btnHistoryAction.setText("Email");
            String email = "mailto:" + current_item.getData();
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(email));
        }
        else if (Patterns.PHONE.matcher(current_item.getData()).matches())
        {
            btnHistoryAction.setText("Call");
            String call = "tel:" + current_item.getData();
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(call));
        }
        else if (Patterns.WEB_URL.matcher(current_item.getData()).matches())
        {

            btnHistoryAction.setText("Visit URL");

            String web =  current_item.getData();
            if (!web.startsWith("https:\\"))
            {
                web  = "https:\\" + web;
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
        }
        else
        {
            btnHistoryAction.setVisibility(View.GONE);
        }

        btnHistoryAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Input   :   None
                 *  Utility :   Launch relevant intent.
                 *  Output  :   Activity Launch
                 */
                context.startActivity(intent);
            }
        });

        btnHistoryCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ScanIt:result", current_item.getData());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        btnHistoryShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Input   :   None
                 *  Utility :   Share result content.
                 *  Output  :   Activity launch.
                 */
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, current_item.getData());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);

            }
        });

        return historyItemView;
    }
}
