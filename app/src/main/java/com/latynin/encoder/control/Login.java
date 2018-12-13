package com.latynin.encoder.control;

import android.os.AsyncTask;

import com.latynin.encoder.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс работающий с БД с помощью {@link JDBCUtil}
 * Осуществляет авторизацию пользователя {@link User}
 * Работет асинхронно {@link AsyncTask}
 */
public class Login extends AsyncTask<String,Void,Boolean>{

    public static boolean checkUser(String login, String password) {

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users_en WHERE login = ? AND password = ?;");
            ps.setString(1,login);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                User.getInstance().setId(resultSet.getInt(1));
                User.getInstance().setLogin(resultSet.getString(2));
                User.getInstance().setCodes(DBCodes.getCode(User.getInstance().getId()));
                return true;
            }
        } catch (SQLException e) {
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected Boolean doInBackground(String... param) {
        return checkUser(param[0],param[1]);
    }
}
