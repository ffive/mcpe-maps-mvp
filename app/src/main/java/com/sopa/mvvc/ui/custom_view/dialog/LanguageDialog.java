package com.sopa.mvvc.ui.custom_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguageDialogPresenter;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguageView;
import com.sopa.mvvc.mvp.presenter.helpers.language.MyAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

public class LanguageDialog extends MvpAppCompatDialogFragment implements LanguageView {

    public Context context;

    @InjectPresenter
    LanguageDialogPresenter languageDialogPresenter;
    RecyclerView recyclerView;


    private MyAdapter mAdapter;
    private ArrayList<String> mData;
    private LinearLayoutManager mLayoutManager;

    public LanguageDialog ( ) {
    } //empty default constructor (study fragments theory if confused)


    @Override
    public Dialog onCreateDialog ( Bundle savedInstanceState ) {

        mData = new ArrayList<> ( );
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager (getContext ( ));

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter (mData);

        //init recycler
        recyclerView = new RecyclerView (getContext ( ));
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (mLayoutManager);
        recyclerView.setAdapter (mAdapter);

        //now ask to load languages, or before
        languageDialogPresenter.loadAvailableLanguages ( );

        //todo: de-hardcode   +
        // .setIcon(R.drawable.androidhappy)  //why not?
        return new AlertDialog.Builder (getActivity ( ))
                       .setTitle ("Selecxt language")
                       .setMessage ("ftw")
                       .setView (recyclerView)
                       .setPositiveButton ("OK", ( dialog, which ) -> {
                           //positionSelected = which;
                           languageDialogPresenter.onLanguageSelected (which);
                       })
                       .setNegativeButton ("Cancel", null).create ( );
    }


    @Override
    public void updateAvailableLanguages ( Map<String, String> languageMap, String deviceLang ) {


        //int pos = mAdapter.get (languageMap.get (deviceLang) != null ? languageMap.get (deviceLang) : languageMap.get ("English"));

        mData.addAll (languageMap.values ( ));
        recyclerView.getAdapter ( ).notifyDataSetChanged ( );

    }


}
