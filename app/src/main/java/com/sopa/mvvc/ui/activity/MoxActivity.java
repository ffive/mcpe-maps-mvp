package com.sopa.mvvc.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ActivityMoxBinding;
import com.sopa.mvvc.datamodel.local.MyDiffCallback;
import com.sopa.mvvc.datamodel.local.UserConfig;
import com.sopa.mvvc.datamodel.remote.backendless.Category;
import com.sopa.mvvc.mvp.presenter.entities.UserConfigPresenter;
import com.sopa.mvvc.mvp.presenter.screens.MoxPresenter;
import com.sopa.mvvc.mvp.view.entities.UserConfigView;
import com.sopa.mvvc.mvp.view.screens.MoxView;
import com.sopa.mvvc.ui.fragment.CategoryListFragment;
import com.sopa.mvvc.ui.fragment.UploadMapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//todo      backgroundtasksPresenter ( will show a Global spinning progress with int showing number of async calls to 1.Backenless, 2realm
// (reads/writes) , 3 ALiveObservables oO anything)   And include
//todo      it in global realm Log     log presenter   - write to live analytics to balance load
public class MoxActivity extends MvpAppCompatActivity implements MoxView, UserConfigView {

    private static final String TAG = "MoxActivity : DEBUG";

    @InjectPresenter( type = PresenterType.GLOBAL )
    UserConfigPresenter mUserConfigPresenter;

    @InjectPresenter
    MoxPresenter mMoxPresenter;


    ActivityMoxBinding binding;
    String userLocale = Locale.getDefault ( ).getLanguage ( ).toLowerCase ( );

    //--------   Create fragments for tabs ------- ///
    private static Fragment getCategoryFragment ( Category category ) {
        Log.d (TAG, "getCategoryFragment: created categoryListFragment with Id" + category.getCategory ( ));

        CategoryListFragment fragment = new CategoryListFragment ( );
        Bundle args = new Bundle ( );

        args.putString ("objectId", category.getObjectId ( ));

        fragment.setArguments (args);
        return fragment;
    }

    private static Fragment getUploadMapFragment ( ) {
        Log.d (TAG, "getUploadMapFragment: created UploadMapFragment ");
        return new UploadMapFragment ( );
    }


    //--------   Moxy View  methods implementation ------- ///

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);

        binding = DataBindingUtil.setContentView (MoxActivity.this, R.layout.activity_mox);
        // you
        // build
        // the project
        setSupportActionBar (binding.toolbar);


        //binding.counterWidgetOnActivity.init(getMvpDelegate());
        //  binding.count.
        setupViewPager ( );

    }

    @Override
    protected void onStop ( ) {
        Log.d (TAG, "onStop: ");

        super.onStop ( );
    }

    private void setupViewPager ( ) {
        binding.container.pager.setOffscreenPageLimit (1);
        binding.container.pager.setAdapter (new MyAdapter (getSupportFragmentManager ( )));
        binding.container.pager.setPageTransformer (true, new ViewPager.PageTransformer ( ) {
            private static final float MIN_SCALE = 0.75f;

            public void transformPage ( View view, float position ) {
                /*
                int pageWidth = view.getWidth();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 0) { // [-1,0]

                    // Use the default slide transition when moving to the left page
                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);

                } else if (position <= 1) { // (0,1]
                    // Fade the page out.
                    view.setAlpha(1 - position);

                    // Counteract the default slide transition
                    view.setTranslationX(pageWidth * -position);

                    // Scale the page down (between MIN_SCALE and 1)

                  float scaleFactor =  new AccelerateDecelerateInterpolator().getInterpolation(1-position);
                 //   float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));


                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
                */
            }

        });     //sliding animation

        binding.tabs.setupWithViewPager (binding.container.pager, true);
        binding.tabs.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener ( ) {
            @Override
            public void onTabSelected ( TabLayout.Tab tab ) {
                mMoxPresenter.onPageSet (tab.getPosition ( ));
            }

            @Override
            public void onTabUnselected ( TabLayout.Tab tab ) {

            }

            @Override
            public void onTabReselected ( TabLayout.Tab tab ) {

            }
        });
    }

    String detectuserLocale ( ) {
        return userLocale;
    }

    @Override
    public void setLanguagesList ( Map<String, String> languageMap, String userLang ) {
/*

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder (this);
        builder.setTitle (R.string.language_chooser_dialog_title);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> (this, android.R.layout.select_dialog_singlechoice);

        arrayAdapter.addAll (languageMap.values());  // localized {   0: "Русский" ,  2:"English", 3:"Deutch" }

        int defaultListPosition;
        if ( languageMap.get(userLang)!= null ){
            defaultListPosition = arrayAdapter.getPosition( languageMap.get(userLang) );
        }   else {
            defaultListPosition = arrayAdapter.getPosition( languageMap.get("English") );
        }

        builder.setSingleChoiceItems (arrayAdapter, defaultListPosition,
            ( dialogInterface, i ) -> mMoxPresenter.onLanguageSelected(i) );


        builder.setPositiveButton (android.R.string.ok,
                ( dialogInterface, i ) -> mMoxPresenter.onLanguageSelected(i) );

        builder.show();
*/


    }

    @Override
    public void sendLanguage(String language) {

    }

    @Override
    public void showMyAppsDialog ( ) {

    }

    @Override
    public void showNewApp ( String message, String link, String pkg ) {

    }

    @Override
    public void showRateDialog ( ) {
    }

    @Override
    public void showEULA ( ) {
    }

    @Override
    public void showRewardedAd ( ) {
    }

    @Override
    public void showSearch ( ) {
    }

    @Override
    public void hideSearch ( ) {
    }

    @Override
    public void showInterstitial ( ) {

    }

    @Override
    public void showLoading ( ) {
        binding.container.progressBar.setVisibility (View.VISIBLE);
    }

    @Override
    public void hideLoading ( ) {
        binding.container.progressBar.setVisibility (View.GONE);
    }

    @Override
    public void showTabs ( List<Category> categories ) {
        Log.d (TAG, "showTabs: sorry not implement");
    }

    @Override
    public void updateTabs ( List<Category> categories, int position ) {
        ( ( MyAdapter ) binding.container.pager.getAdapter ( ) ).updateItems (categories);
        setCurrentTab (position);
    }

    @Override
    public void showUploadDialog ( ) {

    }

    @Override
    public void sendLastTab ( int lastTab ) {
        setCurrentTab (lastTab);
    }

    public void setCurrentTab ( int currentTab ) {

        binding.container.pager.setCurrentItem (currentTab, true);

    }


    //--------   Adapter for tabs (viewpager) ------- ///

    //--------   UserConfig View methods implementation ------- ///
    @Override
    public void onUpdatedSettings ( UserConfig config ) {

    }

    //--------   Toolbar  and   menu  ------- ///
    @Override
    public boolean onCreateOptionsMenu ( Menu menu ) {
        getMenuInflater ( ).inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected ( MenuItem item ) {
        int id = item.getItemId ( );
        if ( id == R.id.action_settings ) {
            return true;
        }
        return super.onOptionsItemSelected (item);
    }


    class Lng {
        int pos;
        String code;

        public Lng ( int pos, String code ) {
            super ( );
            this.pos = pos;
            this.code = code;
        }
    }


    public class MyAdapter extends FragmentPagerAdapter {
        List<Category> categories;

        MyAdapter ( FragmentManager fm ) {
            super (fm);
            this.categories = new ArrayList<Category> ( );
            Category c = new Category ( );
            c.setCategory ("loading");
            c.setObjectId ("loading");
            categories.add (c);
        }

        @Override
        public int getCount ( ) {
            return categories.size ( );
        }

        @Override
        public CharSequence getPageTitle ( int position ) {
            return categories.get (position).getCategory ( );
        }

        @Override
        public Fragment getItem ( int position ) {
            return getCategoryFragment (categories.get (position));
        }

        void updateItems ( List<Category> updated ) {

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff (new MyDiffCallback (updated, this.categories));
            notifyDataSetChanged ( );
            diffResult.dispatchUpdatesTo (new ListUpdateCallback ( ) {
                @Override
                public void onInserted ( int position, int count ) {

                    categories = updated;
                    notifyDataSetChanged ( );
                    Log.d (TAG, "onInserted: " + count + " categories");
                }

                @Override
                public void onRemoved ( int position, int count ) {
                    Log.d (TAG, "onRemoved: ");
                }

                @Override
                public void onMoved ( int fromPosition, int toPosition ) {
                    Log.d (TAG, "onMoved: ");
                }

                @Override
                public void onChanged ( int position, int count, Object payload ) {
                    Log.d (TAG, "onChanged: ");
                    notifyDataSetChanged ( );

                }
            });
            //  diffResult.dispatchUpdatesTo(this);


        }

    }


}
