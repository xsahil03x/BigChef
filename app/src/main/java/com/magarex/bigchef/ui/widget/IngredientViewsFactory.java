package com.magarex.bigchef.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.magarex.bigchef.R;
import com.magarex.bigchef.model.Ingredient;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.repository.PreferenceRepository;

import java.util.List;

import timber.log.Timber;

public class IngredientViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> ingredientList;
    private PreferenceRepository prefRepo;
    private int appWidgetId;

    IngredientViewsFactory(Context mContext, Intent intent, PreferenceRepository prefRepo) {
        this.mContext = mContext;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        this.prefRepo = prefRepo;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Timber.d("onDataSetChanged: called");
        Recipe currentRecipe = prefRepo.getIngredientsWidgetRecipe(appWidgetId);
        if (currentRecipe != null) {
            ingredientList = currentRecipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (ingredientList == null ? 0 : ingredientList.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredientList.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout
                .ingredient_widget_item);
        rv.setTextViewText(R.id.tvIngredientName, ingredient.getIngredient());
        rv.setTextViewText(R.id.tvIngredientQuantity, ingredient.getQuantity() + " " + ingredient.getMeasure());
        return rv;
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
