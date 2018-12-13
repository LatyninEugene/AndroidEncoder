package com.latynin.encoder.control;

/**
 * Класс, реализующий {@link Coder}
 * Осуществляет кодирование и декодирование
 * с помощью сдвига.
 */
public class ShiftCoder implements Coder{

    @Override
    public String encode(char[] text, char[] key) {
        if (key.length <= 0)return new String(text);
        int sum = 0;
        for (int i = 0; i < key.length; i++) {
            sum+=key[i];
        }
        for (int i = 0; i < text.length; i++) {
            int shift = key[key.length-i%key.length-1];
            shift = (shift*sum)%50;
            text[i]+=shift;
        }
        return new String(text);
    }

    @Override
    public String decode(char[] text, char[] key) {
        if (key.length <= 0)return new String(text);
        int sum = 0;
        for (int i = 0; i < key.length; i++) {
            sum+=key[i];
        }
        for (int i = 0; i < text.length; i++) {
            int shift = key[key.length-i%key.length-1];
            shift = (shift*sum)%50;
            text[i]-=shift;
        }
        return new String(text);
    }
}
