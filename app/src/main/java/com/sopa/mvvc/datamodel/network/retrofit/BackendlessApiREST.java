package com.sopa.mvvc.datamodel.network.retrofit;

import com.backendless.BackendlessCollection;
import com.sopa.mvvc.datamodel.Category;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yurikomlev on 11.12.16.
 */

public interface BackendlessApiREST {

@GET
public Observable<Response<ServiceGenerator.ProgressResponseBody>> download(@Url String url);


    @GET
    public Observable<BackendlessCollection<Category>> getCategories();
}
