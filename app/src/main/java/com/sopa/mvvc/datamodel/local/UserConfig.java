package com.sopa.mvvc.datamodel.local;

import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



/**
 * Created by yurikomlev on 12.12.16.
 */

public class UserConfig extends RealmObject{

    public UserConfig() {
        super();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @PrimaryKey
    private long id;

    private String language;
    private int lastTab;
    private float recyclerPosition;
    public static Dictionary dictionary;
    private Dictionary dictionaryTest;

    public int getLastTab() {
        return lastTab;
    }

    public void setLastTab(int lastTab) {
        this.lastTab = lastTab;
    }

    public float getRecyclerPosition() {
        return recyclerPosition;
    }

    public void setRecyclerPosition(float recyclerPosition) {
        this.recyclerPosition = recyclerPosition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Dictionary getDictionaryTest() {
        return dictionaryTest;
    }

    public void setDictionaryTest(Dictionary dictionaryTest) {
        this.dictionaryTest = dictionaryTest;
    }
}
