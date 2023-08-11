package com.colis.colis_mobile;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.colis.colis_mobile.models.PostModel;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListPostManagementAdapter extends BaseAdapter {

    List<PostModel> postModelList;
    Context context;

    public ListPostManagementAdapter(List<PostModel> postModelList, Context context) {
        this.postModelList = postModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return postModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.fragment_post_management, parent, false);

        PostModel postModel = postModelList.get(position);

        LinearLayout infoLayout = new LinearLayout(context);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        infoLayout.setOrientation(LinearLayout.HORIZONTAL);
        infoLayout.setPadding(0, 20, 0, 0);

        TextView infoText = new TextView(context);
        infoText.setText(postModel.getRegionDepart() + " -> " + postModel.getRegionDestination() + " \n" +
                postModel.getDateRegionDepart().format(formatter));
        infoText.setTextSize(20);
        infoText.setPadding(10 , 0 , 0 , 0);
        infoText.setTextColor(Color.BLACK);
        infoText.setLayoutParams(new LinearLayout.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT));

        // prix
        TextView prixText = new TextView(context);
        prixText.setText(postModel.getPrix().toString() + " " + postModel.getDevise() + "/Kg");
        prixText.setTextSize(20);
        prixText.setTextColor(ContextCompat.getColor(context, R.color.green_1)) ;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30 , 0 , 0 );
        prixText.setLayoutParams(params);

        // Ajout des elements

        infoLayout.addView(infoText);
        infoLayout.addView(prixText);

        LinearLayout listLayout = convertView.findViewById(R.id.myListPost);

        if(!postModel.isActivity())
            infoLayout.setBackgroundColor(Color.parseColor("#E5E5E5"));

        listLayout.addView(infoLayout);

        return listLayout;
    }
}
