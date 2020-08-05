package com.jewong.bakingapp.ui;

import android.text.TextUtils;

import androidx.annotation.NonNull;
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

    public MutableLiveData<List<Video>> mVideoList = new MutableLiveData<>();
    public MutableLiveData<Video> mVideo = new MutableLiveData<>();
    public MutableLiveData<Integer> mStepIndex = new MutableLiveData<>();
    public LiveData<List<Step>> mStepList = Transformations.map(mVideo, Video::getSteps);
    public LiveData<Step> mStep = Transformations.map(mStepIndex, this::getStep);
    private VideoAPIClient mVideoAPIClient = new VideoAPIClient();

    public void loadVideos() {
        mVideoAPIClient.getVideos(new retrofit2.Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                mVideoList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {

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
        if (mStepIndex.getValue() != null && mStepList.getValue() != null) {
            int newIndex = mStepIndex.getValue() + adjustment;
            if (newIndex >= 0 && newIndex < mStepList.getValue().size()) {
                mStepIndex.setValue(newIndex);
            }
        }
    }

    public void defaultStep() {
        mStepIndex.setValue(0);
    }

    public Step getStep(int index) {
        if (mStepList.getValue() != null) {
            return mStepList.getValue().get(index);
        }
        return null;
    }

    public boolean hasPreviousStep() {
        if (mStepIndex.getValue() != null) {
            return mStepIndex.getValue() > 0;
        }
        return false;
    }

    public boolean hasNextStep() {
        if (mStepIndex.getValue() != null && mStepList.getValue() != null) {
            return mStepIndex.getValue() < mStepList.getValue().size() - 1;
        }
        return false;
    }

    public boolean hasVideo() {
        if (mStep.getValue() != null) {
            return !TextUtils.isEmpty(mStep.getValue().getVideoURL());
        }
        return false;
    }
}
