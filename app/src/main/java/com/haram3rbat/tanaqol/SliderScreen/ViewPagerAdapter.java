package com.haram3rbat.tanaqol.SliderScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.haram3rbat.tanaqol.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int sliderAllImages[] = {
            R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.slider3,
            R.drawable.slider4,
            R.drawable.slider5,
    };

    int sliderAllTitle[] = {
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
    };

    int sliderAllDesc[] = {
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {return sliderAllTitle.length;}

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_screen,container,false);

        ImageView sliderImage = (ImageView) view.findViewById(R.id.sliderImage);
//        TextView sliderTitle = (TextView) view.findViewById(R.id.sliderTitle);
//        TextView sliderDesc = (TextView) view.findViewById(R.id.sliderDesc);

        sliderImage.setImageResource(sliderAllImages[position]);
//        sliderTitle.setText(this.sliderAllTitle[position]);
//        sliderDesc.setText(this.sliderAllDesc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}