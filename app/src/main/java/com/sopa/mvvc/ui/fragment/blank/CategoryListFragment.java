package com.sopa.mvvc.ui.fragment.blank;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.minimize.android.rxrecycleradapter.RxDataSource;
import com.minimize.android.rxrecycleradapter.SimpleViewHolder;
import com.sopa.mvvc.DetailsTransition;
import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.FragmentCategoryListBinding;
import com.sopa.mvvc.databinding.ListItemCardBinding;
import com.sopa.mvvc.datamodel.Category;
import com.sopa.mvvc.datamodel.Map;
import com.sopa.mvvc.presentation.presenter.blank.CategoryListPresenter;
import com.sopa.mvvc.presentation.view.blank.CategoryListView;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * I CAN SHOW ANY java.util.List<Map>
 */
public class CategoryListFragment extends MvpAppCompatFragment implements CategoryListView {

    public static final String TAG = "CategoryListFragment";

    @InjectPresenter
    CategoryListPresenter mCategoryListPresenter;

    @ProvidePresenter
    CategoryListPresenter getPresenter() {
        return new CategoryListPresenter((Category)getArguments().getSerializable("objectId"));
    }


    FragmentCategoryListBinding binding;
    private RxDataSource<Map> dataSource;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_list, container, false);
        binding.myRecyclerView.setHasFixedSize(false);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.myRecyclerView.setLayoutManager(mLayoutManager);



        //todo refactor , move everything except update to presenter somehow
        dataSource = new RxDataSource<Map>(new ArrayList<>());
        dataSource.bindRecyclerView(binding.myRecyclerView, R.layout.list_item_card)
                .subscribe(
                        onNext
                );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        mCategoryListPresenter.iWantList();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showList(List<Map> items) {


    }

    private Action1<SimpleViewHolder<Map, ViewDataBinding>> onNext = new Action1<SimpleViewHolder<Map, ViewDataBinding>>() {
        @Override
        public void call(SimpleViewHolder<Map, ViewDataBinding> mapHolder) {
            ListItemCardBinding b = (ListItemCardBinding) mapHolder.getViewDataBinding();
            Map map = mapHolder.getItem();
            if (b != null) {
                b.getRoot().setOnClickListener(view -> {
                    if (map.isLocked()) {
                        mCategoryListPresenter.unlock(map);
                    } else {
                        CategoryListFragment.this.openDetailsWithTransition(map, b.imageView);
                    }
                });
                b.setMap(map);

                //b.mapName.setText(map.getName());
                //b.button.setText(R.string.btn_unlock);
                //Picasso.with(b.getRoot().getContext()).load(map.getI_url()).fit().into(b.imageView);
                //b.button.setVisibility(map.isLocked() ? View.VISIBLE : View.GONE);
            }
        }
    };

    private void openDetailsWithTransition(Map map, final ImageView sharedView) {


        mCategoryListPresenter.onMapCardClicked(map);

        Fragment details = DetailsFragment.newInstance();
        //details.getArguments().putString("map",map.getObjectId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            details.setSharedElementEnterTransition(new DetailsTransition());
            details.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            details.setSharedElementReturnTransition(new DetailsTransition());
        }



        Bundle args = new Bundle();
        args.putString("map", map.getObjectId());
        details.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(sharedView, "transition_image")
                .replace(R.id.frag_container,
                        details)
                .addToBackStack("myStack")
                .commit();


       // (getActivity().
    }


    @Override
    public void showProgress() {
        binding.progressBar2.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar2.setVisibility(View.INVISIBLE);

    }

    @Override
    public void updateList(List<Map> updatedList) throws NullPointerException {

        if (dataSource == null) showList(updatedList);

        else {
            dataSource.updateDataSet(updatedList);
            dataSource.updateAdapter();
        }

    }


}
