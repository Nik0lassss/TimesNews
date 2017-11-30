package com.chirkevich.nikola.testdb.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Колян on 29.05.2017.
 */

public class Media {
    public Integer id;
    public String type;
    public String subtype;
    public String caption;
    public String copyright;
    @SerializedName("media-metadata")
    @Expose
    public List<Mediametadata> media_metadata;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    public List<Mediametadata> getMedia_metadata() {
        return media_metadata;
    }

    public void setMedia_metadata(List<Mediametadata> media_metadata) {
        this.media_metadata = media_metadata;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
