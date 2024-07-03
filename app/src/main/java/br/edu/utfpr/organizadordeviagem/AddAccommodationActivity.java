package br.edu.utfpr.organizadordeviagem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddAccommodationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText addressEditText;
    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;
    private int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accommodation);

        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        checkInDateEditText = findViewById(R.id.checkInDateEditText);
        checkOutDateEditText = findViewById(R.id.checkOutDateEditText);

        checkInDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkInDateEditText);
            }
        });

        checkOutDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkOutDateEditText);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("position", -1);
            if (position != -1) {
                nameEditText.setText(intent.getStringExtra("name"));
                addressEditText.setText(intent.getStringExtra("address"));
                checkInDateEditText.setText(intent.getStringExtra("checkInDate"));
                checkOutDateEditText.setText(intent.getStringExtra("checkOutDate"));
            }
        }

        setTitle(getString(R.string.title_add_accommodation));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.accommodation_menu, menu);
        return true;
    }

    private void showDatePickerDialog(final EditText editText) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void saveAccommodation() {
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String checkInDate = checkInDateEditText.getText().toString();
        String checkOutDate = checkOutDateEditText.getText().toString();

        if (name.isEmpty() || address.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            Toast.makeText(this, getString(R.string.fields_empty_warning), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("address", address);
        resultIntent.putExtra("checkInDate", checkInDate);
        resultIntent.putExtra("checkOutDate", checkOutDate);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void clearFields() {
        nameEditText.setText("");
        addressEditText.setText("");
        checkInDateEditText.setText("");
        checkOutDateEditText.setText("");
        Toast.makeText(this, getString(R.string.fields_cleared), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else
        if(item.getItemId() == R.id.menuItemSave){
            saveAccommodation();
            return true;
        } else
        if (item.getItemId() == R.id.menuItemClear) {
            clearFields();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}