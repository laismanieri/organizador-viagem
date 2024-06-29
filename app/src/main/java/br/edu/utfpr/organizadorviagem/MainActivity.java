package br.edu.utfpr.organizadorviagem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextInputEditText editTextStartDate, editTextEndDate;
    Spinner spinnerTravelPurpose;
    Button buttonAddItinerary;
    LinearLayout linearLayoutItineraries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);

        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextStartDate);
            }
        });

        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextEndDate);
            }
        });

        Spinner spinnerTravelPurpose = findViewById(R.id.spinnerTravelPurpose);
        String[] travelPurposes = getResources().getStringArray(R.array.travel_purposes_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, travelPurposes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTravelPurpose.setAdapter(adapter);

        buttonAddItinerary = findViewById(R.id.buttonAddItinerary);
        linearLayoutItineraries = findViewById(R.id.linearLayoutItineraries);
        buttonAddItinerary.setOnClickListener(v -> addItineraryView());
    }

    private void showDatePickerDialog(final TextInputEditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final TextInputEditText editText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    String time = hourOfDay + ":" + minute1;
                    editText.setText(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }
    private void addItineraryView() {
        final View itineraryView = getLayoutInflater().inflate(R.layout.itinerary_item, null);

        TextInputEditText editTextActivity = itineraryView.findViewById(R.id.editTextActivity);
        TextInputEditText editTextDate = itineraryView.findViewById(R.id.editTextDate);
        TextInputEditText editTextTime = itineraryView.findViewById(R.id.editTextTime);
        Button buttonRemoveItinerary = itineraryView.findViewById(R.id.buttonRemoveItinerary);

        editTextDate.setOnClickListener(v -> showDatePickerDialog(editTextDate));
        editTextTime.setOnClickListener(v -> showTimePickerDialog(editTextTime));

        buttonRemoveItinerary.setOnClickListener(v -> linearLayoutItineraries.removeView(itineraryView));

        linearLayoutItineraries.addView(itineraryView);
    }
}
