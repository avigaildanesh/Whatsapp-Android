package com.example.ex3.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex3.entities.Message;
import com.example.ex3.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    private MessagesRepository messagesRepository;
    private LiveData<List<Message>> chatMessages;

    public MessagesViewModel() {
        messagesRepository = new MessagesRepository();
    }

    public void setMessages(int id){
        chatMessages = messagesRepository.getMessagesForContact(id);
    }

    public LiveData<List<Message>> getChatMessages() {
        return chatMessages;
    }

    public void fetchData(int id){
        messagesRepository.fetchData(id);
    }



    public void sendMessage(int id,String messageText ) {
        messagesRepository.sendMessage(id,messageText);
    }
}
