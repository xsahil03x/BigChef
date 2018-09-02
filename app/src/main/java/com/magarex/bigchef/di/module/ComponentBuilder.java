package com.magarex.bigchef.di.module;

import com.magarex.bigchef.di.scope.PerActivity;
import com.magarex.bigchef.ui.main.RecipeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ComponentBuilder {

    // Activities

    @PerActivity
    @ContributesAndroidInjector()
    public abstract RecipeActivity bindMainActivity();

//    @PerActivity
//    @ContributesAndroidInjector()
//    public abstract DetailActivity bindDetailActivity();
//
//    // Fragments
//
//    @PerFragment
//    @ContributesAndroidInjector()
//    public abstract RecipeStepFragment bindRecipeStepFragment();
//
//    @PerFragment
//    @ContributesAndroidInjector()
//    public abstract StepDetailFragment bindStepDetailFragment();
//
//    // Broadcast Receivers
//
//    @PerBroadcastReciever
//    @ContributesAndroidInjector()
//    public abstract RecipeIngredientsWidget bindRecipeIngredientsWidget();
//
//    // Services
//
//    @PerService
//    @ContributesAndroidInjector()
//    public abstract IngredientsListWidgetService bindIngredientsListWidgetService();
}
