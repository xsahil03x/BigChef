package com.magarex.bigchef.ui.detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.FragmentRecipeDetailBinding;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.model.Step;
import com.magarex.bigchef.ui.base.BaseFragment;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerActivity;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerFragment;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeDetailFragment extends BaseFragment<FragmentRecipeDetailBinding> implements StepItemClickListener {

    private RecipeIngredientsAdapter ingredientsAdapter;
    private RecipeStepAdapter stepAdapter;
    private Dialog ingredientDialog;
    private Recipe recipe;
    private Boolean isTablet;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setTablet(Boolean tablet) {
        isTablet = tablet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (recipe != null) {
            ingredientDialog = new Dialog(Objects.requireNonNull(getActivity()));
            prepareStepsRecyclerView();
            prepareIngredientDialog();
            getDataBinding().setRecipe(recipe);
            ingredientsAdapter.addIngredientsToList(recipe.getIngredients());
            stepAdapter.addIngredientsToList(recipe.getSteps());
        }
        getDataBinding().fabIngredient.setOnClickListener(v -> ingredientDialog.show());
    }

    private void prepareIngredientDialog() {
        ingredientDialog.setContentView(R.layout.dialog_recipe_ingredients);
        ingredientDialog.setCancelable(true);
        ingredientDialog.setCanceledOnTouchOutside(true);
        ingredientDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ingredientsAdapter = new RecipeIngredientsAdapter();
        RecyclerView rvIngredients = ingredientDialog.findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvIngredients.setItemAnimator(new DefaultItemAnimator());
        rvIngredients.setAdapter(ingredientsAdapter);
        rvIngredients.setHasFixedSize(true);
        Objects.requireNonNull(ingredientDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void prepareStepsRecyclerView() {
        stepAdapter = new RecipeStepAdapter(this);
        RecyclerView rvSteps = getDataBinding().rvSteps;
        rvSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSteps.setItemAnimator(new DefaultItemAnimator());
        rvSteps.setAdapter(stepAdapter);
        rvSteps.setHasFixedSize(true);
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_recipe_detail;
    }

    @Override
    public void onClick(ArrayList<Step> step, int position) {
        if (!isTablet) {
            Intent intent = new Intent(getActivity(), ExoPlayerActivity.class);
            Bundle recipeData = new Bundle();
            recipeData.putParcelableArrayList("step", step);
            recipeData.putInt("stepPosition", position);
            recipeData.putString("recipeName", recipe.getName());
            recipeData.putBoolean("isTablet", isTablet);
            intent.putExtras(recipeData);
            startActivity(intent);
        } else {
            ExoPlayerFragment fragment = new ExoPlayerFragment();
            fragment.setStep(step.get(position));
            fragment.setTablet(isTablet);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameExoPlayer, fragment)
                    .commit();
        }
    }
}
