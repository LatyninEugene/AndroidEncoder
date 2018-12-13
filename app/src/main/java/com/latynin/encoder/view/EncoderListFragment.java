package com.latynin.encoder.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latynin.encoder.R;

/**
 * Класс (Fragment) отображает список кодировок {@link com.latynin.encoder.control.Coder}
 * Использует {@link RecyclerView} с реализованым адаптером {@link EncodersAdapter}
 */
public class EncoderListFragment extends Fragment {

    private static EncodersAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_item_encoder_list, container, false);
        adapter = new EncodersAdapter(this);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}
