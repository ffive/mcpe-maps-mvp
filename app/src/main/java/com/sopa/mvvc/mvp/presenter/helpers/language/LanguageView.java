package com.sopa.mvvc.mvp.presenter.helpers.language;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.Map;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

public interface LanguageView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLanguageChooserDialog();

    void setLanguagesList (Map<String, String> languageMap, String userLang );

}
