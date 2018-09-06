package com.magarex.bigchef.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityRecipeBinding;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.ui.base.BaseActivity;
import com.magarex.bigchef.ui.detail.RecipeDetailActivity;
import com.magarex.bigchef.util.GridSpacingItemDecoration;
import com.magarex.bigchef.viewmodel.RecipeViewModel;


import org.parceler.Parcels;

import javax.inject.Inject;

import static com.magarex.bigchef.util.AppUtils.dpToPx;

public class RecipeActivity extends BaseActivity<ActivityRecipeBinding> implements RecipeItemClickListener {

    private RecipeAdapter recipeAdapter;

    @Inject
    RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareRecyclerView();
        loadRecipes();
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

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        Bundle recipeData = new Bundle();
        recipeData.putParcelable("recipe", Parcels.wrap(recipe));
        intent.putExtras(recipeData);
        startActivity(intent);
    }
}