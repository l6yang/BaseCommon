package com.loyal.base.sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loyal.base.adapter.ABasicRecycleAdapter;
import com.loyal.base.adapter.ABasicRecyclerViewHolder;
import com.loyal.base.sample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleAdapter extends ABasicRecycleAdapter<String, RecycleAdapter.ViewHolder> {

    public RecycleAdapter(Context context, List<String> arrList) {
        super(context, arrList);
    }

    @Override
    public int adapterLayout() {
        return R.layout.item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(getConvertView(viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemText.setText(getItem(position));
    }

    public class ViewHolder extends ABasicRecyclerViewHolder {
        @BindView(R.id.itemText)
        AppCompatTextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(View itemView, OnItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
        }

        public ViewHolder(View itemView, OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener) {
            super(itemView, itemClickListener, itemLongClickListener);
        }

        @Override
        public void bindView(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
