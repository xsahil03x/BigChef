package com.magarex.bigchef.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.magarex.bigchef.api.RecipeService;
import com.magarex.bigchef.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RecipeRepository {

    private RecipeService recipeService;

    @Inject
    public RecipeRepository(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
        recipeService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                if (recipeList != null)
                    recipes.setValue(recipeList);
                else
                    Timber.d("List is null");
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                call.cancel();
                Timber.d(t);
            }
        });
        return recipes;
    }
}
