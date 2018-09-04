package com.magarex.bigchef.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class RecipeViewModel extends ViewModel {

    private LiveData<List<Recipe>> recipes;
    private RecipeRepository recipeRepository;

    @Inject
    public RecipeViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        if (recipes != null) {
            return;
        }
        loadRecipes();
    }

    private void loadRecipes() {
        recipes = recipeRepository.getRecipeLiveData();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
