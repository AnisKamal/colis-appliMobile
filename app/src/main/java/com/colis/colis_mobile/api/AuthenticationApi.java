package com.colis.colis_mobile.api;

import com.colis.colis_mobile.models.AuthenticationRequestModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {

    @POST("api/v1/auth/authenticate")
    Call<ResponseBody> authenticate(@Body AuthenticationRequestModel request);


}
