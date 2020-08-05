package com.jewong.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_graph);
        mRecipeListViewModel = new ViewModelProvider(backStackEntry).get(RecipeListViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);
        mRecipeListViewModel.loadVideos();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        initializeObservers();
    }

    private void initializeViews() {
        if (getResources().getBoolean(R.bool.isTablet)) {
            mBinding.recipeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            mBinding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mBinding.recipeRecyclerView.setAdapter(new VideoAdapter(this));
    }

    private void initializeObservers() {
        mRecipeListViewModel.mVideoList.observe(getViewLifecycleOwner(), this::setRecyclerView);
        mBinding.errorLayout.retryButton.setOnClickListener(v -> mRecipeListViewModel.loadVideos());
    }

    private void setRecyclerView(List<Video> dataSet) {
        VideoAdapter adapter = (VideoAdapter) mBinding.recipeRecyclerView.getAdapter();
        if (adapter != null && dataSet != null) {
            mBinding.errorLayout.container.setVisibility(View.GONE);
            adapter.setData(dataSet);
        } else {
            mBinding.errorLayout.container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onVideoClick(Video video) {
        mRecipeListViewModel.setVideo(video);
        NavHostFragment.findNavController(this).navigate(R.id.action_recipeListFragment_to_stepListFragment);
    }

}