package com.task.stackview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.task.stackview.R;


public class CardAdapter extends BaseAdapter {

    private int[] resIds;

    public CardAdapter(int... resIds) {
        this.resIds = resIds;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View container = inflater.inflate(R.layout.item_card, viewGroup, false);

        ImageView photo = (ImageView) container.findViewById(R.id.iv_photo);
        photo.setImageResource(resIds[i % 2]);

        return container;
    }

    @Override
    public int getCount() {
        return resIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return resIds[i];
    }

    public void addCard() {

    }

}
