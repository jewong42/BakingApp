package com.jewong.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.jewong.bakingapp.data.Ingredient;
import com.jewong.bakingapp.data.Video;
import com.jewong.bakingapp.model.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppWidgetRemoteViewService extends RemoteViewsService {

    public static Intent getIntent(Context context) {
        return new Intent(context, AppWidgetRemoteViewService.class);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetRemoteViewsFactory(getApplicationContext());
    }

    public static class AppWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private List<Ingredient> mIngredientList = new ArrayList<>();
        private AppDatabase mAppDatabase;

        public AppWidgetRemoteViewsFactory(Context context) {
            this.mContext = context;
            this.mAppDatabase = AppDatabase.getInstance(mContext);
    }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public void onDataSetChanged() {
            Video video = mAppDatabase.favoritesDao().loadVideoValue();
            if (video != null) {
                mIngredientList.clear();
                mIngredientList.addAll(video.getIngredients());
            }
        }

        @Override
        public int getCount() {
            if (mIngredientList == null) {
                return 0;
            } else {
                return mIngredientList.size();
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
            Ingredient ingredient = mIngredientList.get(position);
            String string = String.format("%s %s of %s",
                    ingredient.getQuantity(),
                    ingredient.getMeasure(),
                    ingredient.getIngredient());
            views.setTextViewText(R.id.ingredient_text_view, string);
            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.ingredient_text_view, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
