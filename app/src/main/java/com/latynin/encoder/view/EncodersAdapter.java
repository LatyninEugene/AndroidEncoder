package com.latynin.encoder.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.latynin.encoder.R;
import com.latynin.encoder.control.BitCoder;
import com.latynin.encoder.control.Coder;
import com.latynin.encoder.control.ComboCoder;
import com.latynin.encoder.control.ShiftCoder;
import com.latynin.encoder.control.XORCoder;

import java.util.ArrayList;

/**
 * Класс адаптер, настраивающий список кодировок.
 * Испльзует класс {@link EncodersAdapter.ViewHolder}
 * Используется в {@link EncoderListFragment}
 */
public class EncodersAdapter extends RecyclerView.Adapter<EncodersAdapter.ViewHolder> {

    private ArrayList<Coder> coders;
    private EncoderListFragment encoderListFragment;
    public EncodersAdapter(EncoderListFragment encoderListFragment){
        coders = new ArrayList<>();
        coders.add(new BitCoder());
        coders.add(new ShiftCoder());
        coders.add(new XORCoder());
        coders.add(new ComboCoder());
        this.encoderListFragment = encoderListFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_encoder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String str[] = coders.get(position).getClass().getName().split("\\.");
        holder.mCoderView.setText(str[str.length-1]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentManager fm = encoderListFragment.getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                if(position == (getItemCount()-1)){
                    fragmentTransaction.replace(R.id.containerCoders, new ComboEncoderFragment(), "coder-" + str[str.length - 1]);
                }else {
                    fragmentTransaction.replace(R.id.containerCoders, new EncoderFragment(), "coder-" + str[str.length - 1]);
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return coders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView mCoderView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mCoderView = view.findViewById(R.id.content);
        }

    }
}
