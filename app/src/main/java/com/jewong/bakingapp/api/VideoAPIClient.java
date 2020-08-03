package com.jewong.bakingapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jewong.bakingapp.data.Video;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoAPIClient {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private VideoAPI mVideoAPI;

    public VideoAPIClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.mVideoAPI = retrofit.create(VideoAPI.class);
    }

    public void getVideos(Callback<List<Video>> callback) {
        mVideoAPI.getVideos().enqueue(callback);
    }

}
