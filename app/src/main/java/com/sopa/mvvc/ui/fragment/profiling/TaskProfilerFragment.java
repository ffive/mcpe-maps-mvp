package com.sopa.mvvc.ui.fragment.profiling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sopa.mvvc.R;
import com.sopa.mvvc.datamodel.profiling.Profiler;
import com.sopa.mvvc.presentation.presenter.profiling.TaskProfilerPresenter;
import com.sopa.mvvc.presentation.view.profiling.TaskProfilerView;

public class TaskProfilerFragment extends MvpAppCompatFragment implements TaskProfilerView {
    public static final String TAG = "TaskProfilerFragment";
    @InjectPresenter
    TaskProfilerPresenter mTaskProfilerPresenter;

    public static TaskProfilerFragment newInstance() {
        TaskProfilerFragment fragment = new TaskProfilerFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_profiler, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void updateProfileInfo(Profiler element) {

    }
}
