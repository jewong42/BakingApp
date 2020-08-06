package com.jewong.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.jewong.bakingapp.common.AppExecutors;
import com.jewong.bakingapp.data.Video;
import com.jewong.bakingapp.model.AppDatabase;
import com.jewong.bakingapp.ui.MainActivity;

public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            AppDatabase mAppDatabase = AppDatabase.getInstance(context);
            Video video = mAppDatabase.favoritesDao().loadVideoValue();
            AppExecutors.getInstance().mainThread().execute(() -> {
                RemoteViews views = getViews(context, video);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            });
        });
    }

    private static RemoteViews getViews(Context context, Video video) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if (video != null) {
            views.setViewVisibility(R.id.container, View.VISIBLE);
            views.setViewVisibility(R.id.error_container, View.GONE);
            views.setTextViewText(
                    R.id.title, video.getName());
            views.setRemoteAdapter(
                    R.id.ingredient_list_view,
                    AppWidgetRemoteViewService.getIntent(context));
        } else {
            views.setViewVisibility(R.id.container, View.GONE);
            views.setViewVisibility(R.id.error_container, View.VISIBLE);
            views.setOnClickPendingIntent(R.id.error_container, pendingIntent);
        }
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

