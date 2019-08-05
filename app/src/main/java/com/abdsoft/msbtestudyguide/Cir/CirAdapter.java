package com.abdsoft.msbtestudyguide.Cir;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.PDFActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by abd on 29/1/18.
 */

public class CirAdapter extends RecyclerView.Adapter<CirAdapter.ViewHolder>
{

    List<CirItem> cirItems;
    Context context;

    String semester;
    String filePath;
    String fileName;
    String url;

    HomeActivity homeActivity;


    public CirAdapter(List<CirItem> cirItems, Context context) {
        this.cirItems = cirItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cir_list, parent, false);


        return new CirAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final CirItem cirItem = cirItems.get(position);

        holder.textCir.setText(cirItem.getCirName());


        homeActivity = (HomeActivity)context;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                homeActivity.semesterSelected = cirItem.getCirName();

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


                filePath = homeActivity.userChoice + "/" + homeActivity.departmentSelected + "/" + semester + ".pdf";
                fileName = homeActivity.departmentSelected + " " + semester + " Cir.pdf";
                homeActivity.instance++;
                Intent pdfIntent =  new Intent(context, PDFActivity.class);
                pdfIntent.putExtra("path",filePath);
                pdfIntent.putExtra("filename",fileName);
                pdfIntent.putExtra("instance",homeActivity.instance);
                pdfIntent.putExtra("userChoice",homeActivity.userChoice);
                homeActivity.startActivity(pdfIntent);


            }
        });


        holder.dLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeActivity.semesterSelected = cirItem.getCirName();

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

                fileName = homeActivity.departmentSelected + " " + semester + " Cir.pdf";
                filePath = homeActivity.userChoice + "/" + homeActivity.departmentSelected + "/" + semester + ".pdf";

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


    @Override
    public int getItemCount() {
        return cirItems.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textCir;
        CardView cardView;
        ImageButton dLoadButton;

        public ViewHolder(View itemView) {

            super(itemView);

            textCir = itemView.findViewById(R.id.textview_cir);
            cardView = itemView.findViewById(R.id.card_cir);
            dLoadButton = itemView.findViewById(R.id.button_cir);

        }
    }

    private void downloadFile(){

        if (isOffline()){
            showSnackbar("No Internet Connection");
        }
        else{

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
                        Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show();
                    }
                });


            }catch (Exception e){
                Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show();
            }

        }

    }

}
