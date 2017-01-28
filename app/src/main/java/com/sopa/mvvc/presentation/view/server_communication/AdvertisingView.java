package com.sopa.mvvc.presentation.view.server_communication;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.HouseAd;

public interface AdvertisingView extends MvpView {

    public void inflateAds(HouseAd houseAd);
    public void hideAds();
    public void showAdsLoading();

    void setAdProgress(int value);
}
