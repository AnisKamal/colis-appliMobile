package com.colis.colis_mobile;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colis.colis_mobile.models.PostModel;

import java.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationPostFragment} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class InformationPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_information_post, container, false);

        TextView prixText, kiloInitial,lieuDepart, lieuDestination, description, dateDepart, dateArrivee ;

        Bundle bundle = getArguments();

        if(bundle != null){
            PostModel selectedPost = (PostModel) bundle.getSerializable("selectedPost");
            prixText = view.findViewById(R.id.prixId0);
            prixText.setText(selectedPost.getPrix() + " " + selectedPost.getDevise() + "/ Kg");
            kiloInitial = view.findViewById(R.id.kiloDispoId0);
            kiloInitial.setText(selectedPost.getkiloRestant() + " Kg");
            lieuDepart = view.findViewById(R.id.departId0);
            lieuDepart.setText(selectedPost.getRegionDepart());
            lieuDestination = view.findViewById(R.id.arriveId0);
            lieuDestination.setText(selectedPost.getRegionDestination() );
            description = view.findViewById(R.id.descriptionId0);
            description.setText(selectedPost.getDescription());

            // paramettre des dates :

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String selectedDateArrivee = selectedPost.getDateRegionDestination().format(formatter);

            dateArrivee = view.findViewById(R.id.dateArrivee0);
            dateArrivee.setText(selectedDateArrivee);

            String selectedDateDepart = selectedPost.getDateRegionDepart().format(formatter);

            dateDepart = view.findViewById(R.id.dateDepart0);
            dateDepart.setText(selectedDateDepart);

            description = view.findViewById(R.id.descriptionId0);
            description.setText(selectedPost.getDescription());
        }

         return view ;
    }
}