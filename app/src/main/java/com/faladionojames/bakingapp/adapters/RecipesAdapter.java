package com.faladionojames.bakingapp.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faladionojames.bakingapp.R;
import com.faladionojames.bakingapp.models.Recipe;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class RecipesAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Recipe> recipes;
    private Activity activity;
    View.OnClickListener listener;
    public RecipesAdapter(Activity activity, List<Recipe> recipes, View.OnClickListener listener)
    {
        this.activity=activity;
        this.recipes=recipes;
        this.listener=listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.recipe_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.name.setText(recipe.getName());
        holder.cardView.setCardBackgroundColor(getBackground(recipe));
        holder.itemView.setTag(recipe);
        holder.itemView.setOnClickListener(listener);

    }

    public int getBackground(Recipe recipe)
    {
        int[] array=activity.getResources().getIntArray(R.array.colors);
        return array[recipe.getId()];
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.card)
    CardView cardView;
    ViewHolder(View v)
    {
        super(v);
        ButterKnife.inject(this,v);
    }
}
