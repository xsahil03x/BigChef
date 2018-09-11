package com.magarex.bigchef.di.module;

import android.app.Application;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.magarex.bigchef.api.RecipeService;
import com.magarex.bigchef.repository.PreferenceRepository;
import com.magarex.bigchef.repository.RecipeRepository;
import com.magarex.bigchef.viewmodel.RecipeViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {

    @Provides
    @Singleton
    static SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    static PreferenceRepository providePreferenceRepository(SharedPreferences sharedPreferences, Gson gson) {
        return new PreferenceRepository(sharedPreferences, gson);
    }

    @Provides
    @Singleton
    static GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @Singleton
    static RecipeService provideRecipeApi(Retrofit retrofit) {
        return retrofit.create(RecipeService.class);
    }

    @Provides
    @Singleton
    static RecipeRepository provideRecipeRepository(RecipeService recipeService) {
        return new RecipeRepository(recipeService);
    }

    @Provides
    @Singleton
    static RecipeViewModel provideRecipeViewModel(RecipeRepository recipeRepository) {
        return new RecipeViewModel(recipeRepository);
    }

}