package com.sopa.mvvc.ui.custom_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.R;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguageDialogPresenter;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguageView;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

public class LanguageDialog extends MvpAppCompatDialogFragment implements LanguageView {

    public Context context;

    @InjectPresenter
    LanguageDialogPresenter languageDialogPresenter;
    RecyclerView recyclerView;


    private LanguageAdapter mAdapter;
    private ArrayList<String> mData;
    private ArrayList<String> mKeys;
    private LinearLayoutManager mLayoutManager;

    public LanguageDialog ( ) {
    } //empty default constructor (study fragments theory if confused)


    @Override
    public Dialog onCreateDialog ( Bundle savedInstanceState ) {

        mData = new ArrayList<> ( );
        mKeys = new ArrayList<>();
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager (getContext ( ));
       // new LinearLayoutManager()
        mLayoutManager.canScrollVertically();
        //mLayoutManager.heig

        // specify an adapter (see also next example)
        mAdapter = new LanguageAdapter( mData, mKeys );


        //init recycler
        recyclerView = new RecyclerView ( getContext() );
     //   recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (mLayoutManager);
        recyclerView.setAdapter (mAdapter);


        //now ask to load languages, or before
        languageDialogPresenter.loadAvailableLanguages ( );

        Realm realm = Realm.getDefaultInstance();
        Dictionary dictionary = realm.where(Dictionary.class).findFirst();
        //todo: de-hardcode   +
        // .setIcon(R.drawable.androidhappy)  //why not?
        return new AlertDialog.Builder (getActivity ( ))
                       .setTitle (dictionary.language_chooser_dialog_title)
                       .setView (recyclerView)
                       .setPositiveButton ("OK", ( dialog, which ) -> {
                           languageDialogPresenter.onLanguageSelected (mKeys.get(mAdapter.getItemClickedPosition()));
                       })
                       .setNegativeButton ("Cancel", null).create ( );
    }


    @Override
    public void updateAvailableLanguages ( Map<String, String> languageMap, String deviceLang ) {

        mData.addAll (languageMap.values ( ));
        mKeys.addAll(languageMap.keySet());
        recyclerView.getAdapter ( ).notifyDataSetChanged ( );
        ((LanguageAdapter)recyclerView.getAdapter()).setDefaultLanguage( deviceLang );

    }


}
