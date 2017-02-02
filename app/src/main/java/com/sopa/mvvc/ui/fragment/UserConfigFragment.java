package com.sopa.mvvc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.sopa.mvvc.R;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.mvp.presenter.entities.UserConfigPresenter;
import com.sopa.mvvc.mvp.view.entities.UserConfigView;

public class UserConfigFragment extends MvpAppCompatFragment implements UserConfigView {
    public static final String TAG = "UserConfigFragment";

    @InjectPresenter(type = PresenterType.GLOBAL)
    UserConfigPresenter mUserConfigPresenter;

    public static UserConfigFragment newInstance() {
        UserConfigFragment fragment = new UserConfigFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_config, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onUpdatedSettings(UserConfig config) {

    }

    @Override
    public void sendLastTab(int lastTab) {

    }
}
