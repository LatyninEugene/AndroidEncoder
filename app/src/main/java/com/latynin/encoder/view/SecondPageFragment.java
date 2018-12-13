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
 * {@link UsersCodeListFragment} и {@link CodeInfoFragment}
 * Используется в {@link MainActivity}
 */
public class SecondPageFragment extends Fragment {

    FragmentManager myFragmentManager;
    UsersCodeListFragment myFragment1;
    final static String TAG_2 = "FRAGMENT_2";

    public SecondPageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_page, container, false);
        myFragment1 = new UsersCodeListFragment();
        myFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerUsersCoders, myFragment1, TAG_2);
        fragmentTransaction.commit();
        return view;
    }
}
