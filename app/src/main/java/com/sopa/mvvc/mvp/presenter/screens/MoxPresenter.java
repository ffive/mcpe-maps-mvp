package com.sopa.mvvc.mvp.presenter.screens;


import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Category;
import com.sopa.mvvc.datamodel.remote.backendless.Map;
import com.sopa.mvvc.mvp.view.screens.MoxView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Я выше приводил пример, но повторюсь снова:
 * <p>
 * Есть Activity с табами. Стоит задача, чтобы внутри каждого таба была независимая цепочка экранов, сохраняющаяся при смене таба.
 * <p>
 * Решается это двумя типами навигации: глобальной и локальной.
 * <p>
 * GlobalRouter – роутер приложения, связанный с навигатором Activity.
 * Презентер, обрабатывающий клики по табам, вызывает команды у GlobalRouter.
 * <p>
 * LocalRouter – роутеры внутри каждого Fragment’а-контейнера. Навигатор для LocalRouter'а реализует сам Fragment-контейнер.
 * Презентеры, относящиеся к локальным цепочкам внутри табов, получают для навигации LocalRouter.
 * <p>
 * Где связь? Во Fragment’ах-контейнерах есть доступ и к глобальному навигатору! В момент, когда локальная цепочка внутри таба закончилась
 * и вызвана команда Back(), то Fragment передает её в глобальный навигатор.
 * <p>
 * Совет: для настройки зависимостей между компонентами, используйте Dagger 2, а для управления их жизненным циклом – его CustomScopes.
 * <p>
 * А что с системной кнопкой Back?
 * <p>
 * Этот вопрос специально не решается в библиотеке. Нажатие на кнопку Back надо воспринимать как взаимодействие пользователя и передавать
 * просто как событие в презентер.
 */

@InjectViewState
public class MoxPresenter extends MvpPresenter<MoxView> {
    private final static String TAG = "MoxPresenter: ";


    private Realm realm;
    private UserConfig userConfig;
    private RealmResults<Category> categories;
    private AsyncCallback<BackendlessCollection<Category>> nextPageCallback = new AsyncCallback<BackendlessCollection<Category>> ( ) {
        @Override
        public void handleResponse ( BackendlessCollection<Category> response ) {
            Log.d (TAG, "handleResponse: just received  " + response.getCurrentPage ( ).size ( ) + "categories ");

            realm.executeTransactionAsync (
                    realm1 -> {
                        realm1.copyToRealmOrUpdate (response.getCurrentPage ( ));
                    });
        }

        @Override
        public void handleFault ( BackendlessFault fault ) {
            Log.d (TAG, "handleFault: " + fault.getDetail ( ));  //todo retry?
        }
    };
    private java.util.Map<String, String> servedLocales;

    {
        servedLocales = new HashMap<> ( );
    }


    public MoxPresenter ( ) {
        super ( );

        Log.d (TAG, "MoxPresenter: Constructor()");

        getViewState ( ).showLoading ( );

        realm = Realm.getDefaultInstance ( );
        userConfig = realm.where (UserConfig.class).findFirstAsync ( );
        userConfig.addChangeListener (( RealmChangeListener<UserConfig> ) element -> {
            getViewState ( ).sendLastTab (element.getLastTab ( ));
        });

        dataListenerFunction ( );
        loadAllCategories ( );

    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable ( int color ) {
        return color != 0 ? new ColorDrawable (color) : null;
    }

    @BindingAdapter( "img:url" )
    public static void imgLoad ( ImageView imageView, Map map ) {
        if ( map != null ) {
            Picasso.with (imageView.getContext ( )).load (map.getI_url ( )).tag (map.getCategory ( )).into (imageView);
        }

    }

    @BindingAdapter( value = { "android:src", "placeHolder" },
            requireAll = false )
    public static void setImageUrl ( ImageView view, String url, int placeHolder ) {

        RequestCreator requestCreator = Picasso.with (view.getContext ( )).load (url);

        if ( placeHolder != 0 ) {
            requestCreator.placeholder (placeHolder);
        }
        requestCreator.into (view);
    }

    private void dataListenerFunction ( ) {
        realm.where (Category.class)
                .findAllAsync ( )
                .asObservable ( )
                .filter (RealmResults:: isLoaded)
                .filter (RealmResults:: isValid)
                .subscribeOn (AndroidSchedulers.mainThread ( ))
                .observeOn (AndroidSchedulers.mainThread ( ))
                .subscribe (cats -> {
                    getViewState ( ).updateTabs (cats, userConfig.getLastTab ( ));  //if not same, else - reset position after apply
                });
    }

    private void loadAllCategories ( ) {

        Backendless.Persistence.of (Category.class).find (new BackendlessDataQuery (new QueryOptions (100, 0)), nextPageCallback);
    }

    @Override
    protected void onFirstViewAttach ( ) {
        Log.d (TAG, "onFirstViewAttach: ");
        super.onFirstViewAttach ( );

        //getViewState().updateTabs(categories);
        //updateTabs

    }

    @Override
    public void onDestroy ( ) {
        realm.close ( );
        super.onDestroy ( );

    }

    public void onPageSet ( int page ) {

        //vasponel)))
        //  getViewState().sendLastTab(page);
        realm.executeTransactionAsync (bgRealm -> {
            UserConfig user = bgRealm.where (UserConfig.class).findFirst ( );
            user.setLastTab (page);
        });

    }
/*
    public void loadAvailableLanguages ( ) {

        getViewState().showLanguageDialog();

        userConfig = realm.where (UserConfig.class).findFirst ( );
        String userLang = userConfig.getLanguage();

        Backendless.Events.dispatch ("getAvailableLanguages", new HashMap(), new AsyncCallback<java.util.Map> ( ) {
            @Override
            public void handleResponse ( java.util.Map map ) {

                servedLocales.putAll (map);

                getViewState ( ).setLanguagesList (map, userLang);

            }

            @Override
            public void handleFault ( BackendlessFault backendlessFault ) {

            }
        });

    }

    public void onLanguageSelected ( int dialogPosition ) {
       // getViewState().sendLanguage( servedLocales.get (dialogPosition));

        realm.executeTransaction(realm1 -> {

            userConfig.setLanguage (servedLocales.get (dialogPosition));
            realm1.copyToRealmOrUpdate(userConfig);
        });


    }*/

}

