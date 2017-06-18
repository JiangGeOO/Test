package com.example.administrator.okhttpdemo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IUserBiz{

    @GET("{username}")
    Call<User>  getUser(@Path("username") String username);

    @GET("users")
    Call<List<User>>  getUsersBySort(@Query("sortby") String sort);

    @POST("add")
    Call<List<User>> addUser(@Body User user);


    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("username")String username,@Field("password") String password);


    @Multipart
    @POST("register")
    Call<User> registerUser(@Part MultipartBody.Part photo, @Part("username")RequestBody username,@Part("password") RequestBody password);





    @Multipart
    @POST("register")
    Call<User> registerUser(@PartMap Map<String,RequestBody> params,@Part("password") RequestBody password);



    @GET("download")
    Call<RequestBody> downloadTest();





















}