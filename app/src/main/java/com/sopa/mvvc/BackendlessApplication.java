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

        //todo apikeys de-hardcode
        Backendless.initApp(getApplicationContext(), "D6C4C848-6DBF-3FDA-FF9B-5EAC025EB500", "B69F271E-04E3-B179-FF94-7D8C68295500", "v1");

    }
}
