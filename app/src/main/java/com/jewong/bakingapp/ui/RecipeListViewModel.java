package com.jewong.bakingapp.ui;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jewong.bakingapp.api.VideoAPIClient;
import com.jewong.bakingapp.data.Step;
import com.jewong.bakingapp.data.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeListViewModel extends ViewModel {

    public MutableLiveData<List<Video>> mVideoList = new MutableLiveData();
    public MutableLiveData<Video> mVideo = new MutableLiveData();
    public MutableLiveData<Integer> mStepIndex = new MutableLiveData();
    public LiveData<List<Step>> mStepList = Transformations.map(mVideo, video -> video.getSteps());
    public LiveData<Step> mStep = Transformations.map(mStepIndex, index -> mStepList.getValue().get(index));
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

    public void setVideo(Video video) {
        mVideo.setValue(video);
    }

    public void setStepIndex(int index) {
        mStepIndex.setValue(index);
    }

    public void adjustStepIndex(int adjustment) {
        int newIndex = mStepIndex.getValue() + adjustment;
        if (newIndex >= 0 && newIndex < mStepList.getValue().size()) {
            mStepIndex.setValue(newIndex);
        }
    }

    public boolean hasPreviousStep() {
        return mStepIndex.getValue() > 0;
    }

    public boolean hasNextStep() {
        return mStepIndex.getValue() < mStepList.getValue().size() - 1;
    }

    public boolean hasVideoURL() {
        return mStep.getValue() != null && !TextUtils.isEmpty(mStep.getValue().getVideoURL());
    }
}
