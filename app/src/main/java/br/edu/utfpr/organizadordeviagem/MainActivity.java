package br.edu.utfpr.organizadordeviagem;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    private ListView listView;
    private ArrayList<Accommodation> accommodations;
    private AccommodationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        registerForContextMenu(listView);
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
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemAdd){
            Intent intent = new Intent(MainActivity.this, AddAccommodationActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
            return true;
        } else
            if (item.getItemId() == R.id.menuItemAbout) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            } else {
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