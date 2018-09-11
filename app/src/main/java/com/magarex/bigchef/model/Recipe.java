package com.magarex.bigchef.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.magarex.bigchef.R;
import com.magarex.bigchef.util.GlideApp;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class Recipe {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    @Expose
    private List<Step> steps;
    @SerializedName("servings")
    @Expose
    private int servings;

    private String time;
    private String calorie;
    private int imageId;

    @ParcelConstructor
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String time, String calorie, int imageId) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.time = time;
        this.calorie = calorie;
        this.imageId = imageId;
    }

    @BindingAdapter({"android:recipeImage"})
    public static void loadRecipeImage(ImageView view, int imageId) {
        GlideApp.with(view.getContext())
                .load(imageId)
                .placeholder(R.drawable.recipe_placeholder)
                .error(R.drawable.error_placeholder)
                .dontAnimate()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into(view);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public String toString() {
        return name+" "+id;
    }
}
