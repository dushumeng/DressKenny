package com.fcdream.dress.kenny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.DressItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmdu on 2017/9/3.
 */

public class DressItemAdapter extends RecyclerView.Adapter<DressItemAdapter.ViewHolder> implements View.OnClickListener {

    private List<DressItem> dataList = new ArrayList<>();

    private Context context;

    private OnItemClickListener listener;

    private int selectPosition;

    public DressItemAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public DressItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_dress_info, parent, false);
        ViewHolder vh = new ViewHolder(view, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DressItem dressItem = dataList.get(position);
        Picasso.with(App.getAppInstance())
                .load(dressItem.img)
                .resize((int) App.getAppInstance().getResources().getDimension(R.dimen.list_page_content_dress_item_width)
                        , (int) App.getAppInstance().getResources().getDimension(R.dimen.list_page_content_dress_item_height))
                .centerCrop().into(holder.dressImage);
        holder.dressImage.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        DressItem item = dataList.get(position);
        if (listener != item) {
            listener.onItemClick(view, position, item);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView dressImage;
        public ImageView bgImage;

        public ViewHolder(View view, View.OnClickListener clickListener) {
            super(view);
            dressImage = (ImageView) view.findViewById(R.id.dress_item);
            dressImage.setOnClickListener(clickListener);
            bgImage = (ImageView) view.findViewById(R.id.dress_item_bg);
        }
    }

    public List<DressItem> getDataList() {
        return dataList;
    }
}
