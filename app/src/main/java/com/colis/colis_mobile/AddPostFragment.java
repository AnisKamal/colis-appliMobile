package com.colis.colis_mobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.colis.colis_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener datePickerListenerDepart, datePickerListenerArrivee;
    private TimePickerDialog.OnTimeSetListener timePickerListenerDepart, timePickerListenerArrivee ;
    private Calendar calendar;

    ImageButton selectDateTimeButtonDepart, selectDateTimeButtonDestination;
    TextView selectedDateTimeTextViewDepart, selectedDateTimeTextViewDestination;

    Spinner deviseSpinner ;
    EditText numberEditText;

    public AddPostFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.fragment_add_post, container, false);
        selectDateTimeButtonDepart = view.findViewById(R.id.dateTimeDepartId) ;
        selectDateTimeButtonDestination = view.findViewById(R.id.dateTimeArriveeId);
         selectedDateTimeTextViewDepart = view.findViewById(R.id.textDateDepartId);
        selectedDateTimeTextViewDestination = view.findViewById(R.id.textDateTimeArrivee);

         numberEditText = view.findViewById(R.id.prixEditId);

        deviseSpinner = view.findViewById(R.id.deviseSpinnerId);

        ArrayAdapter<Devise> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, Devise.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviseSpinner.setAdapter(adapter);

        calendar = Calendar.getInstance();



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

        numberEditText.addTextChangedListener(new TextWatcher() {
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
                        numberEditText.setText("");
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
}