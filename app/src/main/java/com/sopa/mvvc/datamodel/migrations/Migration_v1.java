package com.sopa.mvvc.datamodel.migrations;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by yurikomlev on 15.12.16.
 */

public class Migration_v1 implements RealmMigration {

    private long userId=0;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

        // Migrate to version 1: Add a new class.
        // Example:
        // public Person extends RealmObject {
        //     private String name;
        //     private int age;
        //     // getters and setters left out for brevity
        // }
        // if (oldVersion == 0) {
        //    schema.create("Author")
        //          .addField("name", String.class)
        //      .addField("age", int.class);
        //oldVersion++;
        //}

        // Migrate to version 2: Add a primary key + object references
        // Example:
        // public Person extends RealmObject {
        //     private String name;
        //     @PrimaryKey
        //     private int age;
        //     private Dog favoriteDog;
        //     private RealmList<Dog> dogs;
        //     // getters and setters left out for brevity
        // }
        if (newVersion == 1) {
            // schema.get("UserConfig").addField("id", long.class, FieldAttribute.PRIMARY_KEY);
            realm.beginTransaction();
            DynamicRealmObject dynamicRealmObject = realm.createObject("UserConfig", userId);
            dynamicRealmObject.set("lastTab",0);
            dynamicRealmObject.set("recyclerPosition",0f);
            realm.commitTransaction();
        }

        //

        if (oldVersion == 2) {
            // reserved for copyingnext migration
        }
    }
}

