package com.sopa.mvvc.datamodel;

import android.support.annotation.NonNull;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 *
 */
public class Map extends RealmObject implements  Comparable<Map>, Serializable{


  //  @SerializedName("p")
    private boolean p;
    //@SerializedName("i_url")
    private String i_url;
    //@SerializedName("map_url")
    private String map_url;
    //@SerializedName("map_urlb")
    private String map_urlb;


    @PrimaryKey
    private String objectId;
    private String ownerId;
    private long created;
    private long updated;

    private boolean rated;
    private boolean cached;
    private boolean locked;
    private String name;
    private String category;
    private String description;
    private String description_ru;


    private Double rating;
    private Integer downloads=0;
    private Integer totalRates;

    //ratings stuff
    public Integer getTotalRates() {
        return totalRates;
    }

    public void setTotalRates(Integer totalRates) {
        this.totalRates = totalRates;
    }

    public String getDescription_ru() {
        return description_ru;
    }

    public void setDescription_ru(String description_ru) {
        this.description_ru = description_ru;
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



    public Map() {

    }

    public Map(String name, String img, String category, String description) {
        this.name = name;
        this.i_url = img;
        this.map_url = "https://www.dropbox.com/s/qrzjz0ebd9r8e1z/Goenitz's%20Galeon.zip?dl=1";
        this.category = category;
        this.description = description;
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
        return map_url;
    }

    public void setMap_url(String map_url) {
        this.map_url = map_url;
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

    public String getDescription_Ru() {
        return description_ru;
    }

    public void setDescription_Ru(String description_ru) {
        this.description_ru = description_ru;
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
        return map_urlb;
    }

    public void setMap_urlb(String map_urlb) {
        this.map_urlb = map_urlb;
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

   // @SerializedName("___class")
    private String __class = getClass().getSimpleName();


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
    public int compareTo(@NonNull Map map) {
        return isP() ? (map.isP() ? 0 : 1) : (map.isP() ? -1 : -0);
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }
}
