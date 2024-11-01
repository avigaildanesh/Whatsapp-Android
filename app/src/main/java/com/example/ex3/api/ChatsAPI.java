package com.example.ex3.api;

import android.util.Log;

import com.example.ex3.MyApplication;
import com.example.ex3.R;
import com.example.ex3.entities.Contact;
import com.example.ex3.entities.Message;
import com.example.ex3.model.NewChatRequest;
import com.example.ex3.model.SendMessageRequest;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {
    private WebServiceAPI apiService;
    private Retrofit retrofit;

    public ChatsAPI() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("TAG", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getInstance().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        apiService = retrofit.create(WebServiceAPI.class);
    }

    public void getCurrentChats(String token, final ChatsCallback callback) {
        Call<List<Contact>> call = apiService.getCurrentChats("Bearer " + token);

        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    List<Contact> chats = response.body();
                    callback.onSuccess(chats);
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void createChat(String username, String token, final CreateChatCallback callback) {
        NewChatRequest request = new NewChatRequest(username);
        Call<Contact> call = apiService.createChat(request, "Bearer " + token);

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    Contact contact = response.body();
                    callback.onSuccess(contact);
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.d("contact", "onFailure: " + t.toString());
                callback.onError(t);
            }
        });
    }

    public void getChatMessages(String token, int chatId, final GetChatMessagesCallback callback) {



        Call<List<Message>> call = apiService.getChatMessages( "Bearer " + token, chatId);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    callback.onSuccess(messages);
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void sendMessage(String token, int chatId, String message, final SendMessageCallback callback) {
        SendMessageRequest request = new SendMessageRequest(message);
        Call<Void> call = apiService.sendMessage("Bearer " + token,chatId, request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getChat(int chatId, final GetChatCallback callback) {
        Call<Contact> call = apiService.getChat(chatId);

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    Contact chat = response.body();
                    callback.onSuccess(chat);
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                callback.onError(t);
            }


        });
    }


    public void deleteChat(int chatId, final DeleteChatCallback callback) {
        Call<Void> call = apiService.deleteChat(chatId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError(new Exception("Chat deletion failed"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface ChatsCallback {
        void onSuccess(List<Contact> chats);

        void onError(Throwable throwable);
    }

    public interface CreateChatCallback {
        void onSuccess(Contact contact);

        void onError(Throwable throwable);
    }

    public interface GetChatMessagesCallback {
        void onSuccess(List<Message> messages);

        void onError(Throwable throwable);
    }

    public interface SendMessageCallback {
        void onSuccess();

        void onError(Throwable throwable);
    }

    public interface GetChatCallback {
        void onSuccess(Contact chat);

        void onError(Throwable throwable);
    }

    public interface DeleteChatCallback {
        void onSuccess();

        void onError(Throwable throwable);
    }


}

