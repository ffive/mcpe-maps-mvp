package com.sopa.mvvc.mvp.presenter.entities;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.local.migrations.Migration_v1;
import com.sopa.mvvc.mvp.view.entities.UserConfigView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;


@InjectViewState
public class UserConfigPresenter extends MvpPresenter<UserConfigView> {
    private final static String TAG = "UserConfigPresenterGlob";
    private Realm realm;
    private UserConfig config;
    private int version = 1;  // todo: to use with migrations when we change the set of classes stored in realm - old db should be deleted by version (of the app mb)

    public UserConfigPresenter() {
        super();
        RealmConfiguration zRealmConfig = new RealmConfiguration.Builder()
                .initialData(realm1 -> {
                    UserConfig initialCfg = realm1.createObject(UserConfig.class, 0L);
                    initialCfg.setLastTab(0);
                    initialCfg.setRecyclerPosition(0);
                    realm1.copyToRealm(initialCfg);
                })
                .schemaVersion(version) // Must be bumped when the schema changes
                .migration(new Migration_v1()) // Migration to run instead of throwing an exception
                .build();

        //Realm.deleteRealm(zRealmConfig);
        Realm.setDefaultConfiguration(zRealmConfig);

        realm = Realm.getDefaultInstance();

        config = realm.where(UserConfig.class).findFirstAsync();
        config.addChangeListener(new RealmChangeListener<RealmObject>() {
            @Override
            public void onChange(RealmObject element) {
                Log.e(TAG, "onChange: I am global userconfig's onchange listener -----lastTab:"+((UserConfig)element).getLastTab()+" ------!!!!!"+ ((UserConfig)element)
                        .getId());
            }
        });

    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }


    @Override
    public void attachView(UserConfigView view) {
        // view.sendLastTab(config.getLastTab());
        super.attachView(view);
    }




    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }



}
