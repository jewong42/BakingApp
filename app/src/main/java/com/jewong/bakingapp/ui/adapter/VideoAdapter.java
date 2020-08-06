package com.jewong.bakingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.jewong.bakingapp.R;
import com.jewong.bakingapp.data.Video;
import com.jewong.bakingapp.databinding.AdapterVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<Video> mDataset = new ArrayList<>();
    private VideoAdapterCallback mVideoAdapterCallback;
    private Video mFavoriteVideo;

    public VideoAdapter(VideoAdapterCallback callback) {
        mVideoAdapterCallback = callback;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterVideoBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_video, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Video video = mDataset.get(position);
        holder.bind(video, mVideoAdapterCallback, mFavoriteVideo);
    }

    public void setData(List<Video> dataSet) {
        mDataset.clear();
        if (dataSet != null) mDataset.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void setFavoriteVideo(Video video) {
        mFavoriteVideo = video;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterVideoBinding mBinding;
        private Context mContext;

        public ViewHolder(AdapterVideoBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mContext = binding.getRoot().getContext();
        }

        public void bind(Video video, VideoAdapterCallback callback, Video favorite) {
            final int numServings = video.getServings() != null ? video.getServings() : 0;
            final int numIngredients = video.getIngredients() != null ? video.getIngredients().size() : -1;
            final int numSteps = video.getSteps() != null ? video.getSteps().size() : -1;
            final boolean isFavorite = favorite != null && video.getId().equals(favorite.getId());
            mBinding.name.setText(video.getName());
            renderServings(mBinding.servings, numServings);
            renderChip(mBinding.ingredientsChip, mContext.getString(R.string.x_ingredients), numIngredients);
            renderChip(mBinding.stepsChip, mContext.getString(R.string.x_steps), numSteps);
            renderFavorite(isFavorite);
            mBinding.container.setOnClickListener(v1 -> callback.onVideoClick(video));
            mBinding.favorite.setOnClickListener(v1 -> callback.onFavorite(video));
        }

        private void renderFavorite(boolean isFavorite) {
            int drawableId = isFavorite ? R.drawable.ic_push_pin_filled : R.drawable.ic_push_pin_outlined;
            mBinding.favorite.setImageResource(drawableId);
        }

        private void renderServings(TextView textView, Integer servings) {
            final String template = textView.getContext().getString(R.string.makes_x_servings);
            final String string = String.format(template, servings.toString());
            textView.setText(string);
        }

        private void renderChip(Chip chip, String template, Integer size) {
            final String count = size != -1 ? String.valueOf(size) : "";
            final String string = String.format(template, count);
            final int visibility = size != -1 ? View.VISIBLE : View.GONE;
            chip.setText(string);
            chip.setVisibility(visibility);
        }
    }

    public interface VideoAdapterCallback {
        void onVideoClick(Video video);
        void onFavorite(Video video);
    }

}
