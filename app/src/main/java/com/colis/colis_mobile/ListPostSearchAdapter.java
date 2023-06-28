package com.colis.colis_mobile;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.colis.colis_mobile.models.PostModel;

import java.time.LocalDate;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListPostSearchAdapter extends BaseAdapter {

    private List<PostModel> listPost;
    private Context context;

    public ListPostSearchAdapter(List<PostModel> listPost, Context context) {
        this.listPost = listPost;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listPost.size();
    }

    @Override
    public Object getItem(int position) {
        return listPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_home, parent, false);

//        // Configurez les données pour cet élément de liste spécifique
        PostModel postModel = listPost.get(position);

        LinearLayout infoLayout = new LinearLayout(context);


        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        infoLayout.setOrientation(LinearLayout.HORIZONTAL);

        // infoLayout.setId();


        // profile  Image info

        ImageView profilImage = new ImageView(context);
        profilImage.setImageResource(R.drawable.utilisateur);
        profilImage.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        profilImage.setPadding(10, 0, 0 , 0);

        // nom , depart -> Arrivee , date depart

        // TODO:  ajouter le poids restant

        TextView infoText = new TextView(context) ;

            String myDate = LocalDate.now().toString();

        infoText.setText("Anis Kamal \n" +
                postModel.getLieuDepart()+ "->" + postModel.getLieuDestination() + "\n"+
                postModel.getDateDepart()
        );
        infoText.setTextSize(20);
        infoText.setPadding(10 , 0 , 0 , 0);
        infoText.setTextColor(Color.BLACK);
        infoText.setLayoutParams(new LinearLayout.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Prix

        TextView prixText = new TextView(context);
        prixText.setText(postModel.getPrix().toString() + " " + postModel.getDevise() + "/Kg");
        prixText.setTextSize(20);
        prixText.setTextColor(ContextCompat.getColor(context, R.color.green_1)) ;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30 , 0 , 0 );
        prixText.setLayoutParams(params);


        // ajout des élèments

        infoLayout.addView(profilImage);
        infoLayout.addView(infoText);
        infoLayout.addView(prixText);

        LinearLayout listLayout = convertView.findViewById(R.id.listPostLayout);

        listLayout.addView(infoLayout);

        return listLayout;
    }
}
