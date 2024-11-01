package com.example.ex3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ex3.api.TokenAPI;

// ...

public class LogInScreenActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TokenAPI tokenAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setTextColor(ContextCompat.getColor(this, R.color.purple2));
        tokenAPI = new TokenAPI(this);


        buttonLogin.setOnClickListener(v -> {
            // Perform login logic here
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Validate input data and perform login
            if (isValidCredentials(username, password)) {
                login(username, password);
            } else {
                Toast.makeText(LogInScreenActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        TextView textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LogInScreenActivity.this, RegisterScreenActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidCredentials(String username, String password) {
        return username.trim().length() > 0 && password.trim().length() > 0;
    }


    private void login(String username, String password) {
        tokenAPI.createJwtToken(username, password, new TokenAPI.TokenCallback() {
            @Override
            public void onSuccess(String token) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LogInScreenActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", token);
                editor.apply();
                Toast.makeText(LogInScreenActivity.this, getString(R.string.success_registration), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogInScreenActivity.this, ContactsActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("username", username);
                startActivity(intent);
                editTextUsername.setText("");
                editTextPassword.setText("");
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(LogInScreenActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }}