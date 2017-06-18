package com.example.administrator.okhttpdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        IUserBiz userBiz=retrofit.create(IUserBiz.class);
//        Call<User> call=userBiz.getUser("sd");
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.e("", "onResponse: "+response.body() );
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable throwable) {
//
//            }
//        });


      Retrofit retrofit=new Retrofit.Builder()
              .baseUrl("")
              .addConverterFactory(GsonConverterFactory.create())
              .build();
        IUserBiz userBiz=retrofit.create(IUserBiz.class);




//        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
//        RequestBody photo = RequestBody.create(MediaType.parse("image/png", file);
//        Map<String,RequestBody> photos = new HashMap<>();
//        photos.put("photos\"; filename=\"icon.png", photo);
//        photos.put("username",  RequestBody.create(null, "abc"));
//
//        Call<User> call = userBiz.registerUser(photos, RequestBody.create(null, "123"));

        File file=new File(Environment.getExternalStorageDirectory(),"messenger_01.png");
        RequestBody photo=RequestBody.create(MediaType.parse("image/png"),file);
        Map<String,RequestBody> photos=new HashMap<>();
        photos.put("photos\":filename=\"icon.png",photo);
        photos.put("username",RequestBody.create(null,"abc"));

        Call<User> call=userBiz.registerUser(photos,RequestBody.create(null,"124"));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e(TAG, "onResponse: "+response.body() );
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });


    }
}
