package com.jewong.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jewong.bakingapp.R;
import com.jewong.bakingapp.data.Video;
import com.jewong.bakingapp.databinding.FragmentRecipeListBinding;
import com.jewong.bakingapp.ui.adapter.VideoAdapter;

import java.util.List;

public class RecipeListFragment extends Fragment
        implements VideoAdapter.VideoAdapterCallback {

    FragmentRecipeListBinding mBinding;
    RecipeListViewModel mRecipeListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeObservers();
        initializeViews();
        mRecipeListViewModel.loadVideos();
    }

    private void initializeViews() {
        mBinding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recipeRecyclerView.setAdapter(new VideoAdapter(this));
    }

    private void initializeObservers() {
        mRecipeListViewModel.mVideoList.observe(this, this::setRecyclerView);
    }

    private void setRecyclerView(List<Video> dataSet) {
        VideoAdapter adapter = (VideoAdapter) mBinding.recipeRecyclerView.getAdapter();
        if (adapter != null) adapter.setData(dataSet);
    }

    @Override
    public void onVideoClick(Video video) {

    }

}