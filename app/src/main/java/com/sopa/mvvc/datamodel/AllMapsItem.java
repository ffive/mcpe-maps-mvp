package com.sopa.mvvc.datamodel;

/**
 *
 */
public class AllMapsItem {

    public AllMapsItem(String name, String category) {
        this.name = name;
        this.name = category;

    }

    private String name;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}