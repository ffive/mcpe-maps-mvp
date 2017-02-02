package com.sopa.mvvc.network;

import java.io.Serializable;


public class BackendlessObject implements Serializable {

    public String get__class() {
        return __class;
    }

    public void set__class(String __class) {
        this.__class = __class;
    }


    private String __class = getClass().getSimpleName();

   // @PrimaryKey
    private String objectId;
    private String ownerId;
    private long created;
    private long updated;

    public String getType() {
        return get__class();
    }

    public void setType(String type) {
        this.__class = type;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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


}