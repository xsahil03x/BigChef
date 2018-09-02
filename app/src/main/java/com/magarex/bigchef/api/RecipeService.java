package com.magarex.bigchef.api;

import com.magarex.bigchef.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RecipeService {

    @GET("android-baking-app-json")
    Observable<List<Recipe>> getRecipes();
}
