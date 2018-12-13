package com.latynin.encoder.control;

import android.os.AsyncTask;

import com.latynin.encoder.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс работающий с БД с помощью {@link JDBCUtil}
 * Осуществляет регистрацию пользователя {@link User}
 * а в последствии авторизацию {@link Login} (При успешной регистрации)
 * Работет асинхронно {@link AsyncTask}
 */
public class Registration extends AsyncTask<String,Void,Boolean> {

    public static boolean regUser(String login, String password){
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO users_en(login, password) VALUES(?,?);");
            ps.setString(1,login);
            ps.setString(2,password);
            ps.executeUpdate();
            Login.checkUser(login,password);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected Boolean doInBackground(String... param) {
        return regUser(param[0],param[1]);
    }
}
