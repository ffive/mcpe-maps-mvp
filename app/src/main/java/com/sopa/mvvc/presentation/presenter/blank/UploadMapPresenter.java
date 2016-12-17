package com.sopa.mvvc.presentation.presenter.blank;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.sopa.mvvc.datamodel.Category;
import com.sopa.mvvc.datamodel.UploadMap;
import com.sopa.mvvc.presentation.view.blank.UploadMapView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

@InjectViewState
public class UploadMapPresenter extends MvpPresenter<UploadMapView> {

    UploadMap userMap;

    RealmResults<Category> categories;
    Realm realm;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        userMap = new UploadMap();


        realm = Realm.getDefaultInstance();

        categories = Realm.getDefaultInstance().where(Category.class).findAllAsync();
        categories.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
            @Override
            public void onChange(RealmResults<Category> element) {

                List<String> categories = new ArrayList<String>();

                for (Category cat : element) {
                    categories.add(cat.getCategory());
                }

               // getViewState().setCategories( );
            }
        });
    }


    public void onClicked(UploadMap map) {

        getViewState().showProgress();
        userMap.setName(map.getName());
        userMap.setDescription(map.getDescription());
        userMap.setAuthor(map.getAuthor());




        Backendless.Data.of(UploadMap.class).save(userMap, new AsyncCallback<UploadMap>() {
            @Override
            public void handleResponse(UploadMap response) {
                getViewState().hideProgress();
                getViewState().showSuccess();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                getViewState().showError("ERROREBTA");
                getViewState().hideProgress();
            }
        });

    }

    public void onCategoryChosen(int pos) {
        Log.d("TAG", "onCategoryChosen: " + categories.get(pos).getCategory());
        userMap.setCategory(categories.get(pos).getCategory());
    }
}
