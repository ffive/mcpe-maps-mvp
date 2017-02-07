package com.sopa.mvvc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
          //  BackendlessUser backendlessUser;
            switch ( resultCode ) {


                case Defaults.VALID_LOGIN: {

                    //backendlessUser = ( BackendlessUser ) data.getSerializableExtra ("backendless_user");
                    startActivity (new Intent (MainActivity.this, MoxActivity.class));
                    break;
                }
                case Defaults.LOGIN_SUCCESS: {

                  // backendlessUser = ( BackendlessUser ) data.getSerializableExtra ("backendless_user");
                    startActivity (new Intent (MainActivity.this, MoxActivity.class));
                    break;
                }
                default:{startActivity (new Intent (MainActivity.this, MoxActivity.class));}
            }



              //
      //  }
    }
}
    }
