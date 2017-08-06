package com.faladionojames.bakingapp.models;

import java.io.Serializable;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class RecipeStep implements Serializable {
    private String shortDesc,desc,videoUrl;
    private int id;
    public RecipeStep(int id, String shortDesc, String desc, String videoUrl)
    {
        this.id=id;
        this.shortDesc=shortDesc;
        this.desc=desc;
        this.videoUrl=videoUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getDesc() {
        return desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getId() {
        return id;
    }
}
