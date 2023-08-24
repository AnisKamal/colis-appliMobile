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
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTrajetFragment} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DetailTrajetFragment extends Fragment {

    private static final Logger logger = Logger.getLogger(DetailTrajetFragment.class.getName());

    TextView prixText, kiloDispo,lieuDepart, lieuDestination, description, dateDepart, dateArrivee ;
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
        }
        return view ;
    }
}