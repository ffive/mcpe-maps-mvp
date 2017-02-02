package com.sopa.mvvc.mvp.view.entities;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface UploadMapView extends MvpView {


    void showProgress();
    void hideProgress();

    void showError(String msg);

    void showSuccess();

    void setCategories(List<String> categories);


}
