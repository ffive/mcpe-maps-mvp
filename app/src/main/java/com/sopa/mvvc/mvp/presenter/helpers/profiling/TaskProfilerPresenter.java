package com.sopa.mvvc.mvp.presenter.helpers.profiling;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sopa.mvvc.datamodel.local.profiling.Profiler;
import com.sopa.mvvc.mvp.view.helpers.profiling.TaskProfilerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;


/**
 * smth changes profiler object in realm db - momentally sends event to any connected views with updated profiler data;
 */
@InjectViewState
public class TaskProfilerPresenter extends MvpPresenter<TaskProfilerView> {


    Profiler profiler;

    public TaskProfilerPresenter() {
        super();
        Realm.getDefaultInstance().executeTransactionAsync(realm -> profiler = realm.createObject(Profiler.class));

        profiler = Realm.getDefaultInstance().where(Profiler.class).findFirstAsync();
        profiler.addChangeListener(new RealmChangeListener<Profiler>() {
            @Override
            public void onChange(Profiler element) {
                getViewState().updateProfileInfo(element);
            }
        });
    }


}
