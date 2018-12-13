package com.latynin.encoder.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.latynin.encoder.R;


/**
 * Класс (Fragment) исполюзуется для отображения информации
 * о зашифрованом сообщении.
 * Вызывается из {@link UsersCoderAdapter}
 */
public class CodeInfoFragment extends Fragment {

    private static final String NAME = "param1";
    private static final String CODE = "param2";
    private static final String TYPE = "param3";
    private static final String KEY = "param4";

    private String name;
    private String code;
    private String type;
    private String key;

    private TextView tName;
    private TextView tCode;
    private TextView tType;
    private TextView tKey;

    public static CodeInfoFragment newInstance(String name, String code, String type, String key) {
        CodeInfoFragment fragment = new CodeInfoFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(CODE, code);
        args.putString(TYPE, type);
        args.putString(KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME);
            code = getArguments().getString(CODE);
            type = getArguments().getString(TYPE);
            key = getArguments().getString(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_code_info, container, false);
        tName = v.findViewById(R.id.tName);
        tCode = v.findViewById(R.id.tCode);
        tKey = v.findViewById(R.id.tKey);
        tType = v.findViewById(R.id.tType);

        tName.setText(name);
        tCode.setText(code);
        tKey.setText(key);
        tType.setText(type);
        return v;
    }

}
