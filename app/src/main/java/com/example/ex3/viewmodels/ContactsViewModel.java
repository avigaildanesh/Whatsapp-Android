package com.example.ex3.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.ex3.entities.Contact;

import com.example.ex3.repositories.ContactsRepository;


import java.util.List;

public class ContactsViewModel extends ViewModel {

    private ContactsRepository mRepository;
    private LiveData<List<Contact>> contacts;


    public ContactsViewModel() {
        mRepository = new ContactsRepository();
        contacts = mRepository.getAll();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void insert(Contact contact) {
        mRepository.insert(contact);
    }

    public void update(Contact contact) {
        mRepository.update(contact);
    }


    public void delete(Contact contact) {
        mRepository.delete(contact);
    }
    public void deleteAll() {
        mRepository.deleteAll();
    }
}
