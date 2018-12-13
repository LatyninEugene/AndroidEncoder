package com.latynin.encoder.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.latynin.encoder.R;
import com.latynin.encoder.control.DBCodes;
import com.latynin.encoder.model.Code;
import com.latynin.encoder.model.User;

import java.util.concurrent.ExecutionException;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Класс адаптер, настраивающий список зашифрованных сообщений пользователя.
 * Испльзует класс {@link UsersCoderAdapter.ViewHolder}
 * Используется в {@link UsersCodeListFragment}
 */
public class UsersCoderAdapter extends RecyclerView.Adapter<UsersCoderAdapter.ViewHolder> {

    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private UsersCodeListFragment clf;

    public UsersCoderAdapter(UsersCodeListFragment clf) {
        this.clf = clf;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_code, parent, false);
        clipboardManager=(ClipboardManager) view.getContext().getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int x = position;
        holder.mName.setText(User.getInstance().getCodes().get(position).getName());
        holder.mCodeView.setText(User.getInstance().getCodes().get(position).getCode());
        holder.mCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipData = ClipData.newPlainText("code-"+x,User.getInstance().getCodes().get(x).getCode());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(v.getContext().getApplicationContext(), "Text coped ", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBCodes c = new DBCodes();
                c.execute("remove",User.getInstance().getCodes().get(x).getCode());
                try {
                    if(c.get()) {
                        User.getInstance().getCodes().remove(x);
                        clf.updateData();
                        Toast.makeText(v.getContext().getApplicationContext(), "Text deleted ", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Code c = User.getInstance().getCodes().get(position);
                FragmentManager fm = clf.getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.containerUsersCoders, CodeInfoFragment.newInstance(c.getName(),c.getCode(),c.getType(),c.getKey()),"INFO");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
       return User.getInstance().getCodes().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final Button mDelete;
        public final Button mCopy;
        public final Button mInfo;
        public final TextView mCodeView;
        public final TextView mName;

        public ViewHolder(View view) {
            super(view);
            mDelete = view.findViewById(R.id.btnDelete);
            mCopy = view.findViewById(R.id.btnCopy);
            mCodeView = view.findViewById(R.id.item_code);
            mInfo = view.findViewById(R.id.btnInfo);
            mName = view.findViewById(R.id.item_name);
        }

    }
}
