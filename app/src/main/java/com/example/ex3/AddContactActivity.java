package com.example.ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ex3.api.ChatsAPI;
import com.example.ex3.db.ContactsDB;
import com.example.ex3.entities.Contact;
import com.example.ex3.rooms.ContactsDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddContactActivity extends AppCompatActivity {

    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact2);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPreferences.getString("token", null);

        executor = Executors.newSingleThreadExecutor();

        Button addContactBtn = findViewById(R.id.addContactBtn);
        EditText etContactName = findViewById(R.id.etContactName);

        addContactBtn.setOnClickListener(v -> {
            String contactName = etContactName.getText().toString();
            ChatsAPI chatsAPI = new ChatsAPI();
            chatsAPI.createChat(contactName, token, new AddContactCb());
            finish();
        });
    }

    class AddContactCb implements ChatsAPI.CreateChatCallback {

        @Override
        public void onSuccess(Contact contact) {
            executor.execute(() -> {
                ContactsDB db = ContactsDB.getInstance();
                ContactsDao contactsDao = db.contactsDao();
                contactsDao.insert(contact);
            });
        }

        @Override
        public void onError(Throwable throwable) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
