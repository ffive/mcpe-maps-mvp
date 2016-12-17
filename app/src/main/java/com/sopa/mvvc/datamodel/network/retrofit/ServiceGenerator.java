package com.sopa.mvvc.datamodel.network.retrofit;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
   static final ProgressListener progressListener = (bytesRead, contentLength, done) -> {
        System.out.println(bytesRead);
        System.out.println(contentLength);
        System.out.println(done);
        System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
    };

    private static final String secret_key ="6098F674-3ED6-20A6-FFCC-532428A1F000"; //"7706C5B1-0C8A-6259-FFE5-307BB1893700";
    private static final String app_id = "5E95CD38-592C-98B5-FFDE-7B36933D8700";

    public static final String API_BASE_URL = "http://api.backendless.com/v1/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, @Nullable final String authToken) {

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("secret-key", secret_key)
                            .header("application-id", app_id)
                            .header("application-type", "REST")
                            .header("Content-type", "application/json")
                            .method(original.method(), original.body());
                    if (authToken != null) {
                        requestBuilder.addHeader("user-token", authToken);
                    }
                    Request request = requestBuilder.build();
                    Response originaResponsel =chain.proceed(request);
                    return originaResponsel.newBuilder()
                            .body(new ProgressResponseBody(originaResponsel.body(),progressListener ))
                            .build();
                }
            });


        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();

        return retrofit.create(serviceClass);
    }



    //ANy request progress
    public static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override public long contentLength() {
            return responseBody.contentLength();
        }

        @Override public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }


}