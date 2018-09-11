package com.magarex.bigchef.ui.main;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityRecipeBinding;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.repository.PreferenceRepository;
import com.magarex.bigchef.ui.base.BaseActivity;
import com.magarex.bigchef.ui.detail.RecipeDetailActivity;
import com.magarex.bigchef.ui.widget.RecipeIngredientWidget;
import com.magarex.bigchef.util.GridSpacingItemDecoration;
import com.magarex.bigchef.viewmodel.RecipeViewModel;


import org.parceler.Parcels;

import javax.inject.Inject;

import static com.magarex.bigchef.util.AppUtils.dpToPx;

public class RecipeActivity extends BaseActivity<ActivityRecipeBinding> implements RecipeItemClickListener {

    private RecipeAdapter recipeAdapter;
    private Boolean isFromWidget = false;
    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Inject
    RecipeViewModel recipeViewModel;

    @Inject
    PreferenceRepository preferenceRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareRecyclerView();
        loadRecipes();
        if (getIntent().getExtras() != null) {
            widgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            isFromWidget = widgetId != AppWidgetManager.INVALID_APPWIDGET_ID;
        }
    }

    private void loadRecipes() {
        recipeViewModel.getRecipes().observe(this, recipes -> {
            if (recipes != null && recipes.size() != 0) {
                recipeAdapter.addRecipeToList(recipes);
            }
        });
    }

    private void prepareRecyclerView() {
        recipeAdapter = new RecipeAdapter(this);
        RecyclerView rvRecipes = getBinding().rvRecipes;
        rvRecipes.setLayoutManager(new GridLayoutManager(RecipeActivity.this, 1));
        rvRecipes.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(this), true));
        rvRecipes.setItemAnimator(new DefaultItemAnimator());
        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setHasFixedSize(true);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_recipe;
    }

    private void updateWidget(Recipe recipe) {

        preferenceRepository.saveIngredientsWidgetRecipe(widgetId, recipe);

        RecipeIngredientWidget.updateAppWidget(
                this,
                AppWidgetManager.getInstance(this),
                widgetId,
                recipe
        );

        Intent resultIntent = new Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onClick(Recipe recipe) {
        if (!isFromWidget) {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            Bundle recipeData = new Bundle();
            recipeData.putParcelable("recipe", Parcels.wrap(recipe));
            intent.putExtras(recipeData);
            startActivity(intent);
        } else {
            updateWidget(recipe);
        }
    }
}