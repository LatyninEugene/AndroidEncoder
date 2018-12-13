package com.latynin.encoder.control;

/**
 * Класс, реализующий {@link Coder}
 * Осуществляет кодирование и декодирование
 * с помощью битового сдвига.
 */
public class BitCoder implements Coder {

    @Override
    public String encode(char[] text, char[] key){
        if(key.length == 0){
            return new String(text);
        }
        int sum = 0;
        for (int i = 0; i < key.length; i++) {
            sum+=key[i];
        }
        for (int i = 0; i < text.length; i++) {
            int shift = key[key.length-(i%key.length)-1];
            shift = (shift*sum)%15;
            if(shift == 0){
                shift += 2;
            }
            text[i] = (char) (((text[i]>>(shift)) | (text[i] << (15 - shift)))&0b0111_1111_1111_1111);
            System.out.println(text[i]+" : "+Integer.toBinaryString(text[i]));
        }
        return new String(text);
    }
    @Override
    public String decode(char[] text, char[] key){
        if(key.length == 0){
            return new String(text);
        }
        int sum = 0;
        for (int i = 0; i < key.length; i++) {
            sum+=key[i];
        }
        for (int i = 0; i < text.length; i++) {
            int shift = key[key.length-(i%key.length)-1];
            shift = (shift*sum)%15;
            if(shift == 0){
                shift += 2;
            }
            text[i] = (char)(((text[i]>>(15-shift)) | (text[i]<<(shift)))&0b0111_1111_1111_1111);
            System.out.println(text[i]+" : "+Integer.toBinaryString(text[i]));
        }
        return new String(text);
    }
}
