package com.jewong.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.jewong.bakingapp.AppWidget;
import com.jewong.bakingapp.R;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        setupWithNavController(toolbar, navController, appBarConfiguration);
    }


    public void updateWidget() {
        Intent intent = new Intent(this, AppWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        ComponentName componentName = new ComponentName(getApplication(), AppWidget.class);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

}