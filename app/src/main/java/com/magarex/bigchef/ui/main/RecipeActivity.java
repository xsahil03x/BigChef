package com.magarex.bigchef.ui.main;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityRecipeBinding;
import com.magarex.bigchef.ui.base.BaseActivity;
import com.magarex.bigchef.util.GridSpacingItemDecoration;
import com.magarex.bigchef.viewmodel.RecipeViewModel;

import javax.inject.Inject;

import static com.magarex.bigchef.util.AppUtils.dpToPx;

public class RecipeActivity extends BaseActivity<ActivityRecipeBinding> {

    private RecipeAdapter recipeAdapter;

    @Inject
    RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
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

}