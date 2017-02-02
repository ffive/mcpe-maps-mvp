package com.sopa.mvvc.mvp.presenter.helpers.counter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ItemCounterBinding;

public class CounterWidget extends FrameLayout implements CounterView {

    private MvpDelegate mParentDelegate;
    private MvpDelegate<CounterWidget> mMvpDelegate;

    @InjectPresenter
    CounterPresenter mCounterPresenter;

    ItemCounterBinding binding;

    public CounterWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_counter,this, true);
        binding.plusButton.setOnClickListener(view -> mCounterPresenter.onPlusClick());
    }

    public void init(MvpDelegate parentDelegate) {
        mParentDelegate = parentDelegate;

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getMvpDelegate().onSaveInstanceState();
        getMvpDelegate().onDetach();
    }

    public MvpDelegate<CounterWidget> getMvpDelegate() {
        if (mMvpDelegate != null) {
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(getId()));
        return mMvpDelegate;
    }


    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    public void showCount(int count) {
        binding.countText.setText(String.valueOf(count));
    }
}