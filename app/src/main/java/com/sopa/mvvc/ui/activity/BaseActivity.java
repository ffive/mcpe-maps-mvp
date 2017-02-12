package com.sopa.mvvc.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.sopa.mvvc.R;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;
import com.sopa.mvvc.mvp.presenter.entities.DictionaryPresenter;
import com.sopa.mvvc.mvp.view.entities.DictionaryView;
import com.sopa.mvvc.ui.custom_view.dialog.LanguageDialog;

import io.realm.Realm;

/**
 * Created by AndreiPiatosin on 12-Feb-17.
 */

public class BaseActivity extends MvpAppCompatActivity implements DictionaryView {

    @InjectPresenter
    DictionaryPresenter dictionaryPresenter;

    DialogFragment dialogFragment;
    Dictionary dictionary;


    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(1);
    SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(2);



    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);

        Realm realm = Realm.getDefaultInstance();
        Dictionary dictionary = realm.where(Dictionary.class).findFirst();

        if (!getIntent().getBooleanExtra("isLanguageChoosed", false)){
            dialogFragment = new LanguageDialog();
            dialogFragment.show (getSupportFragmentManager (),"initial language setup");
        }


    }


    public void initNavigationDrawer ( ) {

        Realm realm = Realm.getDefaultInstance();
        dictionary = realm.where(Dictionary.class).findFirst();



        new Drawer( )
                .withActivity (this)
                .withToolbar (binding.toolbar)
                .withActionBarDrawerToggle (true)
                .withHeader (R.layout.drawer_header)
                .addDrawerItems (
                        new PrimaryDrawerItem( ).withName (dictionary.drawer_item_home).withIcon (FontAwesome.Icon.faw_home).withBadge ("99")
                                .withIdentifier (1),
                        new PrimaryDrawerItem ( ).withName (dictionary.drawer_item_free_play).withIcon (FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem ( ).withName (dictionary.drawer_item_custom).withIcon (FontAwesome.Icon.faw_eye).withBadge ("6")
                                .withIdentifier (2),
                        new SectionDrawerItem( ).withName (dictionary.drawer_item_settings),
                        new SecondaryDrawerItem( ).withName (dictionary.drawer_item_language).withIcon (FontAwesome.Icon.faw_language),
                        new SecondaryDrawerItem ( ).withName (dictionary.drawer_item_help).withIcon (FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem ( ).withName (dictionary.drawer_item_open_source).withIcon (FontAwesome.Icon.faw_question).setEnabled
                                (false),
                        new DividerDrawerItem( ),
                        new SecondaryDrawerItem ( ).withName (dictionary.drawer_item_contact).withIcon (FontAwesome.Icon.faw_github).withBadge
                                ("12+")
                                .withIdentifier (1)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        if (drawerItem instanceof Nameable) {
                            String language = dictionary.drawer_item_language;

                            if ( ((Nameable)drawerItem).getName().equals(dictionary.drawer_item_language) ){
                                dialogFragment.show (getSupportFragmentManager (),"settings");
                            }

/*                            switch ( ((Nameable)drawerItem).getName() ){
                                case (language)   :
                                  //  LanguageDialog languageChooserDialog = new LanguageDialog(MoxActivity.this);
                                   dialogFragment.show (getSupportFragmentManager (),"settings");
                                    break;
                            }*/

                        }
                    }
                })
                .build ( );
    }


    @Override
    public void onUpdateDictionary(Dictionary dictionary) {

    }
}
