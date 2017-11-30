package com.chirkevich.nikola.testdb.data.remote.model;

/**
 * Created by Колян on 29.05.2017.
 */

public class Mediametadata {
    public String url;
    public String format;
    public Integer height;
    public Integer width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
