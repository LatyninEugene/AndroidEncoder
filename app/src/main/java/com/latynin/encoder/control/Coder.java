package com.latynin.encoder.control;

/**
 *
 */
public interface Coder {

    String encode(char[] text, char[] key);

    String decode(char[] text, char[] key);
}
