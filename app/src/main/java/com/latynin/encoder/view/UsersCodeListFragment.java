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
 * Класс (Fragment) отображает список добавленых пользователем {@link com.latynin.encoder.model.User}
 * закодированных сообщений
 * Использует {@link RecyclerView} с реализованым адаптером {@link UsersCoderAdapter}
 */
public class UsersCodeListFragment extends Fragment {

    private static UsersCoderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_item_code_list, container, false);
        adapter = new UsersCoderAdapter(this);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    public static void updateData(){
        adapter.notifyDataSetChanged();
    }


}
