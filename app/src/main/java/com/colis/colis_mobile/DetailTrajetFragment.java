package com.colis.colis_mobile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.colis.colis_mobile.models.PostModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTrajetFragment} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DetailTrajetFragment extends Fragment {

    private static final Logger logger = Logger.getLogger(DetailTrajetFragment.class.getName());

    TextView prixText, kiloDispo,lieuDepart, lieuDestination, description, dateDepart, dateArrivee, nomProfile ;
    ImageView profileImage ;
    Button contactButton ;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_trajet, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            PostModel selectedPost = (PostModel) bundle.getSerializable("selectedPost");
             prixText = view.findViewById(R.id.prixId);
            prixText.setText(selectedPost.getPrix() + " " + selectedPost.getDevise() + "/ Kg");
             kiloDispo = view.findViewById(R.id.kiloDispoId);
            kiloDispo.setText(selectedPost.getkiloRestant() + " Kg");
            lieuDepart = view.findViewById(R.id.departId);
            lieuDepart.setText(selectedPost.getRegionDepart());
            lieuDestination = view.findViewById(R.id.arriveId);
            lieuDestination.setText(selectedPost.getRegionDestination() );
             description = view.findViewById(R.id.descriptionId);
            description.setText(selectedPost.getDescription());

             contactButton = view.findViewById(R.id.contactID);

            nomProfile = view.findViewById(R.id.myProfile);

            profileImage = view.findViewById(R.id.profileImageId);



            // paramettre des dates :

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String selectedDateArrivee = selectedPost.getDateRegionDestination().format(formatter);

            dateArrivee = view.findViewById(R.id.dateArrivee);
            dateArrivee.setText(selectedDateArrivee);



            String selectedDateDepart = selectedPost.getDateRegionDepart().format(formatter);

            dateDepart = view.findViewById(R.id.dateDepart);
            dateDepart.setText(selectedDateDepart);

            description = view.findViewById(R.id.descriptionId);
            description.setText(selectedPost.getDescription());

            nomProfile.setText(selectedPost.getUser().getName());

            if(selectedPost.getUser().getUrlPhoto() != null ){
                Picasso.get().load(selectedPost.getUser().getUrlPhoto()).transform(new CircleTransformation())
                        .into(profileImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("Picasso", "Error loading image", e);
                            }
                        });
            }else{
                profileImage.setImageResource(R.drawable.utilisateur);
            }

            contactButton.setOnClickListener(contact -> {
                ContactFragment contactFragment = new ContactFragment();
//                Bundle myBundle = new Bundle();
//                bundle.putSerializable("selectedPostProfile", selectedPost);
                contactFragment.setArguments(bundle);
                replaceFragment(contactFragment);
            });
        }
        return view ;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        //fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}