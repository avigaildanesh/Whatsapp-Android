package com.example.ex3.api;

import android.content.Context;

import com.example.ex3.R;
import com.example.ex3.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private WebServiceAPI apiService;
    private Retrofit retrofit;

    public UserAPI(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(WebServiceAPI.class);
    }

    public void getUserData(String username, String token, final UserDataCallback callback) {
        Call<UserResponse> call = apiService.getUserData(username, "Bearer " + token);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    callback.onSuccess(userResponse);
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface UserDataCallback {
        void onSuccess(UserResponse response);

        void onError(Throwable throwable);
    }
}
