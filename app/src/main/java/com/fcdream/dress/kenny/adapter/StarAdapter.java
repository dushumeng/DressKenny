package com.fcdream.dress.kenny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.api.StarResult;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by shmdu on 2017/9/3.
 */

public class StarAdapter extends MyUltimateViewAdapter<StarResult.StarInfo> implements View.OnClickListener {

    private OnItemClickListener listener;

    private int selectPosition = -1;

    public StarAdapter(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_dress_info, parent, false);
        ViewHolder vh = new ViewHolder(view, this);
        return vh;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        StarResult.StarInfo item = dataList.get(position);
        if (listener != item) {
            listener.onItemClick(view, position, item);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder mViewHolder = (ViewHolder) holder;
            StarResult.StarInfo item = dataList.get(position);
            Picasso.with(App.getAppInstance())
                    .load(item.img)
                    .resize((int) App.getAppInstance().getResources().getDimension(R.dimen.list_page_content_dress_item_width), (int) App.getAppInstance().getResources().getDimension(R.dimen.list_page_content_dress_item_height))
                    .centerCrop()
                    .placeholder(R.drawable.default_dress_big)
                    .into(mViewHolder.dressImage);
            mViewHolder.dressImage.setTag(position);
            //mViewHolder.num.setText(position + "");
            if (selectPosition == position) {
                mViewHolder.bgImage.setVisibility(View.VISIBLE);
            } else {
                mViewHolder.bgImage.setVisibility(View.GONE);
            }
        }
    }

    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        final public ImageView dressImage;
        final public ImageView bgImage;
        final public TextView num;

        public ViewHolder(View view, View.OnClickListener clickListener) {
            super(view);
            dressImage = (ImageView) view.findViewById(R.id.dress_item);
            dressImage.setOnClickListener(clickListener);
            bgImage = (ImageView) view.findViewById(R.id.dress_item_bg);
            num = (TextView) view.findViewById(R.id.dress_num);
        }
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}
