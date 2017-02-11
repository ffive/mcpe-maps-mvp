package com.sopa.mvvc.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.loopj.android.http.SyncHttpClient;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.FragmentDetailsBinding;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Dictionary;
import com.sopa.mvvc.datamodel.remote.backendless.Map;
import com.sopa.mvvc.mvp.presenter.screens.DetailsPresenter;
import com.sopa.mvvc.mvp.view.screens.DetailsView;
import com.sopa.mvvc.network.asynchttp.LoadingListener;
import com.sopa.mvvc.network.asynchttp.MapDownloader;
import com.sopa.mvvc.network.retrofit.BackendlessApiREST;
import com.sopa.mvvc.network.retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsFragment extends MvpAppCompatFragment implements DetailsView {

    private static final String TAG = "map";
    private static final String mcpe_dir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/games/com.mojang/minecraftWorlds/";
    public String[] allowedContentTypes = new String[]{"application/octet-stream", "text/html; charset=ISO-8859-1", "image/png", "image/jpeg",
            "image/bmp", "application/pdf", "text/html; charset=UTF-8", "image/png;charset=UTF-8"};
      @InjectPresenter
    DetailsPresenter mDetailsPresenter;



    private @NonNull Map map;

    private ProgressDialog progress;
    private BackendlessApiREST service;
    private FragmentDetailsBinding binding;

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment ();
        return fragment;
    }

    @BindingAdapter("img:url")
    public static void imgLoad(ImageView imageView, String url) {

        Picasso.with(imageView.getContext()).load(url).into(imageView);

    }

    @BindingAdapter(value = {"android:src", "placeHolder"},
            requireAll = false)
    public static void setImageUrl(ImageView view, String url,
                                   int placeHolder) {
        RequestCreator requestCreator = Picasso.with(view.getContext()).load(url);
        if (placeHolder != 0) {
            requestCreator.placeholder(placeHolder);
        }
        requestCreator.into(view);
    }

    @ProvidePresenter
    DetailsPresenter getPresenter() {
        return new DetailsPresenter(getArguments().getString("map"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {



        binding = FragmentDetailsBinding.inflate (inflater, root, false);



        service = ServiceGenerator.createService(BackendlessApiREST.class);

       // map = Realm.getDefaultInstance().where(Map.class).equalTo("objectId", getArguments().getString("map")).findFirst();    //todo realm read
       // realm.beginTransaction();
      //  map.setCached(isZipCached(map));
      //  realm.commitTransaction();


        binding.setPresenter(mDetailsPresenter);

        //binding.ratingBar2.setRating(map.getRating().floatValue());


       // if (!map.isRated()) {
         //   binding.ratingBar2.setRating(0);
           // binding.rateBtn.setVisibility(View.VISIBLE);
        //}

        //binding.setMap(map);
        isStoragePermissionGranted();


        binding.rateBtn.setOnClickListener((v) -> {
            tryRate((int) binding.ratingBar2.getRating());
        });

        binding.ratingBar2.setOnRatingBarChangeListener(( simpleRatingBar, rating, fromUser ) -> {
            int color = DetailsFragment.this.generateColor(rating);
            simpleRatingBar.setFillColor(color);
        });

        binding.ratingBar2.setOnDragListener(( view, dragEvent ) -> false);


        progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setMax(100);

/*
        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, "Try the  " + map.getName() + " Minecraft PE map from" )//+ (UserConfig.dictionary.market_url, getActivity().getPackageName())) todo:  link
                .setType("text/plain");
*/
        //((MainActivity) getActivity()).setShareIntent(shareIntent);  TODO:


        return binding.root;
    }

    private int generateColor(float rating) {
        int red = (int) (255 - (Math.abs(rating - 2.5)) * 102);
        int green = (int) (Math.abs(rating - 2.5) * 102 - 128);
        return Color.rgb(red, green, 0);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }


    }

    public void download(String url, String url_b) {


        LoadingListener listener = new LoadingListener() {
            @Override
            public void loadingFinished() {
                Log.d("DetailsFragment:", "loadingFinished");
                launchGame();
            }

            @Override
            public void onFailure() {
                Log.d("DetailsFragment", "onFailure");
            }

            @Override
            public void onBeginDownload() {
                Log.d("DetailsFragment", "onBeginDownload");
            }

            @Override
            public void onBeginUnZip() {
                Log.d("DetailsFragment", "onBeginUnZip");
            }

            @Override
            public void onProgressUpdate(int progresspercent) {

                Log.d("DetailsFragment", "onProgressUpdate " + progresspercent);

            }

            @Override
            public void onFinishedUnzip() {
                Log.d("DetailsFragment", "onFinishedUnzip");
            }
        };

        Observable<String> strings = Observable.just(url, url_b);

        strings.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::workingLinkObservable)
                .filter(String::isEmpty)
                .first()
                .doOnError(Throwable::printStackTrace)
                .subscribe(workingUrl -> {
                    try {
                        new MapDownloader().downloadMap(url, listener);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        //Toast.makeText(getActivity(), (UserConfig.dictionary.toast_error_map_download), Toast.LENGTH_LONG).show();//replace with snackbar
    }

    private boolean isZipCached(Map map) {
        return new File(mcpe_dir + URLUtil.guessFileName(map.getMap_url(), null, "application/zip")).exists();
    }

    private void tryRate(int rating)  {

      mDetailsPresenter.onRateChanged(rating);
    }

    //Permissions listener
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private Observable<String> workingLinkObservable(String url) {

        SyncHttpClient client = new SyncHttpClient();

        Observable.OnSubscribe<String> onSubscribe = sub -> {

            sub.add(new Subscription() {
                @Override
                public void unsubscribe() {

                    Log.e("unsubscribe ", "");
                    //what to do when unsubscribed  for any reason
                }

                @Override
                public boolean isUnsubscribed() {
                    return false;
                }

            });
        };


        return Observable.create(onSubscribe).doOnError(Throwable::printStackTrace).subscribeOn(Schedulers.newThread());

    }

    public void goDownload(String url) {

        //i want a good string///or not yet ;)

    }

    public void unlock() {
     mDetailsPresenter.unlock ();
    }

    public boolean canAccessMCPE() {
        try {
            ApplicationInfo mcAppInfo = getActivity().getPackageManager().getApplicationInfo("com.mojang.minecraftpe", 0);
            return mcAppInfo.sourceDir.equalsIgnoreCase(mcAppInfo.publicSourceDir);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Launch minecraft client
    public void openApp() {
        // __showInterstitial
        new AlertDialog.Builder(getContext())
                .setTitle((UserConfig.dictionary.dialog_launch_title))
                .setMessage((UserConfig.dictionary.dialog_launch_message))
                .setPositiveButton((UserConfig.dictionary.dialog_launch_btn_ok), (dialogInterface, i) -> {
                            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.mojang.minecraftpe");
                            if (intent != null) {
                                startActivity(intent.addCategory(Intent.CATEGORY_LAUNCHER));
                            }
                        }
                )
                .setNegativeButton((UserConfig.dictionary.dialog_launch_btn_no), null)
                .create().show();
    }

    public Observable<File> download(String id) {
        return service.download(id)
                .flatMap(this::saveFile);
    }

    public Observable<File> saveFile(Response<ServiceGenerator.ProgressResponseBody> response) {
        return Observable.create((Observable.OnSubscribe<File>) subscriber -> {
            try {
                // you can access headers of response
                String header = response.headers().get("Content-Disposition");
                // this is specific case, it's up to you how you want to save your file
                // if you are not downloading file from direct link, you might be lucky to obtain file name from header
                String fileName = header.replace("attachment; filename=", "");
                // will create file in global Music directory, can be any other directory, just don't forget to handle permissions
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), fileName);

                BufferedSink sink = Okio.buffer(Okio.sink(file));
                // you can access body of response
                sink.writeAll(response.body().source());
                sink.close();
                subscriber.onNext(file);
                subscriber.onCompleted();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void updateProgress(int percent) {

    }

    public void launchGame() {
        if (canAccessMCPE()) {
            openApp();
        }
    }

    @Override
    public void onAddedToFavourites() {

    }

    @Override
    public void setCached() {

    }

    @Override
    public void bindMap(Map map) {
        binding.setMap(map);
    }
}

