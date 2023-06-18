package com.colis.colis_mobile;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colis.colis_mobile.models.PostModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {



    private LinearLayout listLayout ;
    private ListView myList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        PostModel post1 = new PostModel(
                "Paris",
                LocalDateTime.now(),
                "Dakar",
                LocalDateTime.now(),
                20d,
                "EUR",
                20,
                20
        );



        PostModel post2 = new PostModel(
                "Marseille",
                LocalDateTime.now(),
                "Abidjan",
                LocalDateTime.now(),
                20d,
                "EUR",
                20,
                20
        );

        List<PostModel> postModelList = new ArrayList<>();

        postModelList.add(post1);
        postModelList.add(post2);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listPersonne(postModelList, view );

        return view;
    }

    public void clickPost(View view){

    }

    public void listPersonne(List<PostModel> postModelList , View view ) {

        for(PostModel postModel : postModelList){

            listLayout = (LinearLayout) view.findViewById(R.id.listPostLayout)  ;

            myList = (ListView) view.findViewById(R.id.listPost);



            LinearLayout infoLayout = new LinearLayout(getActivity());

//            ItemPostModel viewModel = new ViewModelProvider(requireActivity()).get(ItemPostModel.class);
//
//            viewModel.selectedPost(postModel);

            infoLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            infoLayout.setOrientation(LinearLayout.HORIZONTAL);

            // infoLayout.setId();


            // profile  Image info

            ImageView profilImage = new ImageView(getActivity());
            profilImage.setImageResource(R.drawable.utilisateur);
            profilImage.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            profilImage.setPadding(10, 0, 0 , 0);

            // nom , depart -> Arrivee , date depart

            // TODO:  ajouter le poids restant

            TextView infoText = new TextView(getActivity()) ;
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

            TextView prixText = new TextView(getActivity()) ;
            prixText.setText(postModel.getPrix().toString() + " " + postModel.getDevise() + "/Kg" );
            prixText.setTextSize(20);
            prixText.setTextColor(ContextCompat.getColor(getActivity(), R.color.green_1)) ;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30 , 0 , 0 );
            prixText.setLayoutParams(params);


            // ajout des élèments

            infoLayout.addView(profilImage);
            infoLayout.addView(infoText);
            infoLayout.addView(prixText);


            listLayout.addView(infoLayout);


//            infoLayout.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    DetailTrajetFragment detailTrajetFragment = new DetailTrajetFragment();
//                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//                    fm.replace(R.id.container,detailTrajetFragment );
//                }
//            });



//            infoLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   // Toast.makeText(getActivity() , "click ok ", Toast.LENGTH_SHORT).show();
//                   // Intent intent = new Intent(getActivity(), DetailTrajetFragment.class);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailTrajetFragment).commit();
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                }
//            });

        }

    }
}