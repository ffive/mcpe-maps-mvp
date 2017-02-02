package com.sopa.mvvc.datamodel.remote.backendless;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Komlev on 29.04.2015.
 */

public class HouseAd extends RealmObject {

    public HouseAd(String msg, String link, String pkg) {
        this.msg = msg;
        this.link = link;
        this.pkg = pkg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public boolean isShown() {

        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    private boolean shown;
    private String msg, link, pkg;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;

    public String get__class() {
        return __class;
    }

    public void set__class(String __class) {
        this.__class = __class;
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

    private String __class = getClass().getSimpleName();

    public HouseAd() {
        super();
    }

    @PrimaryKey
    private String objectId;
    private String ownerId;
    private long created;
    private long updated;

}
