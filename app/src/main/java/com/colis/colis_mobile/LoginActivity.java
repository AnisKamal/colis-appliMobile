package com.colis.colis_mobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.colis.colis_mobile.api.AuthInterceptor;
import com.colis.colis_mobile.api.AuthenticationApi;
import com.colis.colis_mobile.api.RetrofitService;
import com.colis.colis_mobile.api.RetrofitServiceWithoutInterceptor;
import com.colis.colis_mobile.models.AuthenticationRequestModel;
import com.colis.colis_mobile.models.AuthenticationResponseModel;
import com.colis.colis_mobile.models.PostModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;

    private Button loginButton ;

    private static final Logger logger = Logger.getLogger(LoginActivity.class.getName());

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailFieldId);
        passwordEditText = findViewById(R.id.passwordFieldId);
        loginButton = findViewById(R.id.connectionButtonId);


        RetrofitServiceWithoutInterceptor retrofitService = new RetrofitServiceWithoutInterceptor();
        AuthenticationApi authenticationApi = retrofitService.getRetrofit().create(AuthenticationApi.class);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                AuthenticationRequestModel request = new AuthenticationRequestModel(email, password);
                logger.info("ma requete : ");
                logger.info(request.toString());

                authenticationApi.authenticate(request)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {

                                    String responseBody = response.body().string();
                                    Type AuthenticationType = new TypeToken<AuthenticationResponseModel>(){}.getType();
                                    AuthenticationResponseModel tokenResponse = retrofitService.getGson().fromJson(responseBody,AuthenticationType );
                                    logger.info("ma response : " );
                                    logger.info(tokenResponse.toString());

                                    SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    logger.info("Mon token d access : ");
                                    logger.info(tokenResponse.getAccessToken());

                                    editor.putString("tokenAccess" ,tokenResponse.getAccessToken());
                                    editor.apply();

                                    Intent welcomeIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(welcomeIntent);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "KO", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }
}