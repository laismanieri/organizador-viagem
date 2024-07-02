package br.edu.utfpr.organizadordeviagem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private static final String FILE = "br.edu.utfpr.organizadordeviagem.PREFERENCES_COLORS";
    private static final String SORT_ORDER = "SORT_ORDER";
    private int currentSortOrder;
    private LinearLayout layout;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    private ListView listView;
    private ArrayList<Accommodation> accommodations;
    private AccommodationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layoutMain);

        listView = findViewById(R.id.listView);
        accommodations = new ArrayList<>();

        adapter = new AccommodationAdapter(this, accommodations);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Accommodation selectedAccommodation = accommodations.get(position);
                Toast.makeText(MainActivity.this, "Clicou em: " + selectedAccommodation.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        setTitle("Organizador de Viagem");
        registerForContextMenu(listView);
        readOrderPreference();
    }


    private void readOrderPreference() {
        SharedPreferences shared = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        currentSortOrder = shared.getInt(SORT_ORDER, 0);
        sortAccommodations();
    }

    private void sortAccommodations() {
        if (currentSortOrder == 0) {
            sortAccommodationsByNameAZ();
        } else {
            sortAccommodationsByNameZA();
        }
        adapter.notifyDataSetChanged();
    }

    private void sortAccommodationsByNameAZ() {
        Collections.sort(accommodations, new Comparator<Accommodation>() {
            @Override
            public int compare(Accommodation o1, Accommodation o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    private void sortAccommodationsByNameZA() {
        Collections.sort(accommodations, new Comparator<Accommodation>() {
            @Override
            public int compare(Accommodation o1, Accommodation o2) {
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
        });
    }
    private void saveSortOrderPreference(int sortOrder) {
        SharedPreferences prefs = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SORT_ORDER, sortOrder);
        editor.apply();
    }
    private void showThemeSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha um Modo de Tema")
                .setItems(new CharSequence[]{"Modo Normal", "Modo Noturno"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Modo Normal
                                setThemeMode(AppCompatDelegate.MODE_NIGHT_NO);
                                break;
                            case 1: // Modo Noturno
                                setThemeMode(AppCompatDelegate.MODE_NIGHT_YES);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void setThemeMode(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
        recreate(); // Reinicia a atividade para aplicar o novo modo de tema
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String checkInDate = data.getStringExtra("checkInDate");
            String checkOutDate = data.getStringExtra("checkOutDate");

            if (requestCode == REQUEST_CODE_ADD) {
                Accommodation newAccommodation = new Accommodation(name, address, checkInDate, checkOutDate);
                accommodations.add(newAccommodation);
            } else if (requestCode == REQUEST_CODE_EDIT) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    Accommodation updatedAccommodation = accommodations.get(position);
                    updatedAccommodation.setName(name);
                    updatedAccommodation.setAddress(address);
                    updatedAccommodation.setCheckInDate(checkInDate);
                    updatedAccommodation.setCheckOutDate(checkOutDate);
                }
            }
            sortAccommodations();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;

        MenuItem sortItem;
        if (currentSortOrder == 0) {
            sortItem = menu.findItem(R.id.menuItemSortAZ);
        } else {
            sortItem = menu.findItem(R.id.menuItemSortZA);
        }

        sortItem.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        int itemId = item.getItemId();
        if (itemId == R.id.menuItemAdd) {
            Intent intent = new Intent(MainActivity.this, AddAccommodationActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
            return true;
        } else if (itemId == R.id.menuItemAbout) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menuItemSortAZ) {
            currentSortOrder = 0;
            saveSortOrderPreference(currentSortOrder);
            sortAccommodations();
            return true;
        } else if (itemId == R.id.menuItemSortZA) {
            currentSortOrder = 1;
            saveSortOrderPreference(currentSortOrder);
            sortAccommodations();
            return true;
        } else if (itemId == R.id.menuItemTheme) {
            showThemeSelectionDialog(); // Exibe um diálogo para o usuário escolher o tema
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void updateAccommodation(int position) {
        Intent intent = new Intent(MainActivity.this, AddAccommodationActivity.class);
        Accommodation accommodation = accommodations.get(position);
        intent.putExtra("name", accommodation.getName());
        intent.putExtra("address", accommodation.getAddress());
        intent.putExtra("checkInDate", accommodation.getCheckInDate());
        intent.putExtra("checkOutDate", accommodation.getCheckOutDate());
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }
   private void deleteAccommodation(int position) {
        accommodations.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.updateItemMenu){
            updateAccommodation(info.position);
            return true;
        } else if (item.getItemId() == R.id.deleteItemMenu) {
            deleteAccommodation(info.position);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}