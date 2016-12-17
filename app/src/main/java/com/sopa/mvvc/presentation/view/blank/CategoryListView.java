package com.sopa.mvvc.presentation.view.blank;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.sopa.mvvc.datamodel.Map;

import java.util.List;

public interface CategoryListView extends MvpView {


    //void openDetails(String objectId);

    @StateStrategyType(AddToEndStrategy.class)
    void updateList(List<Map> updatedList );

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showList(List<Map> items);

    void showProgress();

    void hideProgress();

    //void unlockItem();

}
