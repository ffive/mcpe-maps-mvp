package com.sopa.mvvc.ui.custom_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.R;
import com.sopa.mvvc.datamodel.remote.backendless.Category;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguagePresenter;
import com.sopa.mvvc.mvp.presenter.helpers.language.LanguageView;
import com.sopa.mvvc.mvp.presenter.screens.MoxPresenter;
import com.sopa.mvvc.mvp.view.screens.MoxView;

import java.util.List;
import java.util.Map;

import static android.R.string.no;
import static android.R.string.yes;

/**
 * Created by AndreiPiatosin on 08-Feb-17.
 */

public class LanguageChooserDialog extends Dialog implements View.OnClickListener, LanguageView {


    public Context context;
    @InjectPresenter
    LanguagePresenter languagePresenter;
    private MvpDelegate mParentDelegate;
    private MvpDelegate<LanguageChooserDialog> mMvpDelegate;
    private ListView listView;
    private ProgressBar progressBar;
    private Button buttonOk;
    private Button buttonCancel;
    private ArrayAdapter<String> arrayAdapter;

    private int positionSelected = 0;

    public LanguageChooserDialog(Context context, ViewGroup parent) {
        super(context);
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_language_chooser, parent, true);

        buttonOk = (Button) dialogView.findViewById(R.id.buttonOk);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_singlechoice);

        listView.setEmptyView(progressBar);
        //listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionSelected = i;
                languagePresenter.onLanguageSelected(i);
            }
        });

        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*
        //binding = DataBindingUtil.setContentView()
        setContentView(R.layout.dialog_language_chooser);

        buttonOk = (Button) findViewById( R.id.buttonOk );
        buttonCancel = (Button) findViewById( R.id.buttonCancel );
        listView = (ListView) findViewById( R.id.listView );
        progressBar = (ProgressBar) findViewById( R.id.progressBar );

        arrayAdapter = new ArrayAdapter<> (context, android.R.layout.select_dialog_singlechoice);

        listView.setEmptyView( progressBar );
        //listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionSelected = i;
                languagePresenter.onLanguageSelected(i);
            }
        });


        buttonOk.setOnClickListener( this );
        buttonCancel.setOnClickListener( this );

      //  languagePresenter.loadAvailableLanguages();
*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOk:
                languagePresenter.onLanguageSelected(positionSelected);
                dismiss();
                break;
            case R.id.buttonCancel:
                dismiss();
                break;
        }

    }


    public void init(MvpDelegate parentDelegate) {
        mParentDelegate = parentDelegate;

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    public MvpDelegate<LanguageChooserDialog> getMvpDelegate() {
        if (mMvpDelegate != null) {
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        //mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(getId()));
        mMvpDelegate.setParentDelegate(mParentDelegate, "kek");
        return mMvpDelegate;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getMvpDelegate().onSaveInstanceState();
        getMvpDelegate().onDetach();
    }


    @Override
    public void showLanguageChooserDialog() {
    }

    @Override
    public void setLanguagesList(Map<String, String> languageMap, String userLang) {
        arrayAdapter.addAll(languageMap.values());

        int defaultListPosition;
        if (languageMap.get(userLang) != null) {
            defaultListPosition = arrayAdapter.getPosition(languageMap.get(userLang));
        } else {
            defaultListPosition = arrayAdapter.getPosition(languageMap.get("English"));
        }

        listView.setAdapter(arrayAdapter);

        listView.setSelection(defaultListPosition);
    }

}
