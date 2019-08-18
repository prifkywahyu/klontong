package com.klontong.mobileapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Rifky on 13-Oct-18.
 */

class SliderAdapter extends PagerAdapter {

    Context context;
    SliderAdapter(Context context) {
        this.context = context;
    }

    private int[] slide_images = {
            R.drawable.icon_eat,
            R.drawable.icon_sleep,
            R.drawable.icon_work,
    };

    private String[] slide_heading = {
            "MUDAH",
            "CEPAT",
            "RESPONSIF"
    };

    private String[] slide_desc = {
            "Sekarang pesan galon dan gas hingga isi ulang air dapat menjadi lebih mudah dan nyaman melalui Klontong",
            "Klontong dapat membuat aktivitas pesanan untuk warung Anda menjadi lebih cepat dan terjamin kualitasnya",
            "Pelayanan yang responsif dan tanggap dari Klontong membuat pengalaman belanja warung Anda lebih menarik"
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.slide_pager, container, false);

        ImageView images = view.findViewById(R.id.images);
        TextView heading = view.findViewById(R.id.heading);
        TextView desc = view.findViewById(R.id.descript);

        images.setImageResource(slide_images[position]);
        heading.setText(slide_heading[position]);
        desc.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
