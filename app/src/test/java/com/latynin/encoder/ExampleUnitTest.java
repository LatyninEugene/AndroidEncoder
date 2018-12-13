package com.latynin.encoder;

import com.latynin.encoder.control.BitCoder;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void bit_isCorrect(){
        System.out.println(new BitCoder().encode("My wonderful word".toCharArray(), "Secret key".toCharArray()));
    }

    @Test
    public void bit1_isCorrect(){
        System.out.println(new BitCoder().encode("ꀉ\u200F\u0004\uE00E\uE00D쀍而ꀌ䀎쀌ꀎ耍\u0004\uE00E\uE00D䀎而\n".toCharArray(), "Secret key".toCharArray()));
    }
}