package com.jewong.bakingapp.api;

import com.jewong.bakingapp.data.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoAPI {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Video>> getVideos();

}
