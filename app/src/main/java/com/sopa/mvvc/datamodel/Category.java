package com.sopa.mvvc.datamodel;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yurikomlev on 09.12.16.
 */

public class Category extends RealmObject implements Serializable{

    private String category;
    private String category_ru;
    private RealmList<Map> maps;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_ru() {
        return category_ru;
    }

    public void setCategory_ru(String category_ru) {
        this.category_ru = category_ru;
    }

    public List<Map> getMaps() {
        return maps;
    }

    public void setMaps(List<Map> maps) {
        this.maps.addAll(maps);
    }


    public void setMaps(RealmList<Map> maps) {
        this.maps = maps;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    @PrimaryKey
    private String objectId;
    private String ownerId;
    private long created;
    private long updated;

    public Category() {
        super();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
