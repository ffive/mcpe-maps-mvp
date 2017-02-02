package com.sopa.mvvc.datamodel.remote.backendless;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by yurikomlev on 08.12.16.
 */

public class UploadMap extends RealmObject implements Serializable {
    public UploadMap() {
        super();
    }

   private String __class;
   private String name;
   private String description;
   private String url;
   private String author;
   private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String get__class() {
        return __class;
    }

    public void set__class(String __class) {
        this.__class = __class;
    }

}
