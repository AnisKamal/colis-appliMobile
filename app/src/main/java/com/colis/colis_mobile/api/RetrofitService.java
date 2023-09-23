package com.colis.colis_mobile.api;

import android.content.Context;
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

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RetrofitService {

    private final String URL = "http://192.168.100.144:8080" ;

    private Retrofit retrofit;

    private Gson gson;

    private Context context;

    public RetrofitService(Context context) {
        initializeRetrofit(context);
    }

    private void initializeRetrofit(Context MyContext) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(MyContext))
                .build();

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
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }
}
