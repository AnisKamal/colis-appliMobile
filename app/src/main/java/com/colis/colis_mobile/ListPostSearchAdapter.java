package com.colis.colis_mobile;

import android.content.Context;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListPostSearchAdapter extends RecyclerView.Adapter<ListPostSearchAdapter.PostViewHolder> {

    private List<PostModel> listPost;
    private Context context;

    private View.OnClickListener itemClickListener;

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


            if (postModel.getProfile().getPhotoProfile() != null) {
                Picasso.get().load(postModel.getProfile().getPhotoProfile()).transform(new CircleTransformation())
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


            //
            // profilImage.setImageURI(postModel.getProfile().getPhotoProfile());
            profilImage.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            profilImage.setPadding(10, 0, 0, 0);

            // nom , depart -> Arrivee , date depart

            // TODO:  ajouter le poids restant

            String myDate = LocalDate.now().toString();

            infoText.setText(postModel.getProfile().getFullName() + " \n" +
                    postModel.getRegionDepart() + "->" + postModel.getRegionDestination() + "\n" +
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

}


