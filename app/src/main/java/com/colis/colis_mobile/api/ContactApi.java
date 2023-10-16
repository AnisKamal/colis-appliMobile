package com.colis.colis_mobile.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContactApi {
    @GET("api/v1/contact/{id}")
    Call<ResponseBody> findByUser(@Path("id") String id);
}
