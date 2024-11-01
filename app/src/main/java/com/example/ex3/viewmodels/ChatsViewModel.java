package com.example.ex3.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.ex3.entities.Chat;
import com.example.ex3.repositories.ChatsRepository;


import java.util.List;

public class ChatsViewModel extends ViewModel {

    private ChatsRepository mRepository;
    private
    LiveData<List<Chat>> chats;



    public ChatsViewModel() {
        mRepository = new ChatsRepository();
        chats = mRepository.getAll();

    }

    public LiveData<List<Chat>> getChats() {
        return chats;
    }

    public LiveData<Chat> getChat(int id) {
        return mRepository.getById(id);
    }

    public void insert(Chat chat) {
        mRepository.insert(chat);
    }

   public LiveData<Chat> insertById(int id, String token){
       mRepository.insertById(id , token);
       return mRepository.getById(id);
   }

    public LiveData<Chat> update(int id, String token) {
       return mRepository.update(id,token);
    }

    public void delete(Chat chat) {
        mRepository.delete(chat);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
