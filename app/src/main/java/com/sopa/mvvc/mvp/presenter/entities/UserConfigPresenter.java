package com.sopa.mvvc.mvp.presenter.entities;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Description;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;
import com.sopa.mvvc.mvp.view.entities.UserConfigView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;


@InjectViewState
public class UserConfigPresenter extends MvpPresenter<UserConfigView> {
    private final static String TAG = "UserConfigPresenterGlob";
    private Realm realm;
    private UserConfig config;
    private int version = 1;  // todo: to use with migrations when we change the set of classes stored in realm - old db should be deleted by version (of the app mb)

    public UserConfigPresenter() {
        super();
        RealmConfiguration zRealmConfig = new RealmConfiguration.Builder()
                .initialData(realm1 -> {

                    Dictionary dictionary = realm1.createObject(Dictionary.class, "active");
                    initDictionary(dictionary);
                    realm1.copyToRealm(dictionary);

                    UserConfig initialCfg = realm1.createObject(UserConfig.class, 0L);
                    initialCfg.setLastTab(0);
                    initialCfg.setRecyclerPosition(0);
                    initialCfg.setLanguage("English");
                    realm1.copyToRealm(initialCfg);
                })
                //.schemaVersion(version) // Must be bumped when the schema changes
               // .migration(new Migration_v1()) // Migration to run instead of throwing an exception
                .build();

        //Realm.deleteRealm(zRealmConfig);
        Realm.setDefaultConfiguration(zRealmConfig);

        realm = Realm.getDefaultInstance();

        config = realm.where(UserConfig.class).findFirstAsync();
        config.addChangeListener(new RealmChangeListener<RealmObject>() {
            @Override
            public void onChange(RealmObject element) {

                Log.e(TAG, "onChange: I am global userconfig's onchange listener -----lastTab:"+((UserConfig)element).getLastTab()+" ------!!!!!"+ ((UserConfig)element)
                        .getId());
            }
        });

    }

    @Override
    public void attachView(UserConfigView view) {
        // view.sendLastTab(config.getLastTab());
        super.attachView(view);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }
/*
    public UserConfig getUserConfig(){
        return config;
    }*/

    private void initDictionary( Dictionary dictionary ){

        dictionary.app_name = "MapsMinecraft";
        dictionary.i_am_banner = "I AM BANNER";
        dictionary.title_section1 = "All maps";
        dictionary.title_section2 = "Survival";
        dictionary.title_section3 = "Adventure";
        dictionary.title_section4 = "Creation";
        dictionary.hello_world = "Hello world!";
        dictionary.action_settings = "Settings";
        dictionary.drawer_item_home = "Home";
        dictionary.maps = "Maps";
        dictionary.mods = "Mods";
        dictionary.skins = "Skins";
        dictionary.textures = "Textures";
        dictionary.drawer_item_free_play = "Free Play";
        dictionary.drawer_item_custom = "Custom";
        dictionary.drawer_item_settings = "Settings";
        dictionary.drawer_item_help = "Help";
        dictionary.drawer_item_language = "Language";
        dictionary.drawer_item_open_source = "Open Source";
        dictionary.drawer_item_contact = "Contact";
        dictionary.hello_blank_fragment = "Hello blank fragment";
        dictionary.some_text = "new text";
        dictionary.dialog_launch_title = "Launch Game?";
        dictionary.dialog_launch_message = "Do you want to launch the Minecraft right now?";
        dictionary.dialog_launch_btn_ok = "Launch";
        dictionary.dialog_launch_btn_no = "Later";
        dictionary.btn_install = "Install";
        dictionary.progress_dialog_map_download_title = "Downloading map";
        dictionary.dialog_howto_title = "Instructions";
        dictionary.dialog_howto_message = "Install any map, then launch your Minecraft client and find the maps under your saved worlds.If map marked as 'unlock', you should click on ad/watch video.than map will be available for downloading.You can find all downloaded maps at your /minecraftWorlds folder.Thank You!";
        dictionary.rate_btn_cancel = "Cancel";
        dictionary.rate_btn_ok = "Rate";
        dictionary.rate_dialog_title = "Rate this app";
        dictionary.rate_thanks = "Thanks for rating";
        dictionary.toast_error_map_download = "Sorry map is temporarily unavailable. Try again in some hours";
        dictionary.toast_error_list_download = "Sorry looks like you have no internet or our server is down. Please check network settings or try again later.";
        dictionary.title_section5 = "Parkour";
        dictionary.title_section6 = "Minigame";
        dictionary.title_section7 = "RollerCoaster";
        dictionary.title_section8 = "Puzzle";
        dictionary.title_section9 = "PvP";
        dictionary.title_section10 = "Horror";
        dictionary.title_section11 = "HideNSeek";
        dictionary.title_section12 = "Russian";
        dictionary.rate_thanks2 = "How about rating this app?";
        dictionary.submit_map = "Submit Your Map";
        dictionary.rate_tounlock_message = "In this case,Please,rate 5 stars our app If you want to download this premium map.Thank You!";
        dictionary.house_ad_ok = "Share";
        dictionary.share_tounlock_message = "If you want to unlock this premium content,Please share our app with your friends through any social app.";
        dictionary.ratetounlock_title_nointent = "Opps..Unfortunately you dont have any apps to share through";
        dictionary.rate_button_ok = "Rate it";
        dictionary.ratetounlock_title = "Premium Content!";
        dictionary.description = "Best Maps for Minecraft Pe Installer.Over 1 Million downloads with high ratings.";
        dictionary.description3 = "All maps are tested by our team before being published here.We add only best and the most interesting maps.";
        dictionary.description4 = "Every day updates with new maps.Dont miss out some new popular and trendy maps.";
        dictionary.description5 = "Create new world and apply resource pack(note:some mods have only behavior packs).Simply click on it.";
        dictionary.description6 = "The same with behavior packs.Simply click on it.Thats all!";
        dictionary.backgroundColor = "#2196F3";
        dictionary.app_intro = "Intro";
        dictionary.ticker = "Keep this app to receive daily bonuses";
        dictionary.ticker2 = "new mods and regular updates";
        dictionary.tittle = "Welcome to Maps for Minecraft";
        dictionary.tittle3 = "100% Working Maps";
        dictionary.tittle4 = "New Maps Every Day";
        dictionary.tittle5 = "Step 4:Applying Resource Packs";
        dictionary.tittle6 = "Step 5:Applying Behavior Packs";
        dictionary.tittle7 = "Customize Your Experience";
        dictionary.tittle8 = "Thank you for choosing us";
        dictionary.description7 = "We would like to use your current location to customize your experience of this app.";
        dictionary.description8 = "Enjoy our App!";
        dictionary.email_subject = "Top Minecraft Maps";
        dictionary.dialog_myapps_title = "New app!";
        dictionary.dialog_myapps_message = "Check out my brand new app - %s. Hope you\'ll like it too!";
        dictionary.dialog_myapps_ok = "Let\'s see";
        dictionary.dialog_myapps_no = "No, thanks :(";
        dictionary.eula_title = "EU User Consent Policy";
        dictionary.eula_msg = "This application uses device identifiers to personalise content and ads, to provide social media features and to analyse its traffic. We also share such identifiers and other information from your device with our social media, advertising and analytics partners.";
        dictionary.details = "View";
        dictionary.unlock = "Premium";
        dictionary.cached = "Cached install";
        dictionary.progress_dialog_map_cache_title = "Installing cached";
        dictionary.btn_unlock = "Premium";
        dictionary.adtoast = "If you want to download this premium map you need to click on ad to unlock this map.Free maps are at the bottom of maps list";
        dictionary.search_hint = "Search maps";
        dictionary.search_btn_text = "Search";
        dictionary.action_search = "Search";
        dictionary.ratings = "4.1";
        dictionary.upload_map_name_hint = "Map name";
        dictionary.title_activity_scrolling = "ScrollingActivity";
        dictionary.advertising = "Advertising";
        dictionary.language_chooser_dialog_title = "Choose your language";
    }


}
