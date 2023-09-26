package com.colis.colis_mobile.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.colis.colis_mobile.LoginActivity;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;


    private static final Logger logger = Logger.getLogger(AuthInterceptor.class.getName());

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenAccess","");

        logger.info("verification token d access ");
        logger.info(token);

        Request request = chain.request().newBuilder()
                .addHeader("Authorization","Bearer " + token )
                .build();
        return chain.proceed(request);
    }
}
