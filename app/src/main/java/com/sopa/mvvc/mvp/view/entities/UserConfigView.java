package com.sopa.mvvc.mvp.view.entities;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.local.UserConfig;

public interface UserConfigView extends MvpView {


     void onUpdatedSettings(UserConfig config);


     void sendLastTab(int lastTab);
}
