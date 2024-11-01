package com.example.ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner colorSpinner;
    private Button saveButton;
    private SharedPreferences sharedPreferences;
    private String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        colorSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        colorSpinner.setOnItemSelectedListener(this);

        saveButton = findViewById(R.id.saveSettingBtn);
        saveButton.setOnClickListener(v -> {
            sharedPreferences.edit().putString("selectedColor", selectedColor).apply();
            finish();
        });

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedColor = parent.getItemAtPosition(position).toString();
        int colorResourceId = getResources().getIdentifier(selectedColor, "color", getPackageName());
        if (colorResourceId != 0) {
            setBackground(colorResourceId);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedColor = "default";
    }

    private void setBackground(int colorResourceId) {
        findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(colorResourceId));
    }
}
