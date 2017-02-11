package com.sopa.mvvc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.mvp.login.Defaults;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;
import com.sopa.mvvc.ui.activity.MoxActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

    //    loadNewDescription();
      //  loadNewDictionary();
        startActivityForResult (new Intent (MainActivity.this, com.backendless.mvp.login.LoginActivity.class),Defaults.REQUEST_CODE);// Defaults
        // .REQUEST_LOGIN_CODE);


    }

    //Will be used for some other modules/requests too, trying to generify:
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult (requestCode, resultCode, data);

        if ( requestCode == Defaults.REQUEST_CODE ) {
            switch ( resultCode ) {


                case Defaults.VALID_LOGIN: {

                    startActivity (new Intent (MainActivity.this, MoxActivity.class).putExtra("isLanguageChoosed", isLanguageChosen(data) ));
                    break;
                }
                case Defaults.LOGIN_SUCCESS: {

                    startActivity (new Intent (MainActivity.this, MoxActivity.class).putExtra("isLanguageChoosed", isLanguageChosen(data) ));
                    break;
                }
                default:{startActivity (new Intent (MainActivity.this, MoxActivity.class));}
            }

        }
    }
    
    private boolean isLanguageChosen(Intent data){
        BackendlessUser backendlessUser = (BackendlessUser) data.getSerializableExtra("backendless_user");

        boolean isLanguageChosen = false;

        if (!backendlessUser.getProperty("language").toString().isEmpty()){
            isLanguageChosen = true;
        }   
        return isLanguageChosen;
    }

    private void loadNewDictionary(){


        HashMap dictionary = new HashMap();


        dictionary.put( "language", "English" );

        dictionary.put ( "app_name", "MapsMinecraft" );


        dictionary.put ( "i_am_banner", "I AM BANNER" );


        dictionary.put ( "title_section1", "All maps" );
        dictionary.put ( "title_section2", "Survival" );
        dictionary.put ( "title_section3", "Adventure" );
        dictionary.put ( "title_section4", "Creation" );

        dictionary.put ( "hello_world", "Hello world!" );

        dictionary.put ( "action_settings", "Settings" );


        dictionary.put ( "drawer_item_home", "Home" );
        dictionary.put ( "maps", "Maps" );
        dictionary.put ( "mods", "Mods" );
        dictionary.put ( "skins", "Skins" );
        dictionary.put ( "textures", "Textures" );


        dictionary.put ( "drawer_item_free_play", "Free Play" );
        dictionary.put ( "drawer_item_custom", "Custom" );
        dictionary.put ( "drawer_item_settings", "Settings" );
        dictionary.put ( "drawer_item_help", "Help" );
        dictionary.put ( "drawer_item_language", "Language" );
        dictionary.put ( "drawer_item_open_source", "Open Source" );
        dictionary.put ( "drawer_item_contact", "Contact" );


        dictionary.put ( "hello_blank_fragment", "Hello blank fragment" );
        dictionary.put ( "some_text", "new text" );
        dictionary.put ( "dialog_launch_title", "Launch Game?" );
        dictionary.put ( "dialog_launch_message", "Do you want to launch the Minecraft right now?" );
        dictionary.put ( "dialog_launch_btn_ok", "Launch" );
        dictionary.put ( "dialog_launch_btn_no", "Later" );
        dictionary.put ( "btn_install", "Install" );
        dictionary.put ( "progress_dialog_map_download_title", "Downloading map" );
        dictionary.put ( "dialog_howto_title", "Instructions" );
        dictionary.put ( "dialog_howto_message", "Install any map, then launch your Minecraft client and find the maps under your saved worlds.If map marked as 'unlock', you should click on ad/watch video.than map will be available for downloading.You can find all downloaded maps at your /minecraftWorlds folder.Thank You!" );
        dictionary.put ( "rate_btn_cancel", "Cancel" );
        dictionary.put ( "rate_btn_ok", "Rate" );
        dictionary.put ( "rate_dialog_title", "Rate this app" );
        dictionary.put ( "rate_thanks", "Thanks for rating" );
        dictionary.put ( "toast_error_map_download", "Sorry map is temporarily unavailable. Try again in some hours" );
        dictionary.put ( "toast_error_list_download", "Sorry looks like you have no internet or our server is down. Please check network settings or try again later." );
        dictionary.put ( "title_section5", "Parkour" );
        dictionary.put ( "title_section6", "Minigame" );
        dictionary.put ( "title_section7", "RollerCoaster" );
        dictionary.put ( "title_section8", "Puzzle" );
        dictionary.put ( "title_section9", "PvP" );
        dictionary.put ( "title_section10", "Horror" );
        dictionary.put ( "title_section11", "HideNSeek" );
        dictionary.put ( "title_section12", "Russian" );
        dictionary.put ( "rate_thanks2", "How about rating this app?" );

        dictionary.put ( "submit_map", "Submit Your Map" );
        dictionary.put ( "rate_tounlock_message", "In this case,Please,rate 5 stars our app If you want to download this premium map.Thank You!" );
        dictionary.put ( "house_ad_ok", "Share" );
        dictionary.put ( "share_tounlock_message", "If you want to unlock this premium content,Please share our app with your friends through any social app." );
        dictionary.put ( "ratetounlock_title_nointent", "Opps..Unfortunately you dont have any apps to share through" );
        dictionary.put ( "rate_button_ok", "Rate it" );
        dictionary.put ( "ratetounlock_title", "Premium Content!" );
        dictionary.put ( "description", "Best Maps for Minecraft Pe Installer.Over 1 Million downloads with high ratings." );
        dictionary.put ( "description3", "All maps are tested by our team before being published here.We add only best and the most interesting maps." );
        dictionary.put ( "description4", "Every day updates with new maps.Dont miss out some new popular and trendy maps." );
        dictionary.put ( "description5", "Create new world and apply resource pack(note:some mods have only behavior packs).Simply click on it." );
        dictionary.put ( "description6", "The same with behavior packs.Simply click on it.Thats all!" );

        dictionary.put ( "backgroundColor", "#2196F3" );
        dictionary.put ( "app_intro", "Intro" );
        dictionary.put ( "ticker", "Keep this app to receive daily bonuses" );
        dictionary.put ( "ticker2", "new mods and regular updates" );
        dictionary.put ( "tittle", "Welcome to Maps for Minecraft" );
        dictionary.put ( "tittle3", "100% Working Maps" );
        dictionary.put ( "tittle4", "New Maps Every Day" );
        dictionary.put ( "tittle5", "Step 4:Applying Resource Packs" );
        dictionary.put ( "tittle6", "Step 5:Applying Behavior Packs" );
        dictionary.put ( "tittle7", "Customize Your Experience" );
        dictionary.put ( "tittle8", "Thank you for choosing us" );
        dictionary.put ( "description7", "We would like to use your current location to customize your experience of this app." );
        dictionary.put ( "description8", "Enjoy our App!" );
        dictionary.put ( "email_subject", "Top Minecraft Maps" );
        dictionary.put ( "dialog_myapps_title", "New app!" );
        dictionary.put ( "dialog_myapps_message", "Check out my brand new app - %s. Hope you\'ll like it too!" );
        dictionary.put ( "dialog_myapps_ok", "Let\'s see" );
        dictionary.put ( "dialog_myapps_no", "No, thanks :(" );
        dictionary.put ( "eula_title", "EU User Consent Policy" );
        dictionary.put ( "eula_msg", "This application uses device identifiers to personalise content and ads, to provide social media features and to analyse its traffic. We also share such identifiers and other information from your device with our social media, advertising and analytics partners." );
        dictionary.put ( "details", "View" );
        dictionary.put ( "unlock", "Premium" );
        dictionary.put ( "cached", "Cached install" );
        dictionary.put ( "progress_dialog_map_cache_title", "Installing cached" );
        dictionary.put ( "btn_unlock", "Premium" );
        dictionary.put ( "adtoast", "If you want to download this premium map you need to click on ad to unlock this map.Free maps are at the bottom of maps list" );
        dictionary.put ( "search_hint", "Search maps" );
        dictionary.put ( "search_btn_text", "Search" );
        dictionary.put ( "action_search", "Search" );
        dictionary.put ( "ratings", "4.1" );
        dictionary.put ( "upload_map_name_hint", "Map name" );
        dictionary.put ( "title_activity_scrolling", "ScrollingActivity" );
        dictionary.put ( "advertising", "Advertising" );
        dictionary.put ( "language_chooser_dialog_title", "Choose your language" );
        dictionary.put ( "language", "Language" );






        Backendless.Persistence.of( "Dictionary" ).save( dictionary, new AsyncCallback<Map>() {
            public void handleResponse( Map response )
            {
                // new Contact instance has been saved
            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }


    private void loadNewDescription(){

    }




}
