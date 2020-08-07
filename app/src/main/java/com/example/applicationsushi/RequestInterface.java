package com.example.applicationsushi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<JsonResponse> login(@Field("login") String login, @Field("motdepasse") String motdepasse);

    @FormUrlEncoded
    @POST("register.php")
    Call<JsonResponse> register(@Field("login") String login, @Field("nom") String nom, @Field("prenom") String prenom, @Field("numerotel") String numerotel, @Field("motdepasse") String motdepasse);

}
