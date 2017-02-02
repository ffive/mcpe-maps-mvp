package com.sopa.mvvc.datamodel.remote.backendless;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by AndreiPiatosin on 02-Feb-17.
 */

public class Description extends RealmObject implements Serializable {

    private String language;
    private String description;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
