package com.sopa.mvvc.presentation.view.profiling;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.profiling.Profiler;

public interface TaskProfilerView extends MvpView {


    void updateProfileInfo(Profiler element);
}
