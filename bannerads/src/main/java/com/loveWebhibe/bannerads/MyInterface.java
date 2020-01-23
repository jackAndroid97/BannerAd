package com.loveWebhibe.bannerads;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyInterface {
    @FormUrlEncoded
    @POST("android_api.php")
    Call<String> FetchBannerADS(@Field("token") String token);
}
