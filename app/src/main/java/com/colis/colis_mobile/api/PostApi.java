package com.colis.colis_mobile.api;

import com.colis.colis_mobile.models.PostModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostApi {

    @POST("api/v1/posts/save")
    Call<ResponseBody> save(@Body PostModel postModel);
}
