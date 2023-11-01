package com.colis.colis_mobile.api;

import com.colis.colis_mobile.models.PostModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {

    @POST("api/v1/posts/save")
    Call<ResponseBody> save(@Body PostModel postModel);

    @GET("api/v1/posts/{id}")
    Call<ResponseBody> findByUser(@Path("id") String id);

    @GET("api/v1/posts")
    Call<ResponseBody> findLastPosts();

   /* @GET("api/v1/posts/{regionDepart}/{regionDestination}")
    Call<ResponseBody> findPostSearch(@Path("regionDepart") String regionDepart,@Path("regionDestination") String regionDestination);*/

    @GET("api/v1/posts/search")
    Call<ResponseBody> findPostSearch(@Query("regionDepart") String regionDepart,@Query("regionDestination") String regionDestination );
}
