package com.abdsoft.msbtestudyguide.Year;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.PDFActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by abd on 28/1/18.
 */

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder>
{

    List<YearItem> yearItems;
    Context context;

    String semester;
    String filePath;
    String fileName;
    String url;


    HomeActivity homeActivity;


    public YearAdapter(List<YearItem> yearItems, Context context) {
        this.yearItems = yearItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.year_list, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        final YearItem yearItem = yearItems.get(position);

        holder.textYear.setText(yearItem.getYearName());

        if (holder.textYear.getText().equals("2017 Winter question paper "))
        {
            holder.dLoadButton.setVisibility(View.GONE);
            holder.textYear.setText(holder.textYear.getText() + " (N/A Refer Solved Answer Paper)");
        }


        homeActivity = (HomeActivity)context;


        switch (homeActivity.semesterSelected)
        {
            case "I SEM":
                semester = "1G";
                break;
            case "II SEM":
                semester = "2G";
                break;
            case "III SEM":
                semester = "3G";
                break;
            case "IV SEM":
                semester = "4G";
                break;
            case "V SEM":
                semester = "5G";
                break;
            case "VI SEM":
                semester = "6G";
                break;
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeActivity.yearSelected = yearItem.getYearName();

                if (homeActivity.yearSelected.equals("2017 Winter question paper "))
                    Toast.makeText(context, "Refer solved answer paper", Toast.LENGTH_LONG).show();
                else {

                    filePath = homeActivity.userChoice + "/" + semester + "/" + homeActivity.subjectSelected + " " + homeActivity.yearSelected+".pdf";
//                Toast.makeText(context, "File path: " + filePath, Toast.LENGTH_SHORT).show();
                    fileName = homeActivity.subjectSelected + " " + homeActivity.yearSelected + ".pdf";

                    homeActivity.instance++;

                    Intent pdfIntent =  new Intent(context, PDFActivity.class);
                    pdfIntent.putExtra("path",filePath);
                    pdfIntent.putExtra("filename",fileName);
                    pdfIntent.putExtra("instance",homeActivity.instance);
                    pdfIntent.putExtra("userChoice",homeActivity.userChoice);
                    homeActivity.startActivity(pdfIntent);

                }

            }
        });

        holder.dLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                homeActivity.yearSelected = yearItem.getYearName();
                fileName = homeActivity.subjectSelected + " " + homeActivity.yearSelected + ".pdf";
                filePath = homeActivity.userChoice + "/" + semester + "/" + homeActivity.subjectSelected + " " + homeActivity.yearSelected+".pdf";


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((homeActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (homeActivity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED))
                    {
                        homeActivity.requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    else
                    {

//                Toast.makeText(context, "File path: " + filePath, Toast.LENGTH_SHORT).show();

                        downloadFile();
                    }
                }
                else
                {


//                Toast.makeText(context, "File path: " + filePath, Toast.LENGTH_SHORT).show();

                    downloadFile();
                }


            }
        });

    }

    private void showSnackbar(String message) {
        Snackbar.make(homeActivity.mRootView, message, Snackbar.LENGTH_LONG).show();
    }

    private boolean isOffline() {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return !(manager != null
                && manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnectedOrConnecting());
    }

    @Override
    public int getItemCount() {
        return yearItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textYear;
        CardView cardView;
        ImageButton dLoadButton;

        public ViewHolder(View itemView)
        {
            super(itemView);

            textYear = itemView.findViewById(R.id.textview_year);
            cardView = itemView.findViewById(R.id.card_year);
            dLoadButton = itemView.findViewById(R.id.button_year);

        }
    }


    public void downloadFile(){
        if (isOffline())
        {
            showSnackbar("No Internet Connection");
        }
        else
        {
            try {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference reference = storage.getReference().child(filePath);
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString();

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setDescription("Downloading " + fileName);
                        request.setTitle(fileName);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        }
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/" + context.getString(R.string.app_name), fileName);

                        DownloadManager manager = (DownloadManager) homeActivity.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
//                        Toast.makeText(context, "Downloading... ", Toast.LENGTH_SHORT).show();
                        showSnackbar("Downloading...");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e.toString().equals("com.google.firebase.storage.StorageException: Object does not exist at location.")) {
                            Toast.makeText(context, "Sorry, this file is currently not available.", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signInAnonymously();
                        String userName = mAuth.getCurrentUser().getUid().toString();
                        DatabaseReference myRef = database.getReference("Error in downloading on " + userName + "'s device: ");

                        String feedbackText = new String(e.fillInStackTrace().toString() + " " + filePath);

                        myRef.setValue(feedbackText);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Toast.makeText(context, "Error Reported", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                //Toast.makeText(context, "Failed to report error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }catch (Exception e){
                Toast.makeText(context, "Error occurred:" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
