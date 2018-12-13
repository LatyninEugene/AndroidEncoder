package com.latynin.encoder.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
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
import com.latynin.encoder.control.ComboCoder;
import com.latynin.encoder.model.Code;
import com.latynin.encoder.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Рассширенный {@link EncoderFragment} отображает
 * окно для кодирования с помощью {@link ComboCoder}
 */
public class ComboEncoderFragment extends EncoderFragment implements View.OnClickListener{

    private EditText text;
    private TextView textDecoded;
    private TextView textEncoded;
    private TextView textAlg;
    private EditText key;
    private EditText name;
    private Button btnAdd;
    private Button btnCopyEn;
    private Button btnCopyDe;
    private ComboCoder coder;

    private Button btnDel;
    private Button btnBit;
    private Button btnShift;
    private Button btnXOR;

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
        View view = inflater.inflate(R.layout.fragment_combo_encoder, container, false);
        text = view.findViewById(R.id.text);
        key = view.findViewById(R.id.key);
        textDecoded = view.findViewById(R.id.decode);
        textEncoded = view.findViewById(R.id.encode);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnDecode = view.findViewById(R.id.btnDecode);
        btnEncode = view.findViewById(R.id.btnEncode);
        btnCopyEn = view.findViewById(R.id.btnCopyEn);
        btnCopyDe = view.findViewById(R.id.btnCopyDe);
        textAlg = view.findViewById(R.id.codeAlg);
        name = view.findViewById(R.id.codeName);
        btnDel = view.findViewById(R.id.btnDel);
        btnBit = view.findViewById(R.id.btnBit);
        btnShift = view.findViewById(R.id.btnShift);
        btnXOR = view.findViewById(R.id.btnXOR);

        coder = new ComboCoder();
        clipboardManager=(ClipboardManager) view.getContext().getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
        tagNow = this.getTag();

        btnBit.setOnClickListener(this);
        btnShift.setOnClickListener(this);
        btnXOR.setOnClickListener(this);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                if(coder.coders.size() > 0) {
                    coder.coders.remove(coder.coders.size() - 1);
                    for (Coder c : coder.coders) {
                        str += " -> " + c.getClass().getName().split("\\.")[4];
                    }
                }
                if(coder.coders.size() <= 1){
                    str += " -> ";
                }
                textAlg.setText(str);
            }
        });

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
                c.execute("add",textEncoded.getText().toString(),name.getText().toString(),textAlg.getText().toString(),keyStr);
                try {
                    if(c.get()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Text add ", Toast.LENGTH_SHORT).show();
                        User.getInstance().getCodes().add(new Code(name.getText().toString(),
                                textEncoded.getText().toString(),keyStr,textAlg.getText().toString()));
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

    @Override
    public void onClick(View v) {
        String str = ((Button) v).getText().toString();
        try {
            System.out.println(BitCoder.class.getName());
            Class c = Class.forName("com.latynin.encoder.control."+str);
            Constructor constructor = c.getDeclaredConstructor();
            Coder coder = (Coder) constructor.newInstance();
            if(this.coder.coders.size() == 3){
                this.coder.coders.remove(0);
            }
            this.coder.coders.add(coder);
        } catch (ClassNotFoundException | NoSuchMethodException
                | IllegalAccessException | java.lang.InstantiationException
                | InvocationTargetException e) {
            str = " -> ";
            textAlg.setText(str);
            e.printStackTrace();
            return;
        }
        str = "";
        for (Coder c :this.coder.coders) {
            str+=" -> "+c.getClass().getName().split("\\.")[4];
        }
        textAlg.setText(str);
    }
}
