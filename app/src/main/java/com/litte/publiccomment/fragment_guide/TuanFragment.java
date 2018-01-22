package com.litte.publiccomment.fragment_guide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.litte.publiccomment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TuanFragment extends Fragment {


    public TuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuan, container, false);
    }

}
