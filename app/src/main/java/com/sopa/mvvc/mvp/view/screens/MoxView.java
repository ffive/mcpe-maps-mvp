package com.sopa.mvvc.mvp.view.screens;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.remote.backendless.Category;

import java.util.List;
import java.util.Map;


public interface MoxView extends MvpView {

    void setLanguagesList ( Map<String, String> languageMap, String userLang );

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
