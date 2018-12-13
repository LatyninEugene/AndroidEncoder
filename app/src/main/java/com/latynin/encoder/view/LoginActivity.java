package com.latynin.encoder.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.latynin.encoder.R;
import com.latynin.encoder.control.Login;
import com.latynin.encoder.control.Registration;

import java.util.concurrent.ExecutionException;

/**
 * Класс (Activity) отображает страницу авторизации пользователя.
 * Здесь происходит авторизация {@link Login},
 * либо регистрация {@link Registration} пользователя.
 */
public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView emailTV;
    EditText passTV;
    Button buttonReg;
    Button buttonLogin;

    @Override
    protected void onStop() {
        super.onStop();
        emailTV.setText("");
        passTV.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTV = findViewById(R.id.login);
        passTV = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.button_login);
        buttonReg = findViewById(R.id.button_reg);



        buttonReg.setOnClickListener(new View.OnClickListener() {
            private String login;
            private String pass;
            @Override
            public void onClick(View v) {
                login = emailTV.getText().toString();
                pass = passTV.getText().toString();
                Registration r = new Registration();
                r.execute(login,pass);
                try {
                    if(r.get()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            private String login;
            private String pass;
            @Override
            public void onClick(View v) {
                login = emailTV.getText().toString();
                pass = passTV.getText().toString();
                Login l = new Login();
                l.execute(login,pass);
                try {
                    if(l.get()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"User not found. ",Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}

