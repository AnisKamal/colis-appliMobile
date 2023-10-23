package com.colis.colis_mobile.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    @Multipart
    @PUT("api/v1/user/update-image/{id}")
    Call<ResponseBody> updateImage(@Path("id") String id ,@Part MultipartBody.Part image);

}
