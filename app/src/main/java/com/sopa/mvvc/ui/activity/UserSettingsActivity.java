package com.sopa.mvvc.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ActivityUserSettingsBinding;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Category;
import com.sopa.mvvc.mvp.presenter.entities.UserConfigPresenter;
import com.sopa.mvvc.mvp.presenter.screens.MoxPresenter;
import com.sopa.mvvc.mvp.view.entities.UserConfigView;
import com.sopa.mvvc.mvp.view.screens.MoxView;

import java.util.List;
import java.util.Map;

public class UserSettingsActivity extends AppCompatActivity implements MoxView,UserConfigView {

    @InjectPresenter( type = PresenterType.GLOBAL )
    UserConfigPresenter mUserConfigPresenter;

    @InjectPresenter
    MoxPresenter mMoxPresenter;

    ActivityUserSettingsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = DataBindingUtil.setContentView( UserSettingsActivity.this, R.layout.activity_user_settings);


        binding.tvLanguageKey.setOnClickListener(view -> mMoxPresenter.loadAvailableLanguages());
        //setSupportActionBar(binding.toolbar);

    }

    @Override
    public void onUpdatedSettings(UserConfig config) {

    }

    @Override
    public void setLanguagesList(Map<String, String> languageMap, String userLang) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder (this);
        builder.setTitle (R.string.language_chooser_dialog_title);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> (this, android.R.layout.select_dialog_singlechoice);

        arrayAdapter.addAll (languageMap.values());  // localized {   0: "Русский" ,  2:"English", 3:"Deutch" }

        int defaultListPosition;
        if ( languageMap.get(userLang)!= null ){
            defaultListPosition = arrayAdapter.getPosition( languageMap.get(userLang) );
        }   else {
            defaultListPosition = arrayAdapter.getPosition( languageMap.get("English") );
        }

        builder.setSingleChoiceItems (arrayAdapter, defaultListPosition,
                ( dialogInterface, i ) -> {
                    mMoxPresenter.onLanguageSelected(i);
                });


        builder.setPositiveButton (android.R.string.ok,
                ( dialogInterface, i ) -> mMoxPresenter.onLanguageSelected(i) );

        builder.show();

    }

    @Override
    public void sendLanguage(String language) {

      //  binding.tvLanguageValue.setText(language);

    }

    @Override
    public void showMyAppsDialog() {

    }

    @Override
    public void showNewApp(String message, String link, String pkg) {

    }

    @Override
    public void showRateDialog() {

    }

    @Override
    public void showEULA() {

    }

    @Override
    public void showRewardedAd() {

    }

    @Override
    public void showSearch() {

    }

    @Override
    public void hideSearch() {

    }

    @Override
    public void showInterstitial() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showTabs(List<Category> categories) {

    }

    @Override
    public void updateTabs(List<Category> categories, int position) {

    }

    @Override
    public void showUploadDialog() {

    }

    @Override
    public void sendLastTab(int tab) {

    }
}
