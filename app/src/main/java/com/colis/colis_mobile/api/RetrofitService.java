package com.colis.colis_mobile.api;

import android.os.Build;
import android.util.JsonReader;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RetrofitService {

    private Retrofit retrofit;

    private Gson gson;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {

        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,  (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(isoFormatter)) )
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json,typeOfT, context) -> {
                    String dateString = json.getAsString();
                    return LocalDateTime.parse(dateString, isoFormatter);
                })
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
               // .baseUrl("http://192.168.11.101:8080")
                .baseUrl("https://cocolisgp.onrender.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }
}
