package com.sopa.mvvc.mvp.view.helpers.profiling;

import com.arellomobile.mvp.MvpView;
import com.sopa.mvvc.datamodel.local.profiling.Profiler;

public interface TaskProfilerView extends MvpView {


    void updateProfileInfo(Profiler element);
}
