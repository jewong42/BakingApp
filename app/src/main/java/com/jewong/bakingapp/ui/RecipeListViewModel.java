package com.jewong.bakingapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jewong.bakingapp.api.VideoAPIClient;
import com.jewong.bakingapp.data.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeListViewModel extends ViewModel {

    public MutableLiveData<List<Video>> mVideoList = new MutableLiveData();
    private VideoAPIClient mVideoAPIClient = new VideoAPIClient();

    public void loadVideos() {
        mVideoAPIClient.getVideos(new retrofit2.Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                mVideoList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });
    }
}
