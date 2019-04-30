package com.abdsoft.msbtestudyguide.Downloads;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.PDFActivity;
import com.abdsoft.msbtestudyguide.R;

import java.util.List;

/**
 * Created by abd on 4/4/18.
 */

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.ViewHolder>
{

    List<DownloadsItem> downloadsItems;
    Context context;

    String filePath;
    String fileName;

    HomeActivity homeActivity;

    public DownloadsAdapter(List<DownloadsItem> downloadsItems, Context context) {
        this.downloadsItems = downloadsItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dload_list, parent, false);


        return new DownloadsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final DownloadsItem downloadsItem = downloadsItems.get(position);

        holder.textDept.setText(downloadsItem.getfileName());

        homeActivity = (HomeActivity)context;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, deptItem.getSubName(), Toast.LENGTH_SHORT).show();

                filePath = downloadsItem.getfileName();

                fileName = downloadsItem.getfileName();

                homeActivity.instance++;

                Intent pdfIntent =  new Intent(context, PDFActivity.class);
                pdfIntent.putExtra("path",filePath);
                pdfIntent.putExtra("filename",fileName);
                pdfIntent.putExtra("instance",homeActivity.instance);
                pdfIntent.putExtra("userChoice",homeActivity.userChoice);
                homeActivity.startActivity(pdfIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return downloadsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textDept;
        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);

            textDept = itemView.findViewById(R.id.textview_downloads);
            cardView = itemView.findViewById(R.id.card_downloads);

        }


    }
}
