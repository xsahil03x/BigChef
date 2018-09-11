package com.magarex.bigchef.repository;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.magarex.bigchef.model.Recipe;

import javax.inject.Inject;

public class PreferenceRepository {

    private static final String WIDGET_PREFIX = "appwidget_";
    private SharedPreferences prefs;
    private Gson gson;

    @Inject
    public PreferenceRepository(SharedPreferences prefs, Gson gson) {
        this.prefs = prefs;
        this.gson = gson;
    }

    public void saveIngredientsWidgetRecipe(int appWidgetId, Recipe recipe) {
        prefs.edit().putString(WIDGET_PREFIX + appWidgetId, gson.toJson(recipe)).apply();
    }

    public Recipe getIngredientsWidgetRecipe(int appWidgetId) {
        String recipeJson = prefs.getString(WIDGET_PREFIX + appWidgetId, null);
        if (recipeJson == null) return null;
        return gson.fromJson(recipeJson, Recipe.class);
    }

    public void deleteIngredientsWidgetRecipe(int appWidgetId) {
        prefs.edit().remove(WIDGET_PREFIX + appWidgetId).apply();
    }
}
