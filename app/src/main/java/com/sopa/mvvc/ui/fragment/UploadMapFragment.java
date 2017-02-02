package com.sopa.mvvc.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ConstraintBinding;
import com.sopa.mvvc.datamodel.remote.backendless.UploadMap;
import com.sopa.mvvc.mvp.presenter.entities.UploadMapPresenter;
import com.sopa.mvvc.mvp.view.entities.UploadMapView;

import java.util.List;

public class UploadMapFragment extends MvpAppCompatFragment implements UploadMapView {
    public static final String TAG = "UploadMapFragment";
    @InjectPresenter
    UploadMapPresenter mUploadMapPresenter;

    public static UploadMapFragment newInstance() {
        UploadMapFragment fragment = new UploadMapFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    ConstraintBinding binding;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.constraint, container, false);
        binding.button3.setOnClickListener(v -> {

            UploadMap newMap = new UploadMap();
            newMap.setDescription(binding.edtDescription.getText().toString());
            newMap.setName(binding.edtAuthor.getText().toString());

            mUploadMapPresenter.onClicked(newMap);

        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showProgress() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Snackbar.make(binding.getRoot(), "SUCCESS", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setCategories(List<String> categories) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mUploadMapPresenter.onCategoryChosen(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


}

