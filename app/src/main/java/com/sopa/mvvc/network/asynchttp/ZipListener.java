package com.sopa.mvvc.network.asynchttp;

/**
 * Created by yurikomlev on 10.12.16.
 */

public interface ZipListener {

    //zipentry
    //xyentry

    void onEntry(String name, long bytesProcessed, long bytesTotal, boolean isDirectory, boolean isZip);


}
