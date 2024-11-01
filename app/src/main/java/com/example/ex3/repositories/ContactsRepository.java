package com.example.ex3.repositories;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.ex3.ContactsActivity;
import com.example.ex3.MyApplication;
import com.example.ex3.api.ChatsAPI;
import com.example.ex3.db.ContactsDB;
import com.example.ex3.entities.Contact;
import com.example.ex3.rooms.ContactsDao;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {

    private ContactsDao contactsDao;
    private LiveData<List<Contact>> contacts;

    public ContactsRepository() {

        ContactsDB db = ContactsDB.getInstance();
        contactsDao = db.contactsDao();
        contacts = contactsDao.getAllContacts();
        ChatsAPI chatsAPI = new ChatsAPI();
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(MyApplication.getInstance());
        String token = sharedPreferences.getString("token", null);
        chatsAPI.getCurrentChats(token, new ChatsCallbackImpl());
    }

    public void insert(Contact... contacts){
        new InsertContactsAsync(contactsDao).execute(contacts);
    }

    public void update(Contact... contacts){
        new UpdateContactsAsync(contactsDao).execute(contacts);
    }

    public void delete(Contact... contacts){
        new DeleteContactsAsync(contactsDao).execute(contacts);
    }

    public void deleteAll() {
        new DeleteAllContactsAsync(contactsDao).execute();
    }


    public LiveData<List<Contact>> getAll(){
        return contacts;
    }

    public void insertAll(List<Contact> contacts) {

        for (Contact contact : contacts){
            new DeleteContactsAsync(contactsDao).execute(contact);
        }

        for (Contact contact : contacts){
            new InsertContactsAsync(contactsDao).execute(contact);
        }

    }

    private static class DeleteAllContactsAsync extends AsyncTask<Void, Void, Void> {

        private ContactsDao contactsDao;

        public DeleteAllContactsAsync(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactsDao.deleteAll();
            return null;
        }
    }

    private static class InsertContactsAsync extends AsyncTask<Contact,Void,Void>{

        private ContactsDao contactsDao;

        public InsertContactsAsync(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            Contact contact = contactsDao.getContactById(contacts[0].getId()).getValue();
            if (contact == null){
                contactsDao.insert(contacts[0]);
            }
            return null;
        }
    }

    private static class UpdateContactsAsync extends AsyncTask<Contact,Void,Void>{

        private ContactsDao contactsDao;

        public UpdateContactsAsync(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactsAsync extends AsyncTask<Contact,Void,Void>{

        private ContactsDao contactsDao;

        public DeleteContactsAsync(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDao.delete(contacts[0]);
            return null;
        }
    }



    class ChatsCallbackImpl implements ChatsAPI.ChatsCallback {
        @Override
        public void onSuccess(List<Contact> contacts) {
            insertAll(contacts);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("Error occurred: " + throwable.getMessage());
        }
    }
}
