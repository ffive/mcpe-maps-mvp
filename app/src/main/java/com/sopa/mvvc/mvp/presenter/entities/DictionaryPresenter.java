package com.sopa.mvvc.mvp.presenter.entities;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;
import com.sopa.mvvc.mvp.view.entities.DictionaryView;
import com.sopa.mvvc.mvp.view.entities.UserConfigView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by AndreiPiatosin on 12-Feb-17.
 */
@InjectViewState
public class DictionaryPresenter extends MvpPresenter<DictionaryView> {

    private final static String TAG = "DictionaryPresenterGlob";
    private Realm realm;
    private Dictionary dictionary;
    private int version = 1;  // todo: to use with migrations when we change the set of classes stored in realm - old db should be deleted by version (of the app mb)

    public DictionaryPresenter(){
        super();

        realm = Realm.getDefaultInstance();

        RealmResults<Dictionary> dictionaries = realm.where(Dictionary.class).equalTo("language","active").findAll();
        dictionary = dictionaries.get(0);
        dictionary.addChangeListener(new RealmChangeListener<Dictionary>() {
            @Override
            public void onChange(Dictionary dictionary) {
                Log.d(TAG, "onChangeDictionary: ");
                getViewState().onUpdateDictionary( dictionary );
            }
        });

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }

}
