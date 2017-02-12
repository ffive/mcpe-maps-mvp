package com.sopa.mvvc.mvp.view.entities;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;

/**
 * Created by AndreiPiatosin on 12-Feb-17.
 */

public interface DictionaryView extends MvpView {

    void onUpdateDictionary(Dictionary dictionary);

}
