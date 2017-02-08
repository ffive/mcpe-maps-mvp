package com.sopa.mvvc.mvp.presenter.helpers.language;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.sopa.mvvc.datamodel.local.UserConfig;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

@InjectViewState
public class LanguagePresenter extends MvpPresenter<LanguageView> {

    private Realm realm;
    private UserConfig userConfig;
    private java.util.Map<String, String> servedLocales;

    public LanguagePresenter() {
        realm = Realm.getDefaultInstance ( );
        userConfig = realm.where (UserConfig.class).findFirst( );
        servedLocales = new HashMap<>();
    }

    public void loadAvailableLanguages ( ) {

        getViewState().showLanguageChooserDialog();

        userConfig = realm.where (UserConfig.class).findFirst ( );
        String userLang = userConfig.getLanguage();

        Backendless.Events.dispatch ("getAvailableLanguages", new HashMap(), new AsyncCallback<Map>( ) {
            @Override
            public void handleResponse ( java.util.Map map ) {

                servedLocales.putAll (map);

                getViewState().setLanguagesList (map, userLang);

            }

            @Override
            public void handleFault ( BackendlessFault backendlessFault ) {

            }
        });

    }

    public void onLanguageSelected ( int dialogPosition ) {
       // getViewState().sendLanguage( servedLocales.get (dialogPosition));

        realm.executeTransaction(realm1 -> {

            userConfig.setLanguage (servedLocales.get (dialogPosition));
            realm1.copyToRealmOrUpdate(userConfig);
        });


    }
}
