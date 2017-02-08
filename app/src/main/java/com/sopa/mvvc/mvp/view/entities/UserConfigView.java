package com.sopa.mvvc.mvp.view.entities;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.local.UserConfig;

import java.util.Map;

public interface UserConfigView extends MvpView {


     void onUpdatedSettings(UserConfig config);


     void sendLastTab(int lastTab);
/*
     void showLanguageDialog ();

     void setLanguagesList (Map<String, String> languageMap, String userLang );

     void sendLanguage ( String language );*/
}
