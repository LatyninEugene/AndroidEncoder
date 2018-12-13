package com.latynin.encoder.model;

/**
 * Класс служит для хранения информации о зашифрованном сообщении.
 */
public class Code {

    private String code;
    private String type;
    private String name;
    private String key;

    public Code(String name,String code, String key, String type) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
