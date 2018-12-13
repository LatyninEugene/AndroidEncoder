package com.latynin.encoder.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.latynin.encoder.R;
import com.latynin.encoder.control.BitCoder;
import com.latynin.encoder.control.Coder;
import com.latynin.encoder.control.DBCodes;
import com.latynin.encoder.control.ShiftCoder;
import com.latynin.encoder.control.XORCoder;
import com.latynin.encoder.model.Code;
import com.latynin.encoder.model.User;

import java.util.concurrent.ExecutionException;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Класс (Fragment) используется для отображения
 * окна кодирования. В зависимости от того какой
 * {@link Coder} выбран, таким и будет происходить
 * кодирование.
 */

public class EncoderFragment extends Fragment {

    protected static String tagNow;
    protected TextView typeCode;
    protected Button btnDecode;
    protected Button btnEncode;
    protected Coder coder;

    private EditText text;
    private TextView textDecoded;
    private TextView textEncoded;
    private EditText key;
    private EditText name;
    private Button btnAdd;
    private Button btnCopyEn;
    private Button btnCopyDe;
    private String keyStr = "";

    private ClipboardManager clipboardManager;
    private ClipData clipData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encoder, container, false);
        text = view.findViewById(R.id.text);
        key = view.findViewById(R.id.key);
        textDecoded = view.findViewById(R.id.decode);
        textEncoded = view.findViewById(R.id.encode);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnDecode = view.findViewById(R.id.btnDecode);
        btnEncode = view.findViewById(R.id.btnEncode);
        btnCopyEn = view.findViewById(R.id.btnCopyEn);
        btnCopyDe = view.findViewById(R.id.btnCopyDe);
        typeCode = view.findViewById(R.id.TypeCode);
        name = view.findViewById(R.id.codeName);

        typeCode.setText(this.getTag().split("-")[1]);
        switch (typeCode.getText().toString()){
            case "BitCoder":
                coder = new BitCoder();
                break;
            case "ShiftCoder":
                coder = new ShiftCoder();
                break;
            case "XORCoder":
                coder = new XORCoder();
                break;
        }

        clipboardManager=(ClipboardManager) view.getContext().getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
        tagNow = this.getTag();
        btnDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDecoded.setText(coder.decode(text.getText().toString().toCharArray(), key.getText().toString().toCharArray()));

            }
        });
        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyStr = key.getText().toString();
                textEncoded.setText(coder.encode(text.getText().toString().toCharArray(), key.getText().toString().toCharArray()));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBCodes c = new DBCodes();
                c.execute("add",textEncoded.getText().toString(),name.getText().toString(), typeCode.getText().toString(),keyStr);
                try {
                    if(c.get()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Text add ", Toast.LENGTH_SHORT).show();
                        User.getInstance().getCodes().add(new Code(name.getText().toString(),
                                textEncoded.getText().toString(),keyStr, typeCode.getText().toString()));
                        UsersCodeListFragment.updateData();

                    }else Toast.makeText(getActivity().getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        btnCopyEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipData = ClipData.newPlainText("code-x", textEncoded.getText());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(v.getContext().getApplicationContext(), "Text coped ", Toast.LENGTH_SHORT).show();
            }
        });

        btnCopyDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipData = ClipData.newPlainText("code-y", textDecoded.getText());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(v.getContext().getApplicationContext(), "Text coped ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
