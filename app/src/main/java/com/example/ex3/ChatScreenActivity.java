package com.example.ex3;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.adapters.ChatAdapter;
import com.example.ex3.api.ChatsAPI;
import com.example.ex3.entities.Chat;
import com.example.ex3.entities.Message;
import com.example.ex3.viewmodels.ChatsViewModel;

import java.util.List;

public class ChatScreenActivity extends AppCompatActivity {

    private EditText messageEditText;
    private TextView evContactName;
    private Button sendButton;
    private MutableLiveData<Chat> currentChat;

    private ChatAdapter adapter;
    private ImageView ivContactPic;
    private ChatsViewModel chatsViewModel;

    private int contactId;
    private String contactUsername;
    private String contactPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String selectedColor = sharedPreferences.getString("selectedColor", "default_color");
        int colorResourceId = getResources().getIdentifier(selectedColor, "color", getPackageName());
        if (colorResourceId != 0) {
            findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(colorResourceId));
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contactId = extras.getInt("id");
            contactUsername = extras.getString("username");
            contactPic = extras.getString("contactPic");
        }

        adapter = new ChatAdapter(this,contactUsername);
        RecyclerView rvMessageList = findViewById(R.id.rvMessages);
        rvMessageList.setAdapter(adapter);
        rvMessageList.setLayoutManager(new LinearLayoutManager(this));

        ivContactPic = findViewById(R.id.ivContactPic);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        byte[] decodedBytes = Base64.decode(contactPic, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        ivContactPic.setImageBitmap(bitmap);
        evContactName = findViewById(R.id.tvContactName);
        evContactName.setText(contactUsername);

        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        MutableLiveData<List<Message>> messages = new MutableLiveData<>();
        currentChat = new MutableLiveData<>();
        currentChat.observe(this, chat -> {
            if (chat != null) {
                messages.setValue(chat.getMessages());
            }
        });

        chatsViewModel.getChat(contactId).observe(this, chat -> {
            if (chat != null) {
                currentChat.setValue(chat);
            }
        });

        messages.observe(this, messagess -> {
            adapter.setMessages(messagess);
        });

        sendButton.setOnClickListener(v -> sendMessage());

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());



    }

    private void sendMessage() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        String token = sharedPreferences.getString("token", "");
        String messageText = messageEditText.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatsAPI chatsAPI = new ChatsAPI();
            chatsAPI.sendMessage(token, contactId, messageText, new ChatsAPI.SendMessageCallback() {
                @Override
                public void onSuccess() {
                    messageEditText.setText("");
                    chatsViewModel.update(contactId,token).observe(ChatScreenActivity.this, chat -> {
                        if (chat != null) {
                            currentChat.setValue(chat);
                        }
                    });
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle error
                }
            });
        }
    }

}
