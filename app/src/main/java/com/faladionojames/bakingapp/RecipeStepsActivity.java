package com.faladionojames.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.faladionojames.bakingapp.adapters.RecipeDetailsAdapter;
import com.faladionojames.bakingapp.models.Ingredient;
import com.faladionojames.bakingapp.models.Recipe;
import com.faladionojames.bakingapp.models.RecipeStep;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecipeStepsActivity extends BakingActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    Recipe recipe;
    static boolean active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        active=true;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        try {
            recipe = new Recipe(new JSONObject(getIntent().getStringExtra(Constants.RECIPE)));
            new Manager(this).storeLastViewedRecipe(recipe.getId());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }



    }
    
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        final List<Ingredient> ingredients = recipe.getIngredients();

        String  s="";
        for(Ingredient ingredient: ingredients)
        {
            s+=ingredient.getQuantity()+" "+ingredient.getMeasure()+" "+ingredient.getIngredient()+",";
        }
        recyclerView.setAdapter(new RecipeDetailsAdapter(this, s, recipe.getRecipeSteps(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.STEPS, (RecipeStep)view.getTag());
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(getApplicationContext(), RecipeStepDetailActivity.class);
                    intent.putExtra(Constants.STEPS, (RecipeStep)view.getTag());
                    RecipeStepDetailActivity.recipe=recipe;
                    startActivity(intent);
                }
            }
        }));
    }


}
