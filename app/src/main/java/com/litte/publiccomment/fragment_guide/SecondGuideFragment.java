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
public class SecondGuideFragment extends BaseFragment {

    @BindView(R.id.tv_second_skip)
    TextView tv_second_skip;

    public SecondGuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_guide, container, false);
        ButterKnife.bind(this,view);
        skip(tv_second_skip);
        return view;
    }

}
