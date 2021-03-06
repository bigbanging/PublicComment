package com.litte.publiccomment.fragment_guide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.litte.publiccomment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdGuideFragment extends BaseFragment {

    @BindView(R.id.tv_third_skip)
    TextView tv_third_skip;

    public ThirdGuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_guide, container, false);
        ButterKnife.bind(this,view);
        skip(tv_third_skip);
        return view;
    }

}
