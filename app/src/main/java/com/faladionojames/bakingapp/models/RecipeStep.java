package com.faladionojames.bakingapp.models;

import java.io.Serializable;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class RecipeStep implements Serializable {
    private String shortDesc,desc,videoUrl,thumbnail;
    private int id;
    public RecipeStep(int id, String shortDesc, String desc, String videoUrl,String thumbnail)
    {
        this.id=id;
        this.shortDesc=shortDesc;
        this.desc=desc;
        this.videoUrl=videoUrl;
        this.thumbnail=thumbnail;
    }

    public String getThumbnail()
    {
        return thumbnail;
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
