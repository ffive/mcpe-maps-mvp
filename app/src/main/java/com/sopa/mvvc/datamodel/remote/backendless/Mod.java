package com.sopa.mvvc.datamodel.remote.backendless;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Set;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AndreiPiatosin on 02-Feb-17.
 */

public class Mod extends RealmObject implements Comparable<Mod>, Serializable{

    //  @SerializedName("p")
    private boolean p;
    //@SerializedName("i_url")
    private String i_url;
    //@SerializedName("mod_url")
    private String mod_url;
    //@SerializedName("mod_urlb")
    private String mod_urlb;


    @PrimaryKey
    private String objectId;
    private String ownerId;
    private long created;
    private long updated;

    private String description;

    private boolean rated;
    private boolean cached;
    private boolean locked;
    private String name;
    private String category;


    private Double rating;
    private Integer downloads=0;
    private Integer totalRates;
    // @SerializedName("___class")
    private String __class = getClass().getSimpleName();

    public Mod() {

    }


    public Mod(String name, String img, String category, String description) {
        this.name = name;
        this.i_url = img;
        this.category = category;
        this.description = description;
    }

    //ratings stuff
    public Integer getTotalRates() {
        return totalRates;
    }

    public void setTotalRates(Integer totalRates) {
        this.totalRates = totalRates;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean _cached) {
        cached = _cached;
    }

    public boolean isP() {
        return p;
    }

    public void setP(boolean p) {
        this.p = p;
    }

    public String getMap_url() {
        return mod_url;
    }

    public void setMap_url(String map_url) {
        this.mod_url = map_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getI_url() {
        return i_url;
    }

    public void setI_url(String i_url) {
        this.i_url = i_url;
    }

    public String getMap_urlb() {
        return mod_urlb;
    }

    public void setMap_urlb(String mod_urlb) {
        this.mod_urlb = mod_urlb;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String get__class() {
        return __class;
    }

    public void set__class(String __class) {
        this.__class = __class;
    }

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




    @Override
    public int compareTo(@NonNull Mod mod) {
        return isP() ? (mod.isP() ? 0 : 1) : (mod.isP() ? -1 : -0);
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }
}
