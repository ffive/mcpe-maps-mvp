package com.sopa.mvvc.mvp.presenter.entities;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.sopa.mvvc.datamodel.remote.backendless.Category;
import com.sopa.mvvc.datamodel.remote.backendless.Map;
import com.sopa.mvvc.mvp.view.entities.CategoryListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;


@InjectViewState
public class CategoryListPresenter extends MvpPresenter<CategoryListView> {


    Category category;



    private Realm realm;
    private String TAG = "categ presenter";

    private int pageSize = 50, offset = 0;

    private rx.Subscription categoryCatcher, backendlessCatcher;

    public CategoryListPresenter(Category categoryObj) {
        super();
        realm = Realm.getDefaultInstance();
        category = categoryObj;


        //todo listen to tab's category from realm, not from constructor?
        categoryCatcher = Observable.just(category).subscribe(new Observer<Category>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "categoryObservableFunction::onCompleted: completed - NOT listening for any category changes in realm");
            }

            @Override
            public void onError(Throwable e) {

//retry)
                e.printStackTrace();
            }

            @Override
            public void onNext(Category _category) {
//начлаьная ок категория один раз триггерит загрузку потому что отличается от того что прилетело/ последующие разы стриггерит только если прилетевшее отличается

                dataListenerFunction(category.getCategory())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(maps1 -> {
                            Log.d(TAG, "dataListenerFunction Rx: updating " + maps1.size() + " maps from realm.where(Map.class).findAllAsync() and sent to CategFragment recycler");
                            getViewState().updateList(maps1);
                            getViewState().hideProgress();

                        });


                backendlessCatcher = getListFromBackendless().
                        subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(maps1 -> {
                            Realm.getDefaultInstance().executeTransactionAsync(realm1 -> {
                                realm1.copyToRealmOrUpdate(maps1);
                            }, () -> {
                                Log.d(TAG, "handleResponse: Backendless received " + maps1.size() + " maps from Backendless and written them realm (background)");
                            });
                        });


            }


            //merge?


        });
    }


    private Observable<RealmResults<Map>> dataListenerFunction(String category) {
        return realm.where(Map.class)
                .equalTo("category", category)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded)
                .filter(RealmResults::isValid);

    }

/*
    private Observable<Category> categoryObservableFunction() {
        return realm.where(Category.class)
                .equalTo("objectId", iAmWorkingWithCategoryId)
                .findAllAsync()
                .<RealmResults<Category>>asObservable()
                .filter(RealmResults::isLoaded)
                .filter(RealmResults::isValid)
                .map(RealmResults::first)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

*/


    public BehaviorSubject<List<Map>> getListFromBackendless() {

        BehaviorSubject<List<Map>> myInviteFriendsBS = BehaviorSubject.create();
        myInviteFriendsBS.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


        Backendless.Persistence.of(Map.class).find(createQuery(pageSize, offset), new BackendlessCallback<BackendlessCollection<Map>>() {
            @Override
            public void handleResponse(BackendlessCollection<Map> response) {
                offset += pageSize;
                if (response.getCurrentPage().size() == 0) {
                    myInviteFriendsBS.onCompleted();
                } else {
                    myInviteFriendsBS.onNext(response.getCurrentPage());
                    Log.d(TAG,"backendlessRxSubject triggers __onNext__:  going to load next "+pageSize +" with offset = "+offset+"  in "+ category.getCategory());
                    response.nextPage(this);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                myInviteFriendsBS.onError(new Throwable(fault.getMessage()));
                super.handleFault(fault);
            }

        });

        return myInviteFriendsBS;
    }


    private BackendlessDataQuery createQuery(int pageSize, int offset) {

        BackendlessDataQuery query = new BackendlessDataQuery(new QueryOptions(pageSize, offset));
        query.setWhereClause("category='" + category.getCategory() + "'");   //todo CATEGORY empty
        return query;
    }

    public void iWantList() {

    }

    @Override
    public void attachView(CategoryListView view) {
        super.attachView(view);
    }

    @Override
    protected void onFirstViewAttach() {

        super.onFirstViewAttach();
        getViewState().showProgress();

        // .getCategory();
    }

    @Override
    public void onDestroy() {
        backendlessCatcher.unsubscribe();
        categoryCatcher.unsubscribe();
        realm.close();
        super.onDestroy();

    }

    public void unlock(Map map) {
        Log.d(TAG, "unlock: called from card click with map "+map.getName()+map.getObjectId());
    }

    public void onMapCardClicked(Map map) {
        //String url = map.getI_url();
        realm.executeTransactionAsync(realm1 -> {
           // HouseAd ad = realm1.where(HouseAd.class).findFirst();
          //  ad.setImg(url);
           // realm1.copyToRealmOrUpdate(ad);
        });
    }
}
