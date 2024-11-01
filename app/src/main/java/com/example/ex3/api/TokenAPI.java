package com.example.ex3.api;

import android.content.Context;

import com.example.ex3.R;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAPI {
    private WebServiceAPI apiService;

    public TokenAPI(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))                .build();

        apiService = retrofit.create(WebServiceAPI.class);
        int x = 1;
    }

    public void createJwtToken(String username, String password, final TokenCallback callback) {

        LoginCredentials credentials = new LoginCredentials(username, password);
        Call<String> call = this.apiService.createJwtToken(credentials);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Request failed";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onError(new Exception(errorMessage));
                }
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface TokenCallback {
        void onSuccess(String token);
        void onError(Throwable throwable);
    }
}
