package com.sopa.mvvc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.backendless.BackendlessUser;
import com.sopa.mvvc.ui.activity.MoxActivity;

public class MainActivity extends AppCompatActivity {

    public final static int request_code_login = 421;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


        startActivityForResult (new Intent (MainActivity.this, com.backendless.mvp.login.LoginActivity.class), request_code_login);


    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {

        BackendlessUser backendlessUser = (BackendlessUser) data.getSerializableExtra("backendless_user");

        if (backendlessUser.getProperty("language").toString().equals("")){
            startActivity (new Intent (MainActivity.this, MoxActivity.class));
        }   else {
            startActivity (new Intent (MainActivity.this, MoxActivity.class));
        }


      //
        //  super.onActivityResult (requestCode, resultCode, data);
    }
}
