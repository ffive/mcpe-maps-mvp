package com.sopa.mvvc.presentation.presenter.blank;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sopa.mvvc.datamodel.Map;
import com.sopa.mvvc.presentation.view.blank.DetailsView;

import io.realm.Realm;
import io.realm.RealmChangeListener;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {
    Map map;
    Realm realm;

    public DetailsPresenter(String mapId) {
        super();

        realm = Realm.getDefaultInstance();
        map = realm.where(Map.class)
                      .equalTo ("objectId", mapId).findFirstAsync();
        map.addChangeListener(new RealmChangeListener<Map>() {
            @Override
            public void onChange(Map m) {
                getViewState().bindMap(m);
            }
        });
    }


    void onDownloadClicked() {
    }

    void onRatingChanged() {
    }


    public void download(String url1, String url2) {

    }


    public void bindMap() {

    }

    public void unlock() {
    }

    public void unlock(Map map) {
    }

    public void openApp() {
    }

    public void deleteMap() {
    }
}
