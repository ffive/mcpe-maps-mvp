package com.sopa.mvvc.mvp.presenter.helpers.language;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ListItemLanguageBinding;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private List<String> mDataset;
    private int itemClickedPosition;

    public LanguageAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    public int getItemClickedPosition() {
        return itemClickedPosition;
    }

    @Override
    public LanguageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemLanguageBinding binding = ListItemLanguageBinding.inflate(inflater, parent, false);
        return new LanguageAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.binding.textView.setText(mDataset.get (position));
        holder.binding.radioButton.setChecked(position == itemClickedPosition);

        holder.binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickedPosition = position;

/*                if (position == mCheckedPostion) {
                    holder.checkBox.setChecked(false);
                    mCheckedPostion = -1;
                } else {
                    mCheckedPostion = position;
                    notifyDataSetChanged();
                }*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListItemLanguageBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }

}