package com.colis.colis_mobile;

import android.annotation.SuppressLint;
import android.content.res.Resources;
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
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
            String drapeauDepart = SearchUnicodeByCountryName(getResources(),R.raw.countries_code,selectedPost.getRegionDepart());
            String drapeauDestination = SearchUnicodeByCountryName(getResources(),R.raw.countries_code,selectedPost.getRegionDestination());
             prixText = view.findViewById(R.id.prixId);
            prixText.setText(selectedPost.getPrix() + " " + selectedPost.getDevise() + "/ Kg");
             kiloDispo = view.findViewById(R.id.kiloDispoId);
            kiloDispo.setText(selectedPost.getkiloRestant() + " Kg");
            lieuDepart = view.findViewById(R.id.departId);
            lieuDepart.setText(drapeauDepart +selectedPost.getRegionDepart());
            lieuDestination = view.findViewById(R.id.arriveId);
            lieuDestination.setText(drapeauDestination + selectedPost.getRegionDestination() );
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