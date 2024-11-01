package com.example.ex3.api;

import com.example.ex3.entities.Contact;
import com.example.ex3.entities.Message;
import com.example.ex3.model.NewChatRequest;
import com.example.ex3.model.RegistrationRequest;
import com.example.ex3.model.RegistrationResponse;
import com.example.ex3.model.SendMessageRequest;
import com.example.ex3.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    @POST("Tokens")
    Call<String> createJwtToken(@Body LoginCredentials credentials);

    @POST("Users")
    Call<RegistrationResponse> registerUser(@Body RegistrationRequest request);

    @GET("Users/{username}")
    Call<UserResponse> getUserData(@Path("username") String username, @Header("Authorization") String token);

    @GET("Chats")
    Call<List<Contact>> getCurrentChats(@Header("Authorization") String token);

    @POST("Chats")
    Call<Contact> createChat(@Body NewChatRequest request, @Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getChatMessages(@Header("Authorization") String token, @Path("id") int chatId);

    @POST("Chats/{id}/Messages")
    Call<Void> sendMessage(@Header("Authorization") String token,@Path("id") int chatId, @Body SendMessageRequest request);

    @GET("Chats/{id}")
    Call<Contact> getChat(@Path("id") int chatId);

    @DELETE("Chats/{id}")
    Call<Void> deleteChat(@Path("id") int chatId);


}
