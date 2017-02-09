package com.sopa.mvvc.mvp.presenter.helpers.language;

import com.arellomobile.mvp.MvpView;

import java.util.Map;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

public interface LanguageView extends MvpView {

    void updateAvailableLanguages ( Map<String, String> languageMap, String userLang );


}
