package com.colis.colis_mobile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.colis.colis_mobile.models.PostModel;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListPostSearchAdapter extends RecyclerView.Adapter<ListPostSearchAdapter.PostViewHolder> {

    private List<PostModel> listPost;
    private Context context;

    private View.OnClickListener itemClickListener;

    private static final Logger logger = Logger.getLogger(HomeFragment.class.getName());

    public ListPostSearchAdapter(List<PostModel> listPost, Context context) {
        this.listPost = listPost;
        this.context = context;
    }

    public void setItemClickListener(View.OnClickListener  listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_list, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel postModel = listPost.get(position);
        holder.bind(postModel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(view); // Appel de la méthode onItemClick avec l'élément cliqué
                }
            }
        });


/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        PostModel selectedPost = (PostModel) parent.getItemAtPosition(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("selectedPost", selectedPost);
                        DetailTrajetFragment detailTrajetFragment = new DetailTrajetFragment();
//                ContactFragment contactFragment = new ContactFragment();
                        detailTrajetFragment.setArguments(bundle);
//                contactFragment.setArguments(bundle);
                        replaceFragment(detailTrajetFragment);

            }
        });
    }*/
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }



    public class PostViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout listLayout;

        ImageView profilImage;
        TextView infoText, prixText;
        LinearLayout infoLayout;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            listLayout = itemView.findViewById(R.id.listPostLayout);
            profilImage = itemView.findViewById(R.id.profileImageId2);
            infoText = itemView.findViewById(R.id.textId1);
            prixText = itemView.findViewById(R.id.textId2);
            infoLayout = itemView.findViewById(R.id.item_layout);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 10, 10, 10); // Vous pouvez ajuster les marges comme vous le souhaitez
            infoLayout.setLayoutParams(params);
        }

        public void bind(PostModel postModel) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            convertView = inflater.inflate(R.layout.fragment_home, parent, false);

//        // Configurez les données pour cet élément de liste spécifique
            // PostModel postModel = listPost.get(position);

            String format = "dd/MM/yyyy HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);


/*
            infoLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            infoLayout.setOrientation(LinearLayout.HORIZONTAL);
*/

            // infoLayout.setId();


            // profile  Image info

            logger.info("tentative pour le nom : " + postModel.getUser().getName());

            if (postModel.getUser().getUrlPhoto() != null) {
                Picasso.get().load(postModel.getUser().getUrlPhoto()).transform(new CircleTransformation())
                        .into(profilImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("Picasso", "Error loading image", e);
                            }
                        });
            } else {
                profilImage.setImageResource(R.drawable.utilisateur);
            }


            // profilImage.setImageURI(postModel.getProfile().getPhotoProfile());
            profilImage.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            profilImage.setPadding(10, 0, 0, 0);

            // nom , depart -> Arrivee , date depart

            // TODO:  ajouter le poids restant

            String myDate = LocalDate.now().toString();

            String drapeauPaysDepart = SearchUnicodeByCountryName(context.getResources(), R.raw.countries_code, postModel.getRegionDepart());
            String drapeauPaysDestination = SearchUnicodeByCountryName(context.getResources(), R.raw.countries_code, postModel.getRegionDestination());


            infoText.setText(postModel.getUser().getName() + " \n" +
                    drapeauPaysDepart + postModel.getRegionDepart() + "->" + drapeauPaysDestination + postModel.getRegionDestination() + "\n" +
                    postModel.getDateRegionDepart().format(formatter)
            );
            infoText.setTextSize(20);
            infoText.setPadding(10, 0, 0, 0);
            infoText.setTextColor(Color.BLACK);
            infoText.setLayoutParams(new LinearLayout.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT));

            // Prix


            prixText.setText(postModel.getPrix().toString() + " " + postModel.getDevise() + "/Kg");
            prixText.setTextSize(20);
            prixText.setTextColor(ContextCompat.getColor(context, R.color.green_1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 0);
            prixText.setLayoutParams(params);


            // ajout des élèments
            infoLayout.removeAllViews();
            infoLayout.addView(profilImage);
            infoLayout.addView(infoText);
            infoLayout.addView(prixText);

            //   listLayout.addView(infoLayout);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(PostModel postModel );
    }

    public String SearchUnicodeByCountryName(Resources resources, int rawResourceId, String countryName) {
        List<String> column5Data = new ArrayList();

        try {
            InputStream inputStream = resources.openRawResource(rawResourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(0).build();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                //  column5Data.add(nextRecord[4]);
                if(nextRecord[4].equals(countryName)){
                    return nextRecord[6];
                }
            }
            inputStream.close();
            inputStreamReader.close();
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}


