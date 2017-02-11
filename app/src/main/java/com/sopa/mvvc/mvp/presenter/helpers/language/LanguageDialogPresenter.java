package com.sopa.mvvc.mvp.presenter.helpers.language;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.sopa.mvvc.datamodel.local.UserConfig;

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

        Backendless.Persistence.of( "Dictionary" ).find( new AsyncCallback<BackendlessCollection<Map>>(){
            @Override
            public void handleResponse( BackendlessCollection<Map> foundContacts )
            {
                // every loaded object from the "Contact" table is now an individual java.util.Map
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });

    }
}
