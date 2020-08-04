package com.jewong.bakingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jewong.bakingapp.R;
import com.jewong.bakingapp.data.Step;
import com.jewong.bakingapp.databinding.AdapterStepBinding;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private ArrayList<Step> mDataset = new ArrayList<>();
    private StepAdapterCallback mStepAdapterCallback;

    public StepAdapter(StepAdapterCallback callback) {
        mStepAdapterCallback = callback;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_step, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = mDataset.get(position);
        holder.bind(step, position, mStepAdapterCallback);
    }

    public void setData(List<Step> dataSet) {
        mDataset.clear();
        if (dataSet != null) mDataset.addAll(dataSet);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterStepBinding mBinding;

        public ViewHolder(AdapterStepBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Step step, int index, StepAdapterCallback callback) {
            mBinding.description.setText(step.getShortDescription());
            mBinding.description.setOnClickListener(v1 -> callback.onStepClick(index));
        }

    }

    public interface StepAdapterCallback {
        void onStepClick(int index);
    }

}
