package com.sopa.mvvc.datamodel.local.migrations;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by yurikomlev on 15.12.16.
 *
 *  Migrations class stores STEPS needed to  local db and making changes to it.
 *
 *  Imagine we have two versions of app v1.00 , v2.0
 *  each uses slightly different columns or even tables in backend
 *  ( we can have both v1 v2 v3 ... versions of same Class table in backendless same time so users who dont update still can use their version)
 *
 *  When the update is coming - users of v1 and v2 will update to the latest.
 *  But they already have old version of LOCAL db  (realm)
 *
 *  Here I save  steps needed to migrate from v1 to v2,  from v2 to v4.20 etc. STEP BY STEP. So any user even with v1 will get no mess
 *
 *  Also here is a good place to write initial or empty data to DB.
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

            DynamicRealmObject dynamicRealmObject = realm.createObject("UserConfig", userId);
            dynamicRealmObject.set("lastTab",0);
            dynamicRealmObject.set("recyclerPosition",0f);
            dynamicRealmObject.set("language","en");


        }

        //

        if (oldVersion == 2) {
            // reserved for copyingnext migration
        }
    }
}

