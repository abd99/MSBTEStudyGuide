package com.abdsoft.msbtestudyguide.SEMESTER;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;

import java.util.List;

/**
 * Created by abd on 27/1/18.
 */

public class SEMAdapter extends RecyclerView.Adapter<SEMAdapter.ViewHolder> {


    List<SEMItem> semItems;
    Context context;


    public SEMAdapter(List<SEMItem> semItems, Context context) {
        this.semItems = semItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sem_list, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SEMItem semItem = semItems.get(position);

        holder.textSEM.setText(semItem.getSemName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, deptItem.getSubName(), Toast.LENGTH_SHORT).show();
                HomeActivity homeActivity = (HomeActivity)context;
                homeActivity.semesterSelected = semItem.getSemName();
                homeActivity.showSubjectFragment("SEMFragment Fragment");

            }
        });
    }

    @Override
    public int getItemCount() {
        return semItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textSEM;
        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);

            textSEM = itemView.findViewById(R.id.textview_sem);
            cardView = itemView.findViewById(R.id.card_sem);

        }


    }
}
