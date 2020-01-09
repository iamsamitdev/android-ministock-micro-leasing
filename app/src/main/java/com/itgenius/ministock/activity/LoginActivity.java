package com.itgenius.ministock.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.itgenius.ministock.R;
import com.itgenius.ministock.api.RestAPI;
import com.itgenius.ministock.api.RetrofitServer;
import com.itgenius.ministock.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // สร้างตัวแปรแบบ SharedPreference
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // เช็คว่าผู้ใช้มีการล็อกอินแล้วหรือยัง
        pref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
        if(pref.contains("pref_userid")){
            // ส่งไปหน้า MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        // FindView
        final TextInputEditText username = findViewById(R.id.username);
        final TextInputEditText password = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Event Click Button Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // เรียกใช้งาน API Login ที่สร้างไว้ในไฟล์ RestAPI.java
                RestAPI api = RetrofitServer.getClient().create(RestAPI.class);
                Call<LoginResponse> checkLogin = api.checkLogin(
                        username.getText().toString(),
                        password.getText().toString()
                );

                checkLogin.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body().getStatus().equals("success")){

                            // Login Success
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();

                            // เก็บข้อมูลการ Login ลงตัวแปร SharedPreferences
                            pref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("pref_userid", response.body().getUserid());
                            editor.apply();

                            // ส่งไปหน้า MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(getApplicationContext(),"Login fail!!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"ติดต่อ API ไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                    }
                });

                // เช็ค Username and Password
                /*
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("1234")){
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Login fail!!!",Toast.LENGTH_SHORT).show();
                }
                 */

            }
        });


    }
}
