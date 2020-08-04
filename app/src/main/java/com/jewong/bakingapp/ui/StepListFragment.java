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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jewong.bakingapp.R;
import com.jewong.bakingapp.data.Step;
import com.jewong.bakingapp.databinding.FragmentStepListBinding;
import com.jewong.bakingapp.ui.adapter.StepAdapter;

import java.util.List;

public class StepListFragment extends Fragment implements StepAdapter.StepAdapterCallback {

    FragmentStepListBinding mBinding;
    RecipeListViewModel mRecipeListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_graph);
        mRecipeListViewModel = new ViewModelProvider(backStackEntry).get(RecipeListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        initializeObservers();
    }

    private void initializeViews() {
        mBinding.stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.stepRecyclerView.setAdapter(new StepAdapter(this));
    }

    private void initializeObservers() {
        mRecipeListViewModel.mStepList.observe(getViewLifecycleOwner(), this::setRecyclerView);
    }

    private void setRecyclerView(List<Step> dataSet) {
        StepAdapter adapter = (StepAdapter) mBinding.stepRecyclerView.getAdapter();
        if (adapter != null) adapter.setData(dataSet);
    }

    @Override
    public void onStepClick(int index) {
        mRecipeListViewModel.setStepIndex(index);
        NavHostFragment.findNavController(this).navigate(R.id.action_stepListFragment_to_stepDetailFragment);
    }
}
