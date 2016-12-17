package com.sopa.mvvc.datamodel.network.asynchttp;


import com.minimize.android.rxrecycleradapter.RxDataSource;
import com.sopa.mvvc.datamodel.Map;

import java.util.List;


public interface DataChangeListener {
	public void onDataChanged();
	public List<Map> getDataList();
	public RxDataSource<Map> getDataSource();
}
