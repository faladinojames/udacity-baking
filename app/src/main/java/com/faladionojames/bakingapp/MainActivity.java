package com.faladionojames.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.faladionojames.bakingapp.adapters.RecipesAdapter;
import com.faladionojames.bakingapp.models.Recipe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BakingActivity {

    @InjectView(R.id.recycler)
    RecyclerView recyclerView;
    
    static boolean active;
    Manager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        active=true;
        manager=new Manager(getApplicationContext());
        if (manager.getRecipes()==null)
        {
            loadRecipes();
        }
        else setUpAdapter(manager.getRecipes());

        
    }

    private void setUpAdapter(List<Recipe> recipes)
    {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipesAdapter(this, recipes, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RecipeStepsActivity.class).putExtra(Constants.RECIPE,((Recipe)view.getTag()).getJSONString()));
            }
        }));
    }

    public void loadRecipes()
    {


        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                
                                onPostExecute(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s!=null) {
                    manager.storeRecipes(s);
                    setUpAdapter(manager.getRecipes());
                    
                }
            }
        }.execute();

    }



}
