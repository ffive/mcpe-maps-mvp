package com.sopa.mvvc.mvp.presenter.helpers.language;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sopa.mvvc.R;
import com.sopa.mvvc.databinding.ListItemLanguageBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private List<String> mDataset;
    private List<String> mKeySet;
    private int itemClickedPosition=-1;
    private RadioButton prevRadioButton;
    private String defaultLanguage;

    public LanguageAdapter(List<String> myData, List<String> myKeySet) {
        this.mDataset = myData;
        this.mKeySet = myKeySet;
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

        holder.binding.textView.setText(mDataset.get(position));
  //      holder.binding.radioButton.setChecked(position == itemClickedPosition);

        String tvText = mKeySet.get(position);
        Log.d("KEK", "onBindViewHolder: KEYSET = " +  mKeySet.get(position));

        Log.d("KEK", "onBindViewHolder: DEFAULT LANGUAGE = " +  defaultLanguage );

         if ( mKeySet.get(position).contains(defaultLanguage) && itemClickedPosition == -1){
            itemClickedPosition = position;
            holder.binding.radioButton.setChecked(true);
            prevRadioButton = holder.binding.radioButton;
        }

        holder.binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickedPosition = position;

               // if (prevRadioButton!=null){
                    prevRadioButton.setChecked(false);
            //    }

                holder.binding.radioButton.setChecked(true);
                prevRadioButton = holder.binding.radioButton;
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size ();
    }

    public void setDefaultLanguage( String defaultLanguage ){
        this.defaultLanguage = defaultLanguage;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListItemLanguageBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }

}