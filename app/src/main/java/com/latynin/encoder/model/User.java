package com.latynin.encoder.model;

import java.util.ArrayList;

/**
 * Класс (Singleton) служит для хранения информации
 * о пользователи, который в данный момент авторизован.
 */
public class User {

    private static User user;
    private int id;
    private String login;
    private ArrayList<Code> codes;

    private User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Code> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<Code> codes) {
        this.codes = codes;
    }

    public static User getInstance(){
        if(user == null){
            user = new User();
        }
        return user;
    }
}
