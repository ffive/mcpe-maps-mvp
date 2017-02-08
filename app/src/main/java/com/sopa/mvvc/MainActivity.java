package com.sopa.mvvc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.backendless.BackendlessUser;
import com.backendless.mvp.login.Defaults;
import com.sopa.mvvc.ui.activity.MoxActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


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
    
}
