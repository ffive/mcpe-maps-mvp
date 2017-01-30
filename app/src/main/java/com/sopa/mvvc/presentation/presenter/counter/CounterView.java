package com.sopa.mvvc.presentation.presenter.counter;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by yurikomlev on 18.12.16.
 */public interface CounterView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showCount(int count);
}