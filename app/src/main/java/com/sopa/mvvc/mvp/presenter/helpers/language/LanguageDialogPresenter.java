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
import io.realm.RealmResults;

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

        Realm realm = Realm.getDefaultInstance();
        String userLang = realm.where (UserConfig.class).findFirst().getLanguage();

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

        Realm realm = Realm.getDefaultInstance();

        boolean isNeedToRetrieveFromBackend = true;

        RealmResults<Dictionary> dicitionaries = realm.where(Dictionary.class).findAll();
        for (Dictionary dictionary : dicitionaries){
            if (dictionary.language.equals(newLanguage)){
                isNeedToRetrieveFromBackend = false;
            }
        }

       Dictionary activeDictionary = realm.where(Dictionary.class)
               .equalTo("language", "active").findAll().get(0);


        if (isNeedToRetrieveFromBackend) {

            String whereClause = "language = '" + newLanguage + "'";
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);

            Backendless.Persistence.of(Dictionary.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Dictionary>>() {
                @Override
                public void handleResponse(BackendlessCollection<Dictionary> foundDictionary) {

                    realm.executeTransaction(realm1 -> {

                        //Changing active dictionary
                        realm1.copyToRealmOrUpdate(updateDictionary(activeDictionary, foundDictionary.getCurrentPage().get(0)));

                        //Creating new realmDictionary
                        realm1.copyToRealmOrUpdate(foundDictionary.getCurrentPage().get(0));

                    });
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });

        }   else {
            Dictionary newLangDictionary = realm.where(Dictionary.class)
                    .equalTo("language", newLanguage).findAll().get(0);

            //Changing active dictionary
            realm.copyToRealmOrUpdate(updateDictionary(activeDictionary, newLangDictionary));

        }

    }



    private Dictionary updateDictionary( Dictionary dictionaryFrom, Dictionary dictionaryTo ){

/*        if (!isActive) {
            dictionaryFrom.language = dictionaryTo.language;
        }*/
        dictionaryFrom.app_name=dictionaryTo.app_name;
        dictionaryFrom.i_am_banner=dictionaryTo.i_am_banner;
        dictionaryFrom.title_section1=dictionaryTo.title_section1;
        dictionaryFrom.title_section2=dictionaryTo.title_section2;
        dictionaryFrom.title_section3=dictionaryTo.title_section3;
        dictionaryFrom.title_section4=dictionaryTo.title_section4;
        dictionaryFrom.hello_world=dictionaryTo.hello_world;
        dictionaryFrom.action_settings=dictionaryTo.action_settings;
        dictionaryFrom.drawer_item_home=dictionaryTo.drawer_item_home;
        dictionaryFrom.maps=dictionaryTo.maps;
        dictionaryFrom.mods=dictionaryTo.mods;
        dictionaryFrom.skins=dictionaryTo.skins;
        dictionaryFrom.textures=dictionaryTo.textures;
        dictionaryFrom.drawer_item_free_play=dictionaryTo.drawer_item_free_play;
        dictionaryFrom.drawer_item_custom=dictionaryTo.drawer_item_custom;
        dictionaryFrom.drawer_item_settings=dictionaryTo.drawer_item_settings;
        dictionaryFrom.drawer_item_help=dictionaryTo.drawer_item_help;
        dictionaryFrom.drawer_item_language=dictionaryTo.drawer_item_language;
        dictionaryFrom.drawer_item_open_source=dictionaryTo.drawer_item_open_source;
        dictionaryFrom.drawer_item_contact=dictionaryTo.drawer_item_contact;
        dictionaryFrom.hello_blank_fragment=dictionaryTo.hello_blank_fragment;
        dictionaryFrom.some_text=dictionaryTo.some_text;
        dictionaryFrom.dialog_launch_title=dictionaryTo.dialog_launch_title;
        dictionaryFrom.dialog_launch_message=dictionaryTo.dialog_launch_message;
        dictionaryFrom.dialog_launch_btn_ok=dictionaryTo.dialog_launch_btn_ok;
        dictionaryFrom.dialog_launch_btn_no=dictionaryTo.dialog_launch_btn_no;
        dictionaryFrom.btn_install=dictionaryTo.btn_install;
        dictionaryFrom.progress_dialog_map_download_title=dictionaryTo.progress_dialog_map_download_title;
        dictionaryFrom.dialog_howto_title=dictionaryTo.dialog_howto_title;
        dictionaryFrom.dialog_howto_message=dictionaryTo.dialog_howto_message;
        dictionaryFrom.rate_btn_cancel=dictionaryTo.rate_btn_cancel;
        dictionaryFrom.rate_btn_ok=dictionaryTo.rate_btn_ok;
        dictionaryFrom.rate_dialog_title=dictionaryTo.rate_dialog_title;
        dictionaryFrom.rate_thanks=dictionaryTo.rate_thanks;
        dictionaryFrom.toast_error_map_download=dictionaryTo.toast_error_map_download;
        dictionaryFrom.toast_error_list_download=dictionaryTo.toast_error_list_download;
        dictionaryFrom.title_section5=dictionaryTo.title_section5;
        dictionaryFrom.title_section6=dictionaryTo.title_section6;
        dictionaryFrom.title_section7=dictionaryTo.title_section7;
        dictionaryFrom.title_section8=dictionaryTo.title_section8;
        dictionaryFrom.title_section9=dictionaryTo.title_section9;
        dictionaryFrom.title_section10=dictionaryTo.title_section10;
        dictionaryFrom.title_section11=dictionaryTo.title_section11;
        dictionaryFrom.title_section12=dictionaryTo.title_section12;
        dictionaryFrom.rate_thanks2=dictionaryTo.rate_thanks2;
        dictionaryFrom.submit_map=dictionaryTo.submit_map;
        dictionaryFrom.rate_tounlock_message=dictionaryTo.rate_tounlock_message;
        dictionaryFrom.house_ad_ok=dictionaryTo.house_ad_ok;
        dictionaryFrom.share_tounlock_message=dictionaryTo.share_tounlock_message;
        dictionaryFrom.ratetounlock_title_nointent=dictionaryTo.ratetounlock_title_nointent;
        dictionaryFrom.rate_button_ok=dictionaryTo.rate_button_ok;
        dictionaryFrom.ratetounlock_title=dictionaryTo.ratetounlock_title;
        dictionaryFrom.description=dictionaryTo.description;
        dictionaryFrom.description3=dictionaryTo.description3;
        dictionaryFrom.description4=dictionaryTo.description4;
        dictionaryFrom.description5=dictionaryTo.description5;
        dictionaryFrom.description6=dictionaryTo.description6;
        dictionaryFrom.backgroundColor=dictionaryTo.backgroundColor;
        dictionaryFrom.app_intro=dictionaryTo.app_intro;
        dictionaryFrom.ticker=dictionaryTo.ticker;
        dictionaryFrom.ticker2=dictionaryTo.ticker2;
        dictionaryFrom.tittle=dictionaryTo.tittle;
        dictionaryFrom.tittle3=dictionaryTo.tittle3;
        dictionaryFrom.tittle4=dictionaryTo.tittle4;
        dictionaryFrom.tittle5=dictionaryTo.tittle5;
        dictionaryFrom.tittle6=dictionaryTo.tittle6;
        dictionaryFrom.tittle7=dictionaryTo.tittle7;
        dictionaryFrom.tittle8=dictionaryTo.tittle8;
        dictionaryFrom.description7=dictionaryTo.description7;
        dictionaryFrom.description8=dictionaryTo.description8;
        dictionaryFrom.email_subject=dictionaryTo.email_subject;
        dictionaryFrom.dialog_myapps_title=dictionaryTo.dialog_myapps_title;
        dictionaryFrom.dialog_myapps_message=dictionaryTo.dialog_myapps_message;
        dictionaryFrom.dialog_myapps_ok=dictionaryTo.dialog_myapps_ok;
        dictionaryFrom.dialog_myapps_no=dictionaryTo.dialog_myapps_no;
        dictionaryFrom.eula_title=dictionaryTo.eula_title;
        dictionaryFrom.eula_msg=dictionaryTo.eula_msg;
        dictionaryFrom.details=dictionaryTo.details;
        dictionaryFrom.unlock=dictionaryTo.unlock;
        dictionaryFrom.cached=dictionaryTo.cached;
        dictionaryFrom.progress_dialog_map_cache_title=dictionaryTo.progress_dialog_map_cache_title;
        dictionaryFrom.btn_unlock=dictionaryTo.btn_unlock;
        dictionaryFrom.adtoast=dictionaryTo.adtoast;
        dictionaryFrom.search_hint=dictionaryTo.search_hint;
        dictionaryFrom.search_btn_text=dictionaryTo.search_btn_text;
        dictionaryFrom.action_search=dictionaryTo.action_search;
        dictionaryFrom.ratings=dictionaryTo.ratings;
        dictionaryFrom.upload_map_name_hint=dictionaryTo.upload_map_name_hint;
        dictionaryFrom.title_activity_scrolling=dictionaryTo.title_activity_scrolling;
        dictionaryFrom.advertising=dictionaryTo.advertising;
        dictionaryFrom.language_chooser_dialog_title=dictionaryTo.language_chooser_dialog_title;
        return dictionaryFrom;
    }
}
