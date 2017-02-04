package com.sopa.mvvc.datamodel.remote.backendless;

import java.util.Set;

/**
 * Created by AndreiPiatosin on 04-Feb-17.
 */

public class Description {
    private String availableLanguages;
    private String descriptionText;


    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(String availableLanguages) {
        this.availableLanguages = availableLanguages;
    }
}
