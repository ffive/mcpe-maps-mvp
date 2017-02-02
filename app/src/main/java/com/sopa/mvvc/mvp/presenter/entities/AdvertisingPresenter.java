package com.sopa.mvvc.mvp.presenter.entities;


import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;
import com.sopa.mvvc.datamodel.remote.backendless.HouseAd;
import com.sopa.mvvc.mvp.view.entities.AdvertisingView;

import io.realm.Realm;
import io.realm.RealmChangeListener;

@InjectViewState
public class AdvertisingPresenter extends MvpPresenter<AdvertisingView> {
    private String TAG = "AdvertisingPresenter";

    Realm realm;
    HouseAd ad;

    public AdvertisingPresenter() {
        super();
        realm = Realm.getDefaultInstance();


        ad = realm.where(HouseAd.class).findFirstAsync();
        ad.addChangeListener(new RealmChangeListener<HouseAd>() {
            @Override
            public void onChange(HouseAd element) {
                showAdsAsyncTask(element);
                Log.d(TAG, "onChange:HouseAd realmtable changed and triggered onChange in Advertising Presenter  (probably changed from categ presenter onCardClicked to replace  google nigga imgurl");
            }
        });
        //TODO   First ad only for now)
        Backendless.Persistence.of(HouseAd.class).findFirst(new BackendlessCallback<HouseAd>() {
            @Override
            public void handleResponse(HouseAd response) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(response);
                    }
                });
            }
        });

    }

    public void onClicked() {
        Log.d(TAG, "onClicked: ");
    }


    private void showAdsAsyncTask(HouseAd houseAd) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);

                }
                return null;

            }

            @Override
            protected void onPreExecute() {
                getViewState().showAdsLoading();
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                getViewState().setAdProgress(5 - (int) values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                getViewState().inflateAds(ad);
            }
        }.execute();
    }
}
