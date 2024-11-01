package com.example.ex3;


import static androidx.core.view.ViewCompat.setBackground;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.adapters.ContactsListAdapter;
import com.example.ex3.api.UserAPI;
import com.example.ex3.model.UserResponse;
import com.example.ex3.viewmodels.ChatsViewModel;
import com.example.ex3.viewmodels.ContactsViewModel;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel viewModel;
    private ImageButton addButton;
    private Button logOutBtn;
    private ImageView ivUserProfile;
    private TextView tvUserName;
    private String username;
    private String token;
    private ChatsViewModel chatsViewModel;
    private ImageView imageViewSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        ivUserProfile = findViewById(R.id.ivUserProfile);
        tvUserName = findViewById(R.id.tvUserName);

        setUserData();

        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        viewModel.getContacts().observe(this, adapter::setContacts);


        adapter.setOnItemClickListener(contact -> {
            Intent intent = new Intent(ContactsActivity.this, ChatScreenActivity.class);
            intent.putExtra("id", contact.getId());
            intent.putExtra("username", contact.getUserName());
            intent.putExtra("contactPic", contact.getProfilePic());

            chatData(contact.getId());
            startActivity(intent);
        });

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivity(intent);
        });

        logOutBtn = findViewById(R.id.logOutBtn);
        logOutBtn.setOnClickListener(v -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("token");
            editor.apply();
            viewModel.deleteAll();
            finish();
        });


        imageViewSettings = findViewById(R.id.imageViewSettings);
        imageViewSettings.setOnClickListener(v -> {
            Intent intent = new Intent(ContactsActivity.this, SettingActivity.class);
            startActivity(intent);
        });


    }

    public void chatData(int id){
        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        chatsViewModel.insertById(id,token);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String selectedColor = sharedPreferences.getString("selectedColor", "default_color");
        if (selectedColor.equals("default")){
            int photoResourceId = R.drawable.pic;
            findViewById(android.R.id.content).setBackgroundResource(photoResourceId);
        }
        else {
            int colorResourceId = getResources().getIdentifier(selectedColor, "color", getPackageName());
            if (colorResourceId != 0) {
                findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(colorResourceId));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
        viewModel.deleteAll();
    }

    private void setUserData() {

        Bundle extras = getIntent().getExtras();

        username = extras.getString("username");
        token = extras.getString("token");
        UserAPI userAPI = new UserAPI(this);
        userAPI.getUserData(username, token, new UserAPI.UserDataCallback() {
            @Override
            public void onSuccess(UserResponse response) {
                tvUserName.setText(response.getDisplayName());
                byte[] decodedBytes = Base64.decode(response.getProfilePic(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                ivUserProfile.setImageBitmap(bitmap);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.i("error", throwable.toString());
            }
        });
    }
}
