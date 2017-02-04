package com.sopa.mvvc;

import android.app.Application;

import com.backendless.Backendless;

import io.realm.Realm;

/**
 * Created by yurikomlev on 13.12.16.
 */

public class BackendlessApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Backendless.initApp(getApplicationContext(), "5866D097-2401-59A4-FFE4-5A242A79D100", "1735093B-9AB4-C4ED-FFC9-68B76DB74D00", "v0");
    }
}
