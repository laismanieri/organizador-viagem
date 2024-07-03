package br.edu.utfpr.organizadordeviagem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    private static final String FILE = "br.edu.utfpr.organizadordeviagem.PREFERENCES_COLORS";
    private static final String COLOR = "COLOR";
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.title_about_activity));

        // Definindo os valores dos TextViews com as strings do strings.xml
        ((TextView) findViewById(R.id.studentNameTextView)).setText(getString(R.string.student_name));
        ((TextView) findViewById(R.id.courseTextView)).setText(getString(R.string.course));
        ((TextView) findViewById(R.id.emailTextView)).setText(getString(R.string.email));
        ((TextView) findViewById(R.id.descriptionTextView)).setText(getString(R.string.app_description));
        ((TextView) findViewById(R.id.universityTextView)).setText(getString(R.string.university));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}