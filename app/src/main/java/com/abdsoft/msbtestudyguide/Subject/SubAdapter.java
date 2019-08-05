package com.abdsoft.msbtestudyguide.Subject;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;

import java.util.List;

/**
 * Created by abd on 28/1/18.
 */

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder>
{

    List<SubItem> subItems;
    Context context;

    public SubAdapter(List<SubItem> subItems, Context context) {
        this.subItems = subItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_list, parent, false);


        return new SubAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SubItem subItem = subItems.get(position);

        holder.textSub.setText(subItem.getSubName());
        holder.textSubCode.setText(subItem.getSubCode());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, deptItem.getSubName(), Toast.LENGTH_SHORT).show();
                HomeActivity homeActivity = (HomeActivity)context;
                homeActivity.subjectSelected = subItem.getSubCode();
                homeActivity.showYearFragment("SubjectFragment Fragment");

            }
        });

    }

    @Override
    public int getItemCount() {
        return subItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textSub;
        TextView textSubCode;
        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);

            textSub = itemView.findViewById(R.id.textview_sub);
            textSubCode = itemView.findViewById(R.id.textview_sub_code);
            cardView = itemView.findViewById(R.id.card_sub);

        }
    }

}
