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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_graph);
        mRecipeListViewModel = new ViewModelProvider(backStackEntry).get(RecipeListViewModel.class);
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
        if (getResources().getBoolean(R.bool.isTablet)) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_detail_fragment_container, new StepDetailFragment())
                    .commit();
            mRecipeListViewModel.defaultStep();
        }
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
        if (!getResources().getBoolean(R.bool.isTablet)) {
            NavHostFragment.findNavController(this).navigate(R.id.action_stepListFragment_to_stepDetailFragment);
        }
    }
}
