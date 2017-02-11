package com.sopa.mvvc.mvp.presenter.helpers.language;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

@InjectViewState
public class LanguageDialogPresenter extends MvpPresenter<LanguageView> {

    private Realm realm;

    private HashMap<String, String> servedLocales;

    public LanguageDialogPresenter () {
        realm = Realm.getDefaultInstance ( );
        servedLocales = new HashMap<>();

    }


    public void loadAvailableLanguages ( ) {

        String userLang = realm.where (UserConfig.class).findFirst ( ).getLanguage();

        Backendless.Events.dispatch ("getAvailableLanguages", new HashMap(), new AsyncCallback<Map>( ) {
            @Override
            public void handleResponse ( java.util.Map map ) {

                servedLocales.putAll (map);
                getViewState().updateAvailableLanguages (map, userLang);
            }

            @Override
            public void handleFault ( BackendlessFault backendlessFault ) {

            }
        });

    }



    public void onLanguageSelected ( String newLanguage ) {

        realm.executeTransaction(realm1 -> {

            UserConfig cfg = realm1.where (UserConfig.class).findFirst ();
            cfg.setLanguage (newLanguage);
            realm1.copyToRealmOrUpdate(cfg);
        });

        String whereClause = "language = '" + newLanguage + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );

        Backendless.Persistence.of(Dictionary.class).find( dataQuery, new AsyncCallback<BackendlessCollection<Dictionary>>(){
            @Override
            public void handleResponse( BackendlessCollection<Dictionary> foundDictionary )
            {
             //  Log.d("TEST", "handleResponse: " + foundDictionary.getCurrentPage().get(0).language);
            //    UserConfig.dictionary = foundDictionary.getCurrentPage().get(0);
                realm.executeTransaction(realm1 -> {

                    UserConfig cfg = realm1.where (UserConfig.class).findFirst ();
                    cfg.setDictionaryTest( foundDictionary.getCurrentPage().get(0) );
                    realm1.copyToRealmOrUpdate(cfg);
                });
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {

            }
        });

    }
}
