package com.sopa.mvvc.ui.custom_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.R;
import com.sopa.mvvc.datamodel.remote.backendless.HouseAd;
import com.sopa.mvvc.mvp.presenter.entities.AdvertisingPresenter;
import com.sopa.mvvc.mvp.view.entities.AdvertisingView;

public class AdvertisingFragment extends MvpAppCompatFragment implements AdvertisingView {
    public static final String TAG = "AdvertisingFragment";
    @InjectPresenter
    AdvertisingPresenter mAdvertisingPresenter;

    public static AdvertisingFragment newInstance() {
        AdvertisingFragment fragment = new AdvertisingFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advertising, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void inflateAds(HouseAd houseAd) {

    }

    @Override
    public void hideAds() {

    }

    @Override
    public void showAdsLoading() {

    }

    @Override
    public void setAdProgress(int value) {

    }
}
