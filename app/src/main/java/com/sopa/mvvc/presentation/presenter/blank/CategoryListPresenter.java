package com.sopa.mvvc.presentation.presenter.blank;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.sopa.mvvc.datamodel.Category;
import com.sopa.mvvc.datamodel.Map;
import com.sopa.mvvc.presentation.view.blank.CategoryListView;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


@InjectViewState
public class CategoryListPresenter extends MvpPresenter<CategoryListView> {

    private RealmResults<Map> maps;
    private Realm realm;
    private String TAG = "categ presenter";

    private int pageSize = 100, offset = 0;
    private String iAmWorkingWithCategoryId;
    private String category;

    public CategoryListPresenter(String categoryId) {
        super();
        realm = Realm.getDefaultInstance();
        iAmWorkingWithCategoryId = categoryId;

        categoryObservableFunction().subscribe(new Observer<Category>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//retry)
            }

            @Override
            public void onNext(Category category) {
                //merge?
                Log.d(TAG, "onNext: I am categoryListFragment's presenter. Something changed Category  and triggered Rx to emit an updated Category obj. If interested I can show what changed " +
                        "exactly.. ");


                loadNext(pageSize, offset = 0);

                dataListenerFunction(category.getCategory()).
                        subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(maps1 -> {

                            getViewState().updateList(maps1);
                        });
            }
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

    private Observable<Category> categoryObservableFunction() {
        return realm.where(Category.class)
                .equalTo("objectId", iAmWorkingWithCategoryId)
                .findFirstAsync()
                .<Category>asObservable()
                .filter(realmObject -> realmObject.isLoaded())
                .filter(realmObject -> realmObject.isValid())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showProgress();


        // .getCategory();


    }


    private BackendlessDataQuery createQuery(int pageSize, int offset) {

        BackendlessDataQuery query = new BackendlessDataQuery(new QueryOptions(pageSize, offset));
        query.setWhereClause("category='" + category + "'");
        return query;
    }


    public void loadNext(int _pageSize, int _offset) {

        // Log.d(TAG, "loadNext: loading next page with params: pagesize=" + _pageSize + "  offset=" + _offset);

        Backendless.Data.of(Map.class).find(createQuery(_pageSize, _offset), new AsyncCallback<BackendlessCollection<Map>>() {
            @Override
            public void handleResponse(BackendlessCollection<Map> response) {

                Log.d(TAG, "execute: " + response.getCurrentPage().size());


                Realm.getDefaultInstance().executeTransactionAsync(realm1 -> {
                    realm1.copyToRealmOrUpdate(response.getCurrentPage());
                }, () -> {

                    Log.d(TAG, "handleResponse: copied/updated" + response.getCurrentPage().size() + "  from Backendless to realm (async).");
                });
                if (response.getTotalObjects() > (offset += pageSize)) {
                    loadNext(pageSize, offset);
                } else getViewState().hideProgress();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "handleFault: " + fault.toString());
            }
        });
    }


    public void iWantList() {
        //getViewState().showList(maps);
        Log.d(TAG, "iWantList: no you don't ;P may be want");
    }

    @Override
    public void attachView(CategoryListView view) {
        super.attachView(view);
    }

    public void unlock(Map map) {

    }


}
