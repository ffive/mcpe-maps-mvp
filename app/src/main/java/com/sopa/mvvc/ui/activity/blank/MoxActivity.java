package com.sopa.mvvc.ui.activity.blank;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ActivityMoxBinding;
import com.sopa.mvvc.datamodel.Category;
import com.sopa.mvvc.datamodel.MyDiffCallback;
import com.sopa.mvvc.datamodel.UserConfig;
import com.sopa.mvvc.presentation.presenter.blank.MoxPresenter;
import com.sopa.mvvc.presentation.presenter.blank.UserConfigPresenter;
import com.sopa.mvvc.presentation.view.blank.MoxView;
import com.sopa.mvvc.presentation.view.blank.UserConfigView;
import com.sopa.mvvc.ui.fragment.blank.CategoryListFragment;
import com.sopa.mvvc.ui.fragment.blank.UploadMapFragment;

import java.util.ArrayList;
import java.util.List;

public class MoxActivity extends MvpAppCompatActivity implements MoxView, UserConfigView {

    private static final String TAG = "MoxActivity : DEBUG";
    private static final String POSITION = "pos";

    @InjectPresenter(type = PresenterType.GLOBAL)
    UserConfigPresenter mUserConfigPresenter;

    @InjectPresenter(type = PresenterType.GLOBAL)
    MoxPresenter mMoxPresenter;

    ActivityMoxBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mox);

        setSupportActionBar(binding.toolbar);


        //binding.tabs.setup//(binding.container.pager, true);

        binding.container.pager.setOffscreenPageLimit(1);
        binding.container.pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        binding.container.pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            public void transformPage(View view, float position) {
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
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });
        binding.tabs.setupWithViewPager(binding.container.pager,true);

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mMoxPresenter.onPageSet(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }





    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        // adapter.addFrag(new DummyFragment("ANDROID"), "ANDROID");
        // adapter.addFrag(new DummyFragment("iOS"), "iOS");
        // adapter.addFrag(new DummyFragment("WINDOWS"), "WINDOWS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void showMyAppsDialog() {

    }

    @Override
    public void showNewApp(String message, String link, String pkg) {

    }

    @Override
    public void showRateDialog() {
    }

    @Override
    public void showEULA() {
    }

    @Override
    public void showRewardedAd() {
    }

    @Override
    public void showSearch() {
    }

    @Override
    public void hideSearch() {
    }

    @Override
    public void showInterstitial() {

    }

    @Override
    public void showLoading() {
        binding.container.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.container.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showTabs(List<Category> categories) {

        Log.d(TAG, "showTabs: sorry not implement");
    }


    @Override
    public void updateTabs(List<Category> categories, int position) {

        //position = binding.tabs.getSelectedTabPosition();
        ((MyAdapter) binding.container.pager.getAdapter()).updateItems(categories);
         setCurrentTab(position);

    }

    @Override
    public void showUploadDialog() {

    }


    public void setCurrentTab(int currentTab) {

        binding.container.pager.setCurrentItem(currentTab, true);

    }


    @Override
    public void onUpdatedSettings(UserConfig config) {

    }

    @Override
    public void sendLastTab(int lastTab) {
        setCurrentTab(lastTab);
    }


//------------         adapter          ------------ //

public class MyAdapter extends FragmentPagerAdapter {
    List<Category> categories;

    MyAdapter(FragmentManager fm) {
        super(fm);
        this.categories = new ArrayList<Category>();
        Category c = new Category();
        c.setCategory("loading");
        c.setObjectId("loading");
        categories.add(c);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getCategory();
    }

    @Override
    public Fragment getItem(int position) {
        return getCategoryFragment(categories.get(position).getObjectId());
    }

    void updateItems(List<Category> updated) {

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(updated, this.categories));
        notifyDataSetChanged();
        diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {

                categories = updated;
                notifyDataSetChanged();
                Log.d(TAG, "onInserted: " + count + " categories");
            }

            @Override
            public void onRemoved(int position, int count) {
                Log.d(TAG, "onRemoved: ");
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                Log.d(TAG, "onMoved: ");
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                Log.d(TAG, "onChanged: ");
                notifyDataSetChanged();

            }
        });
        //  diffResult.dispatchUpdatesTo(this);


    }

}


    private static Fragment getCategoryFragment(String categoryId) {
        Log.d(TAG, "getCategoryFragment: created categoryListFragment with Id" + categoryId);

        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putString("objectId", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    private static Fragment getUploadMapFragment() {
        Log.d(TAG, "getUploadMapFragment: created UploadMapFragment ");

        return new UploadMapFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
