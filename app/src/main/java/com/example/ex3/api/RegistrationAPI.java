package com.example.ex3.api;

import android.content.Context;
import android.util.Log;

import com.example.ex3.R;
import com.example.ex3.model.RegistrationRequest;
import com.example.ex3.model.RegistrationResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationAPI {
    WebServiceAPI apiService;
    Retrofit retrofit;

    public RegistrationAPI(Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("TAG", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
         retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                 .client(httpClientBuilder.build())
                 .build();

        apiService = retrofit.create(WebServiceAPI.class);
    }

    public void registerUser(String username, String password, String displayName, String profilePic, final RegistrationCallback callback) {
        RegistrationRequest request = new RegistrationRequest(username, password, displayName, profilePic);
        Call<RegistrationResponse> call = apiService.registerUser(request);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();
                    callback.onSuccess(registrationResponse);
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface RegistrationCallback {
        void onSuccess(RegistrationResponse response);

        void onError(Throwable throwable);
    }
}
