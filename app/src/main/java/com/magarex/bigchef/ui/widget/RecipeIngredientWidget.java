package com.magarex.bigchef.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.magarex.bigchef.R;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.repository.PreferenceRepository;
import com.magarex.bigchef.ui.main.RecipeActivity;
import com.magarex.bigchef.util.GlideApp;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidget extends AppWidgetProvider {

    @Inject
    PreferenceRepository prefRepo;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Recipe recipe) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.recipe_ingredient_widget);

        // launch app when button is clicked
        Intent appOpenIntent = new Intent(context, RecipeActivity.class);
        appOpenIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent addIngredientPi = PendingIntent.getActivity(context, 0, appOpenIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.btnAddIngredient, addIngredientPi);

        setViews(context, remoteViews, appWidgetId, recipe);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private static void setViews(Context context, RemoteViews remoteViews, int appWidgetId, Recipe recipe) {
        if (recipe == null) {
            Timber.d("Recipe is null");
        } else {
            remoteViews.setViewVisibility(R.id.btnAddIngredient, View.GONE);
            remoteViews.setViewVisibility(R.id.layoutRecipeWidget, View.VISIBLE);

            remoteViews.setTextViewText(R.id.tvRecipeName, recipe.getName());

            Intent listViewIntent = new Intent(context, IngredientListWidgetService.class);
            listViewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            listViewIntent.setData(Uri.parse(listViewIntent.toUri(Intent.URI_INTENT_SCHEME)));
            remoteViews.setRemoteAdapter(R.id.lvRecipeIngredient, listViewIntent);

            AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, R.id.ivRecipe, remoteViews, appWidgetId) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);
                }
            };

            GlideApp.with(context.getApplicationContext())
                    .asBitmap()
                    .load(recipe.getImageId())
                    .apply(RequestOptions.circleCropTransform())
                    .into(appWidgetTarget);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Recipe recipe = prefRepo.getIngredientsWidgetRecipe(appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
            prefRepo.deleteIngredientsWidgetRecipe(appWidgetId);
    }
}

