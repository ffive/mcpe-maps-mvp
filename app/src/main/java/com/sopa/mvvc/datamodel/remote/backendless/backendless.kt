package com.sopa.mvvc.datamodel.remote.backendless

import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by yurikomlev on 03.02.17.
 */

class BackendlessObjectKt : RealmObject(), Serializable {
/*
    // @SerializedName("___class")
    var __class = javaClass.simpleName

    @PrimaryKey
    var objectId: String? = null
    var ownerId: String? = null
    var created: Long = 0
    var updated: Long = 0

    var type: String
        get() = __class
        set(type) {
            this.__class = type
        }
}


class Category : RealmObject(), Serializable {

    var category: String? = null
    var category_ru: String? = null
    private var maps: RealmList<Map>? = null



    fun setMaps(maps: List<Map>) {
        this.maps!!.addAll(maps)
    }


    fun setMaps(maps: RealmList<Map>) {
        this.maps = maps
    }

    @PrimaryKey
    var objectId: String? = null
    var ownerId: String? = null
    var created: Long = 0
    var updated: Long = 0
}


class HouseAd : RealmObject {

    constructor(msg: String, link: String, pkg: String) {
        this.msg = msg
        this.link = link
        this.pkg = pkg
    }

    var isShown: Boolean = false
    var msg: String? = null
    var link: String? = null
    var pkg: String? = null

    var img: String? = null

    var __class = javaClass.simpleName

    constructor() : super() {
    }

    @PrimaryKey
    var objectId: String? = null
    var ownerId: String? = null
    var created: Long = 0
    var updated: Long = 0

}

class Map : RealmObject, Comparable<Map>, Serializable {


    //  @SerializedName("p")
    var isP: Boolean = false
    //@SerializedName("i_url")
    var i_url: String? = null
    //@SerializedName("map_url")
    var map_url: String? = null
    //@SerializedName("map_urlb")
    var map_urlb: String? = null


    @PrimaryKey
    var objectId: String? = null
    var ownerId: String? = null
    var created: Long = 0
    var updated: Long = 0

    var description: String? = null

    var isRated: Boolean = false
    var isCached: Boolean = false
    var isLocked: Boolean = false
    var name: String? = null
    var category: String? = null


    var rating: Double? = null
    var downloads: Int? = 0
    //ratings stuff
    var totalRates: Int? = null
    // @SerializedName("___class")
    var __class = javaClass.simpleName

    constructor() {

    }

    constructor(name: String, img: String, category: String, description: String) {
        this.name = name
        this.i_url = img
        this.category = category
        this.description = description
    }

    var type: String
        get() = __class
        set(type) {
            this.__class = type
        }


    override fun compareTo(map: Map): Int {
        return if (isP) if (map.isP) 0 else 1 else if (map.isP) -1 else -0
    }


    class Mod : RealmObject, Comparable<Mod>, Serializable {

        //  @SerializedName("p")
        var isP: Boolean = false
        //@SerializedName("i_url")
        var i_url: String? = null
        //@SerializedName("mod_url")
        var map_url: String? = null
        //@SerializedName("mod_urlb")
        var map_urlb: String? = null


        @PrimaryKey
        var objectId: String? = null
        var ownerId: String? = null
        var created: Long = 0
        var updated: Long = 0

        var description: String? = null

        var isRated: Boolean = false
        var isCached: Boolean = false
        var isLocked: Boolean = false
        var name: String? = null
        var category: String? = null


        var rating: Double? = null
        var downloads: Int? = 0
        //ratings stuff
        var totalRates: Int? = null
        // @SerializedName("___class")
        var __class = javaClass.simpleName

        constructor() {

        }


        constructor(name: String, img: String, category: String, description: String) {
            this.name = name
            this.i_url = img
            this.category = category
            this.description = description
        }

        var type: String
            get() = __class
            set(type) {
                this.__class = type
            }


        override fun compareTo(mod: Mod): Int {
            return if (isP) if (mod.isP) 0 else 1 else if (mod.isP) -1 else -0
        }
    }


    class UploadMap : RealmObject(), Serializable {

        var __class: String? = null
        var name: String? = null
        var description: String? = null
        var url: String? = null
        var author: String? = null
        var category: String? = null

    }
*/
}


