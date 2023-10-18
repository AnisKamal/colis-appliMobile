package com.colis.colis_mobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.colis.colis_mobile.R;
import com.colis.colis_mobile.api.PostApi;
import com.colis.colis_mobile.api.RetrofitService;
import com.colis.colis_mobile.models.Devise;
import com.colis.colis_mobile.models.PostModel;
import com.colis.colis_mobile.models.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class AddPostFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener datePickerListenerDepart, datePickerListenerArrivee;
    private TimePickerDialog.OnTimeSetListener timePickerListenerDepart, timePickerListenerArrivee ;
    private Calendar calendar;

    private ImageButton selectDateTimeButtonDepart, selectDateTimeButtonDestination;
    private TextView selectedDateTimeTextViewDepart, selectedDateTimeTextViewDestination;

    private Spinner deviseSpinner ;
    private EditText prixEditText, nbreKiloEditText,  descriptionEditText;

    private AutoCompleteTextView villeDepartEditText, villeDestinationEditText;

    private Button publierButton ;

    private static final Logger logger = Logger.getLogger(DetailTrajetFragment.class.getName());

    public AddPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.fragment_add_post, container, false);
        Bundle bundle = getArguments();
        selectDateTimeButtonDepart = view.findViewById(R.id.dateTimeDepartId) ;
        selectDateTimeButtonDestination = view.findViewById(R.id.dateTimeArriveeId);
         selectedDateTimeTextViewDepart = view.findViewById(R.id.textDateDepartId);
        selectedDateTimeTextViewDestination = view.findViewById(R.id.textDateTimeArrivee);
        prixEditText = view.findViewById(R.id.prixFieldId);
        deviseSpinner = view.findViewById(R.id.deviseSpinnerId);

        publierButton = view.findViewById(R.id.publierBtnId);

        nbreKiloEditText = view.findViewById(R.id.nbrKiloFieldId);
        villeDepartEditText = view.findViewById(R.id.villeDepartFieldId);
        villeDestinationEditText = view.findViewById(R.id.villeDestinationFieldId);
        descriptionEditText = view.findViewById(R.id.descriptionFieldId);

        ArrayAdapter<Devise> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, Devise.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviseSpinner.setAdapter(adapter);

        calendar = Calendar.getInstance();

        List<String> recommandations = readTextFile();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                recommandations
        );
        villeDepartEditText.setAdapter(adapter1);

        villeDestinationEditText.setAdapter(adapter1);

        if(bundle != null){
            PostModel selectedPost = (PostModel) bundle.getSerializable("selectedPost2");
            prixEditText.setText(selectedPost.getPrix().toString());
            villeDepartEditText.setText(selectedPost.getRegionDepart());
            villeDestinationEditText.setText(selectedPost.getRegionDestination());
            selectedDateTimeTextViewDepart.setText(selectedPost.getDateRegionDepart().toString());
            selectedDateTimeTextViewDestination.setText(selectedPost.getDateRegionDestination().toString());
            nbreKiloEditText.setText( String.valueOf(selectedPost.getkiloInitial()));
            descriptionEditText.setText(selectedPost.getDescription());
            publierButton.setText("Mettre a jour");
            // publierButton.setBackgroundColor(8743);
        }

        datePickerListenerDepart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Mettre à jour la date sélectionnée
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Afficher le dialogue TimePickerDialog
                showTimePickerDialogDepart();
            }
        };

        datePickerListenerArrivee = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Mettre à jour la date sélectionnée
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Afficher le dialogue TimePickerDialog
                showTimePickerDialogArrivee();
            }
        };

        // Listener pour le TimePickerDialog
        timePickerListenerDepart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Mettre à jour l'heure sélectionnée
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                // Mettre à jour le TextView avec la date et l'heure sélectionnées
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String selectedDateTime = sdf.format(calendar.getTime());
                selectedDateTimeTextViewDepart.setTextSize(22);
                selectedDateTimeTextViewDepart.setTextColor(Color.BLACK);
                selectedDateTimeTextViewDepart.setText(selectedDateTime);
            }
        };

        timePickerListenerArrivee  = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Mettre à jour l'heure sélectionnée
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                // Mettre à jour le TextView avec la date et l'heure sélectionnées
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String selectedDateTime = sdf.format(calendar.getTime());

                selectedDateTimeTextViewDestination.setTextSize(22);
                selectedDateTimeTextViewDestination.setTextColor(Color.BLACK);
                selectedDateTimeTextViewDestination.setText(selectedDateTime);
            }
        };

        selectDateTimeButtonDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogDepart();
            }
        });

        selectDateTimeButtonDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogArrivee();
            }
        });

        prixEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ne rien faire ici
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ne rien faire ici
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (!input.isEmpty()) {
                    // Vérifier si la saisie est un nombre
                    try {
                        int number = Integer.parseInt(input);
                        // La saisie est un nombre valide
                    } catch (NumberFormatException e) {
                        // La saisie n'est pas un nombre valide, réinitialiser le champ de texte
                        prixEditText.setText("");
                    }
                }
            }
        });

        nbreKiloEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (!input.isEmpty()) {
                    // Vérifier si la saisie est un nombre
                    try {
                        int number = Integer.parseInt(input);
                        // La saisie est un nombre valide
                    } catch (NumberFormatException e) {
                        // La saisie n'est pas un nombre valide, réinitialiser le champ de texte
                        prixEditText.setText("");
                    }
                }
            }

        });

        publierButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dateTimeRegex = "\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}";
                boolean isValid = true;
                String villeDepart = villeDepartEditText.getText().toString();
                String villeDestination = villeDestinationEditText.getText().toString();
                String nbreKilo = nbreKiloEditText.getText().toString();
                String prix = prixEditText.getText().toString();
                String dateDepart = selectedDateTimeTextViewDepart.getText().toString();
                String dateDestination = selectedDateTimeTextViewDestination.getText().toString();
                if(villeDepart.isEmpty() || !recommandations.contains(villeDepart)){
                    villeDepartEditText.setError("veuillez saisir un pays de depart correct");
                    Toast.makeText(getContext(), "Veuillez saisir un pays  de depart", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }if (villeDestination.isEmpty() || !recommandations.contains(villeDepart)) {
                    villeDestinationEditText.setError("veuillez saisir un pays de destination");
                    Toast.makeText(getContext(), "Veuillez saisir un pays de destination ", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }  if (nbreKilo.isEmpty()) {
                    nbreKiloEditText.setError("veuillez saisir le nombre de Kg total");
                    Toast.makeText(getContext(), "Veuillez saisir le nombre de Kg Total", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }  if (prix.isEmpty()) {
                    prixEditText.setError("veuillez saisir le prix par Kg");
                    Toast.makeText(getContext(), "Veuillez saisir le nombre de Kg Total", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }  if (!dateDepart.matches(dateTimeRegex)) {
                    Toast.makeText(getContext(), "Veuillez saisir la date et heure de depart", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }  if (!dateDestination.matches(dateTimeRegex)) {
                    Toast.makeText(getContext(), "Veuillez saisir la date et heure de destination", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if(isValid){

                    try {
                        RetrofitService retrofitService = new RetrofitService();
                        PostApi postApi = retrofitService.getRetrofit().create(PostApi.class);
                        String format = "dd/MM/yyyy HH:mm";
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                        LocalDateTime dateTimeDepart = LocalDateTime.parse(dateDepart, formatter);
                        LocalDateTime dateTimeDestination = LocalDateTime.parse(dateDestination,formatter);
                        double prixNumber= Double.parseDouble(prix);
                        int nbreKiloInt = Integer.parseInt(nbreKilo);

                        //   String formattedDateTime = dateTimeDepart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);


                        PostModel postModel = new PostModel(
                                villeDepart,
                                dateTimeDepart,
                                villeDestination,
                                dateTimeDestination,
                                prixNumber,
                                deviseSpinner.getSelectedItem().toString(),
                                nbreKiloInt,
                                nbreKiloInt,
                                descriptionEditText.getText().toString(),
                                true
                                );

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                        String id = sharedPreferences.getString("id", "");

                        postModel.setUser(new UserModel(id));

                        logger.info("mon log "+ postModel.toString());

                        postApi.save(postModel).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(getContext(), "save successful ! ", Toast.LENGTH_SHORT).show();
                                replaceFragment(new PostManagementFragment());
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "save failed ! ", Toast.LENGTH_SHORT).show();
                            }
                        }) ;

                    } catch (DateTimeParseException e) {
                        logger.info("error ! ");
                    }


                }

            }
        });


        return view ;
    }

    private void showDatePickerDialogDepart() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                datePickerListenerDepart,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialogDepart() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                timePickerListenerDepart,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void showDatePickerDialogArrivee() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                datePickerListenerArrivee,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialogArrivee() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                timePickerListenerArrivee,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        //fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private List<String> readTextFile() {
        List<String> dataList = new ArrayList<>();

        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream is = getResources().openRawResource(R.raw.pays);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }

            reader.close();
            isr.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }
}