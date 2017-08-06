package com.faladionojames.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.faladionojames.bakingapp.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class Recipe {
    JSONObject jsonObject;
    public Recipe(JSONObject jsonObject)
    {
        this.jsonObject=jsonObject;

    }

    public String getThumbnail()
    {
        try{
            return jsonObject.getString("image");
        }
        catch (JSONException e){e.printStackTrace();}
        return null;
    }

    public String getJSONString()
    {
        return jsonObject.toString();
    }
    public int getId()
    {
        try{
            return jsonObject.getInt(Constants.ID);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    public String getName()
    {
        try {
            return jsonObject.getString(Constants.NAME);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Ingredient> getIngredients()
    {
        try{
            List<Ingredient> ingredients=new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.INGREDIENTS);
            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ingredients.add(new Ingredient(jsonObject.getString(Constants.MEASURE),jsonObject.getString(Constants.INGREDIENT),jsonObject.getInt(Constants.QUANTITY)));
            }
            return ingredients;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecipeStep> getRecipeSteps()
    {
        try{
            List<RecipeStep> recipeSteps= new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.STEPS);
            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                recipeSteps.add(new RecipeStep(jsonObject.getInt(Constants.ID),jsonObject.getString(Constants.SHORT_DESCRIPTION),jsonObject.getString(Constants.DESCRIPTION),jsonObject.getString(Constants.VIDEO_URL),jsonObject.getString("thumbnailURL")));
            }
            return recipeSteps;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }


}
