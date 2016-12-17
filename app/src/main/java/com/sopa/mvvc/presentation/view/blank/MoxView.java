package com.sopa.mvvc.presentation.view.blank;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.Category;

import java.util.List;


public interface MoxView extends MvpView {

    void showMyAppsDialog();

    void showNewApp(String message, String link, String pkg);

    void showRateDialog();

    void showEULA();

    void showRewardedAd();

    void showSearch();

    void hideSearch();

    void showInterstitial();

    void showLoading();

    void hideLoading();

    void showTabs(List<Category> categories);


    void updateTabs(List<Category> categories,int position);


    void showUploadDialog();

    void sendLastTab(int tab);



}
