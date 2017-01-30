package com.sopa.mvvc.datamodel.profiling;

import io.realm.RealmObject;

/**
 * Created by yurikomlev on 18.12.16.
 */

public class Profiler extends RealmObject {

    private int rlm;
    private int rxs;

    public int getBknd() {
        return bknd;
    }

    public void setBknd(int bknd) {
        this.bknd = bknd;
    }

    public int getRxs() {
        return rxs;
    }

    public void setRxs(int rxs) {
        this.rxs = rxs;
    }

    public int getRlm() {
        return rlm;
    }

    public void setRlm(int rlm) {
        this.rlm = rlm;
    }

    private int bknd;

}
