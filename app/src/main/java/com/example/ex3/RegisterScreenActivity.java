package com.example.ex3;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ex3.api.RegistrationAPI;
import com.example.ex3.model.RegistrationResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RegisterScreenActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextDisplayName;

    private String profilePic;

    private static final int PICK_IMAGE_REQUEST = 1;

    private RegistrationAPI registrationAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextDisplayName = findViewById(R.id.editTextDisplayName);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonChoosePicture = findViewById(R.id.buttonChoosePicture);

        registrationAPI = new RegistrationAPI(this);

        buttonChoosePicture.setOnClickListener(v -> selectProfilePicture());
        buttonRegister.setOnClickListener(v -> registerUser());
        ImageButton backButton = findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreenActivity.this, LogInScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void selectProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profilePic = getBase64FromUri(imageUri);
        }
    }

    private String getBase64FromUri(Uri uri) {
        String base64Image = "";
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }


    private void registerUser() {
        String username = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String displayName = editTextDisplayName.getText().toString().trim();

        if (validateFields(username, password, confirmPassword, displayName)) {
            return;
        }

        if (profilePic == null) {
            Toast.makeText(RegisterScreenActivity.this, R.string.please_select_a_profile_picture, Toast.LENGTH_SHORT).show();
            return;
        }

        registrationAPI.registerUser(username, password, displayName, profilePic, new RegistrationAPI.RegistrationCallback() {
            @Override
            public void onSuccess(RegistrationResponse response) {
                Toast.makeText(RegisterScreenActivity.this, getString(R.string.success_registration), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(RegisterScreenActivity.this,  getString(R.string.registration_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFields(String username, String password, String confirmPassword, String displayName) {
        if (username.isEmpty()) {
            editTextName.setError(getString(R.string.error_invalid_username));
            return true;
        }

        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            editTextPassword.setError(getString(R.string.error_weak_password));
            return true;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError(getString(R.string.error_password_mismatch));
            return true;
        }

        if (displayName.isEmpty()) {
            editTextDisplayName.setError(getString(R.string.error_empty_display_name));
            return true;
        }

        if (displayName.equals(username)) {
            editTextDisplayName.setError(getString(R.string.error_same_username_display_name));
            return true;
        }

        return false;
    }
}
