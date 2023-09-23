package com.colis.colis_mobile.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenAccess","");


        Request request = chain.request().newBuilder()
                .addHeader("Authorization","Bearer " + token )
                .build();
        return chain.proceed(request);
    }
}
