package com.litte.publiccomment.fragment_guide;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.litte.publiccomment.activity.MainActivity;

/**
 * Created by litte on 2018/1/20.
 */

public class BaseFragment extends Fragment {
    public void skip(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
    }
}
