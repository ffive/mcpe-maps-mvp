package com.sopa.mvvc.datamodel.remote.backendless;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yurikomlev on 09.12.16.
 */

public class Category extends RealmObject {

    private String category;
    @PrimaryKey
    private String objectId;
    private String ownerId;
    private long created;
    private long updated;

    public Category() {
        super();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
