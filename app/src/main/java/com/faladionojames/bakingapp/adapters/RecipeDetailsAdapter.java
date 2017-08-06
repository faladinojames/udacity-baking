package com.faladionojames.bakingapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faladionojames.bakingapp.R;
import com.faladionojames.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<StepViewHolder> {
    private Activity activity;
    private String  ingredients;
    private List<RecipeStep> steps;

    View.OnClickListener listener;
    public RecipeDetailsAdapter(Activity activity, String ingredients, List<RecipeStep> steps, View.OnClickListener listener)
    {
        this.activity=activity;
        this.ingredients=ingredients;
        this.steps=steps;

        this.listener=listener;
    }
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepViewHolder(activity.getLayoutInflater().inflate(R.layout.recipe_list_content,parent,false));
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        if(position==0)
        {
            holder.name.setText(ingredients);
        }
        else
        {
            RecipeStep recipeStep= steps.get(position-1);
            holder.name.setText(recipeStep.getShortDesc());
            holder.itemView.setTag(recipeStep);
            holder.itemView.setOnClickListener(listener);
        }


    }

    @Override
    public int getItemCount() {
        return steps.size()+1;
    }
}

class StepViewHolder extends RecyclerView.ViewHolder{
    @InjectView(R.id.name)
    TextView name;
    public StepViewHolder(View view)
    {
        super(view);
        ButterKnife.inject(this,view);

    }
}
