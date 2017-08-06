package com.faladionojames.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.faladionojames.bakingapp.Constants;
import com.faladionojames.bakingapp.MainActivity;
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


    Manager manager;
    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;
    Context context;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {


        this.context=context;
        this.appWidgetManager=appWidgetManager;
        this.appWidgetIds=appWidgetIds;
        manager= new Manager(context);

        if(manager.getRecipes()==null)
        {
            getRecipes(context);
        }

        else{
                loadIngredients(manager.getRecipes().get(manager.getLastViewedRecipe()));
            }
        }




    private void loadIngredients(Recipe recipe) {
        ComponentName thisWidget = new ComponentName(context,
                Provider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            String s = "";

            for (Ingredient ingredient : recipe.getIngredients()) {
                s += ingredient.getIngredient() + "\n";
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
    protected void getRecipes(final Context context)
    {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        manager.storeRecipes(response);
                        loadIngredients(manager.getRecipes().get(manager.getLastViewedRecipe()));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Unable to load recipes, please check your network", Toast.LENGTH_SHORT).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}
