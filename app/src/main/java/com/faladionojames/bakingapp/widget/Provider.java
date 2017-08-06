package com.faladionojames.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.faladionojames.bakingapp.Constants;
import com.faladionojames.bakingapp.Manager;
import com.faladionojames.bakingapp.R;
import com.faladionojames.bakingapp.RecipeStepsActivity;
import com.faladionojames.bakingapp.models.Ingredient;
import com.faladionojames.bakingapp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jamesfalade on 06/08/2017.
 */

public class Provider extends AppWidgetProvider {

    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Recipe recipe = getRecipes(context).get(new Manager(context).getLastViewedRecipe());
        ComponentName thisWidget = new ComponentName(context,
                Provider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            String s="";

            for(Ingredient ingredient : recipe.getIngredients())
            {
                s+=ingredient.getIngredient()+"\n";
            }

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget);
            remoteViews.setTextViewText(R.id.text, s);
            Intent intent = new Intent(context, RecipeStepsActivity.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra(Constants.RECIPE, recipe.getJSONString());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }

    protected List<Recipe> getRecipes(Context context)
    {
        String s="";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("baking.json")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                s+=mLine;
            }

            JSONArray jsonArray = new JSONArray(s);
            List<Recipe> recipes = new ArrayList<>();

            for(int i=0; i<jsonArray.length(); i++)
            {
                recipes.add(new Recipe(jsonArray.getJSONObject(i)));
            }

            return recipes;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
