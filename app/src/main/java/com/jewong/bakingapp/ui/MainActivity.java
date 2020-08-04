package com.jewong.bakingapp.ui;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.jewong.bakingapp.R;
import com.jewong.bakingapp.api.VideoAPIClient;

public class MainActivity extends FragmentActivity {

    VideoAPIClient mVideoAPIClient = new VideoAPIClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return;
            RecipeListFragment fragment = new RecipeListFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}