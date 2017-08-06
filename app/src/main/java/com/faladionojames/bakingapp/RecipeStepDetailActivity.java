package com.faladionojames.bakingapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.faladionojames.bakingapp.models.Recipe;
import com.faladionojames.bakingapp.models.RecipeStep;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RecipeStepDetailActivity extends BakingActivity {

    @InjectView(R.id.prev)
    Button previous;
    @InjectView(R.id.next)
    Button next;
    @InjectView(R.id.layout)
    RelativeLayout layout;

    public static Recipe recipe;
    RecipeStep recipeStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            if(recipeStep==null)
            recipeStep=(RecipeStep)getIntent().getSerializableExtra(Constants.STEPS);
            setTitle(recipeStep.getShortDesc());
            arguments.putSerializable(Constants.STEPS,
                    recipeStep);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.STEPS,recipeStep);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipeStep=(RecipeStep) savedInstanceState.getSerializable(Constants.STEPS);
    }

    @OnClick(R.id.prev)
    public void previous()
    {
        if(recipeStep.getId()==0)
        {
            Snackbar.make(layout,"First Step",Snackbar.LENGTH_SHORT);
            return;
        }

        openRecipeStep(recipe.getRecipeSteps().get(recipeStep.getId()-1));
    }

    @OnClick(R.id.next)
    public void next()
    {
        if(recipeStep.getId()==recipe.getRecipeSteps().size()-1)
        {
            Snackbar.make(layout,"Last Step",Snackbar.LENGTH_SHORT);
            return;
        }

        openRecipeStep(recipe.getRecipeSteps().get(recipeStep.getId()+1));

    }

    private void openRecipeStep(RecipeStep recipeStep)
    {
        this.recipeStep=recipeStep;
        setTitle(recipeStep.getShortDesc());
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.STEPS,
                recipeStep);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, fragment)
                .commit();
    }
}
