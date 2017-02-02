package com.sopa.mvvc.mvp.view.entities;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.remote.backendless.HouseAd;

public interface AdvertisingView extends MvpView {

    public void inflateAds(HouseAd houseAd);
    public void hideAds();
    public void showAdsLoading();

    void setAdProgress(int value);
}
