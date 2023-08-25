package com.colis.colis_mobile;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colis.colis_mobile.models.ProfileModel;

import java.util.List;

public class ListContactAdapter extends BaseAdapter {

    List<ProfileModel> listProfile;

    private Context context;

    public ListContactAdapter(List<ProfileModel> listProfile, Context context) {
        this.listProfile = listProfile;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listProfile.size();
    }

    @Override
    public Object getItem(int i) {
        return listProfile.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(R.layout.fragment_chat, parent, false);

        ProfileModel profileModel = listProfile.get(position);

        LinearLayout contactLayout = new LinearLayout(context);

        contactLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contactLayout.setOrientation(LinearLayout.HORIZONTAL);

        // photo de profile

        ImageView profilImage = new ImageView(context);

        profilImage.setImageResource(R.drawable.utilisateur);
        profilImage.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        profilImage.setPadding(10, 0, 0 , 0);

        TextView infoText =  new TextView(context) ;
        infoText.setText(profileModel.getFullName());
        infoText.setTextSize(20);
        infoText.setPadding(10 , 0 , 0 , 0);
        infoText.setTextColor(Color.WHITE);

        // ajout des elements
        contactLayout.addView(profilImage);
        contactLayout.addView(infoText);

        LinearLayout listLayout = convertView.findViewById(R.id.listContactLayout);
        listLayout.addView(contactLayout);

        return listLayout;
    }
}
