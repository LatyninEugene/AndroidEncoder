package com.latynin.encoder.control;

import java.util.ArrayList;

/**
 * Класс, реализующий {@link Coder}
 * Осуществляет последоватьленое кодирование и декодирование
 * используя кодировщики из списка coders.
 */
public class ComboCoder implements Coder {

    public  ArrayList<Coder> coders = new ArrayList<>();

    @Override
    public String encode(char[] text, char[] key) {
        for (Coder c : coders) {
            text = c.encode(text,key).toCharArray();
        }
        return new String(text);
    }

    @Override
    public String decode(char[] text, char[] key) {
        for (int i = 0; i < coders.size(); i++) {
            text = coders.get(coders.size()-i-1).decode(text,key).toCharArray();
        }
        return new String(text);
    }
}
