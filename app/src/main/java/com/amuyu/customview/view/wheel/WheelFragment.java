package com.amuyu.customview.view.wheel;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amuyu.customview.R;

public class WheelFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wheel_frag, container, false);

        WheelView wheelView = (WheelView)view.findViewById(R.id.wheel);


        Resources res = getResources();
        wheelView.addItem(res.getColor(R.color.cpb_blue), "초밥", null);
        wheelView.addItem(res.getColor(R.color.cpb_blue_dark_1), "함박스테이크", null);
        wheelView.addItem(res.getColor(R.color.cpb_blue_dark_2), "샐러드", null);
        wheelView.addItem(res.getColor(R.color.cpb_blue_dark_3), "닭강정", null);
        wheelView.invalidate();

        return view;
    }
}
