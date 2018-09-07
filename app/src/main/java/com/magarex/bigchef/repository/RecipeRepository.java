package com.magarex.bigchef.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.magarex.bigchef.R;
import com.magarex.bigchef.api.RecipeService;
import com.magarex.bigchef.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class RecipeRepository {

    private RecipeService recipeService;
    private int[] imageList = {
            R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheesecake
    };
    private String[] recipeTime = {"4h 10m", "1h", "1h 10m", "1h 5m"};
    private String[] recipeCalorie = {"759 Cal", "183 Cal", "380 Cal", "387 Cal"};

    @Inject
    public RecipeRepository(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        final MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();
        recipeService.getRecipes()
                .map(recipes -> {
                    for (int i = 0; i < recipes.size(); i++) {
                        recipes.get(i).setImageId(imageList[i]);
                        recipes.get(i).setCalorie(recipeCalorie[i]);
                        recipes.get(i).setTime(recipeTime[i]);
                    }
                    return recipes;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        if (recipes != null)
                            recipeList.setValue(recipes);
                        else
                            Timber.d("List is null");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Completed");
                    }
                });
        return recipeList;
    }
}
