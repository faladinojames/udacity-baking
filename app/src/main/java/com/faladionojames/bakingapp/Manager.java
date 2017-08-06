package com.faladionojames.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.faladionojames.bakingapp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class Manager {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public Manager(Context context)
    {
        this.context=context;
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        editor=preferences.edit();
    }

    public void storeLastViewedRecipe(int id)
    {
        editor.putInt("last",id);
        editor.commit();
    }

    public int getLastViewedRecipe()
    {return preferences.getInt("last",0);}


    public void storeRecipes(String data) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("recipes.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public List<Recipe> getRecipes()
    {


        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("recipes.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                JSONArray jsonArray = new JSONArray(ret);
                List<Recipe> recipes = new ArrayList<>();

                for(int i=0; i<jsonArray.length(); i++)
                {
                    recipes.add(new Recipe(jsonArray.getJSONObject(i)));
                }

                return recipes;



            }
        }
        catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;



    }



}
