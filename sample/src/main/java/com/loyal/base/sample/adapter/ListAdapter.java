package com.loyal.base.sample.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.loyal.base.adapter.ABasicListAdapter;
import com.loyal.base.adapter.ABasicListViewHolder;
import com.loyal.base.sample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends ABasicListAdapter<String, ListAdapter.ViewHolder> {

    public ListAdapter(Context context, List<String> arrList) {
        super(context, arrList);
    }

    @Override
    public int adapterLayout() {
        return R.layout.item;
    }

    @Override
    public void onViewHolder(ViewHolder holder, int position) {
        holder.itemText.setText(getItem(position));
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends ABasicListViewHolder {
        @BindView(R.id.itemText)
        AppCompatTextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
