package com.abdsoft.msbtestudyguide.Department;

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

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.ViewHolder> {


    List<DeptItem> deptItems;
    Context context;


    public DeptAdapter(List<DeptItem> deptItems, Context context) {
        this.deptItems = deptItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dept_list, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final DeptItem deptItem = deptItems.get(position);

        holder.textDept.setText(deptItem.getDeptName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, deptItem.getSubName(), Toast.LENGTH_SHORT).show();
                HomeActivity homeActivity = (HomeActivity)context;
                homeActivity.departmentSelected = deptItem.getDeptName();
                if (homeActivity.userChoice.equals("QP") || homeActivity.userChoice.equals("MAP"))
                    homeActivity.showSEMFragment("DeptFragment Fragment");
                else if (homeActivity.userChoice.equals("Cir"))
                    homeActivity.showCirFragment("DeptFragment Fragment");

            }
        });
    }

    @Override
    public int getItemCount() {
        return deptItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textDept;
        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);

            textDept = itemView.findViewById(R.id.textview_dept);
            cardView = itemView.findViewById(R.id.card_dept);

        }


    }
}
