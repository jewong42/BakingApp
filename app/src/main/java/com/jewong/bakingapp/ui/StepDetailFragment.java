package com.jewong.bakingapp.ui;

import android.net.Uri;
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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.jewong.bakingapp.R;
import com.jewong.bakingapp.databinding.FragmentStepDetailBinding;

public class StepDetailFragment extends Fragment {

    FragmentStepDetailBinding mBinding;
    RecipeListViewModel mRecipeListViewModel;
    SimpleExoPlayer player;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_graph);
        mRecipeListViewModel = new ViewModelProvider(backStackEntry).get(RecipeListViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeObservers();
    }

    @Override
    public void onStop() {
        releasePlayer();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (player == null) initializeExoplayer();
    }

    private void initializeView() {
        if (mRecipeListViewModel.mStep.getValue() == null) return;
        int nextVisibility = mRecipeListViewModel.hasNextStep() ? View.VISIBLE : View.GONE;
        int previousVisibility = mRecipeListViewModel.hasPreviousStep() ? View.VISIBLE : View.GONE;
        mBinding.title.setText(mRecipeListViewModel.mStep.getValue().getShortDescription());
        mBinding.body.setText(mRecipeListViewModel.mStep.getValue().getDescription());
        mBinding.previousButton.setVisibility(previousVisibility);
        mBinding.nextButton.setVisibility(nextVisibility);
    }

    private void initializeExoplayer() {
        releasePlayer();
        if (getContext() == null) return;
        if (mRecipeListViewModel.mStep.getValue() != null) {
            mBinding.playerView.setVisibility(View.VISIBLE);
            player = new SimpleExoPlayer.Builder(getContext()).build();
            mBinding.playerView.setPlayer(player);
            Uri uri = Uri.parse(mRecipeListViewModel.mStep.getValue().getVideoURL());
            MediaSource mediaSource = buildMediaSource(uri);
            if (mediaSource != null) {
                player.setPlayWhenReady(true);
                player.seekTo(0, 0);
                player.prepare(mediaSource, false, false);
            }
        } else {
            mBinding.playerView.setVisibility(View.GONE);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        if (getContext() == null) return null;
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                getContext(),
                this.getClass().getSimpleName());
        return new ProgressiveMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void initializeObservers() {
        mBinding.nextButton.setOnClickListener(v ->
                mRecipeListViewModel.adjustStepIndex(1));
        mBinding.previousButton.setOnClickListener(v ->
                mRecipeListViewModel.adjustStepIndex(-1));
        mRecipeListViewModel.mStep.observe(getViewLifecycleOwner(), step -> {
            initializeView();
            initializeExoplayer();
        });
    }

}
