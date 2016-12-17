package com.sopa.mvvc.datamodel.network.asynchttp;

/**
 *
 */
public interface LoadingListener {
	public void loadingFinished();
	public void onFailure();

	public void onBeginDownload();

	public void onBeginUnZip();

	public void onProgressUpdate(int progresspercent);

	public void onFinishedUnzip();
}
