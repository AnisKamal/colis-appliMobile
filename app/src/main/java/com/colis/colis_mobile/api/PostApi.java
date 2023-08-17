package com.colis.colis_mobile.api;

import com.colis.colis_mobile.models.PostModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi {

    @POST("api/v1/posts/save")
    Call<ResponseBody> save(@Body PostModel postModel);

    @GET("api/v1/posts/{id}")
    Call<ResponseBody> findByUser(@Path("id") String id);

    @GET("api/v1/posts")
    Call<ResponseBody> findLastPosts();
}
