package com.latynin.encoder.control;

import android.os.AsyncTask;

import com.latynin.encoder.model.Code;
import com.latynin.encoder.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс работающий с БД с помощью {@link JDBCUtil}
 * Осуществляет добавление и удаления зашиврованого пользователем сообщения,
 * а так же получения списка этих сообщений.
 * Работет асинхронно {@link AsyncTask}
 */
public class DBCodes extends AsyncTask<String,Void,Boolean>{

    public static ArrayList<Code> getCode(int id_user){
        ArrayList<Code> codes = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users_code WHERE id_user = ?;");
            ps.setInt(1, id_user);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                codes.add(new Code(resultSet.getString(4),
                        resultSet.getString(3),
                        resultSet.getString(6),
                        resultSet.getString(5)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("CODE :"+ e.getLocalizedMessage());
        }
        return codes;
    }
    @Override
    protected Boolean doInBackground(String... command) {
        if(command[0].equals("add")){
            try (Connection con = JDBCUtil.getConnection()){
                PreparedStatement ps = con.prepareStatement("INSERT INTO users_code(id_user, code, code_name, code_type, code_key) VALUES(?,?,?,?,?);");
                ps.setInt(1, User.getInstance().getId());
                ps.setString(2,command[1]);
                ps.setString(3,command[2]);
                ps.setString(4,command[3]);
                ps.setString(5,command[4]);
                ps.executeUpdate();
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }else if (command[0].equals("remove")){
            try (Connection con = JDBCUtil.getConnection()){
                PreparedStatement ps = con.prepareStatement("DELETE from users_code where id_user = ? and code = ?;");
                ps.setInt(1,User.getInstance().getId());
                ps.setString(2,command[1]);
                ps.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                return false;
            }
            return true;
        }
        return false;
    }
}
