package com.magarex.bigchef.di.module;

import com.magarex.bigchef.di.scope.PerActivity;
import com.magarex.bigchef.di.scope.PerFragment;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerActivity;
import com.magarex.bigchef.ui.detail.RecipeDetailActivity;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerFragment;
import com.magarex.bigchef.ui.main.RecipeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    // Activities

    @PerActivity
    @ContributesAndroidInjector()
    public abstract RecipeActivity bindMainActivity();

    @PerActivity
    @ContributesAndroidInjector()
    public abstract RecipeDetailActivity bindRecipeDetailActivity();

    @PerActivity
    @ContributesAndroidInjector()
    public abstract ExoPlayerActivity bindExoPlayerActivity();


    // Fragments

    @PerFragment
    @ContributesAndroidInjector()
    public abstract ExoPlayerFragment bindExoPlayerFragment();
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