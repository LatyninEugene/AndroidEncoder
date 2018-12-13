package com.latynin.encoder.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latynin.encoder.R;

/**
 * Класс (Fragment), контейнер, служит для отображения
 * {@link EncoderListFragment} и {@link EncoderFragment}
 * Используется в {@link MainActivity}
 */
public class FirstPageFragment extends Fragment {

    FragmentManager myFragmentManager;
    EncoderListFragment myFragment1;
    final static String TAG_1 = "FRAGMENT_1";

    public FirstPageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encoder_page, container, false);
        myFragment1 = new EncoderListFragment();
        myFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerCoders, myFragment1, TAG_1);
        fragmentTransaction.commit();
        return view;
    }
}
