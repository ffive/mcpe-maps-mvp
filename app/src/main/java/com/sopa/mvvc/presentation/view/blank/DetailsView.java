package com.sopa.mvvc.presentation.view.blank;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.sopa.mvvc.datamodel.Map;

public interface DetailsView extends MvpView {


    void showDialog();
    void showProgress();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateProgress(int percent);

    @StateStrategyType(SkipStrategy.class)
    void launchGame();

    void onAddedToFavourites();

    void setCached();

    void bindMap(Map map);


}
