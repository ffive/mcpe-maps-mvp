package com.sopa.mvvc.presentation.view.blank;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.UserConfig;

public interface UserConfigView extends MvpView {


    public void onUpdatedSettings(UserConfig config);


    public void sendLastTab(int lastTab);
}
