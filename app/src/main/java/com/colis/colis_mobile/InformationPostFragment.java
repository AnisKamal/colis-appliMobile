package com.colis.colis_mobile;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colis.colis_mobile.models.PostModel;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            String drapeauDepart = SearchUnicodeByCountryName(getResources(),R.raw.countries_code,selectedPost.getRegionDepart());
            String drapeauDestination = SearchUnicodeByCountryName(getResources(),R.raw.countries_code,selectedPost.getRegionDestination());
            prixText = view.findViewById(R.id.prixId0);
            prixText.setText(selectedPost.getPrix() + " " + selectedPost.getDevise() + "/ Kg");
            kiloInitial = view.findViewById(R.id.kiloDispoId0);
            kiloInitial.setText(selectedPost.getkiloRestant() + " Kg");
            lieuDepart = view.findViewById(R.id.departId0);
            lieuDepart.setText(drapeauDepart + selectedPost.getRegionDepart());
            lieuDestination = view.findViewById(R.id.arriveId0);
            lieuDestination.setText(drapeauDestination + selectedPost.getRegionDestination() );
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