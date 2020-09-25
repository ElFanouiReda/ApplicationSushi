package com.example.applicationsushi;

import com.google.gson.JsonArray;

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

    @FormUrlEncoded
    @POST("dpPlatId.php")
    Call<JsonResponse> categorieplat(@Field("id") int id);

    @FormUrlEncoded
    @POST("addPanier.php")
    Call<JsonResponse> addPanier(@Field("ss") String ss, @Field("idd") int idd) ;

    @FormUrlEncoded
    @POST("addLike.php")
    Call<JsonResponse> addLike(@Field("ss") String ss, @Field("idd") int idd) ;

    @FormUrlEncoded
    @POST("addDislike.php")
    Call<JsonResponse> addDislike(@Field("ss") String ss, @Field("idd") int idd) ;

    @POST("commByIdPlat.php")
    Call<JsonArray> commByIdPlat(@Field("id") int idPlat);
}
