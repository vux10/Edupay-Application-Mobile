package com.example.edupay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edupay.api.ApiService;
import com.example.edupay.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_tenDangNhap, edt_matKhau;
    private Button btnLogin, btnKhach;
    private List<User> mListUser;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_tenDangNhap = findViewById(R.id.edt_tenDangNhap);
        edt_matKhau = findViewById(R.id.edt_matKhau);
        btnLogin = findViewById(R.id.btn_login);
        btnKhach = findViewById(R.id.btn_khach);
        mListUser = new ArrayList<>();
        //Call API
        getListUsers();
        //Sự kiện nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();
            }
        });
        btnKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void clickLogin() {
        String strTenDangNhap = edt_tenDangNhap.getText().toString().trim();
        String strMatKhau = edt_matKhau.getText().toString().trim();
        //Xử lý đăng nhập
        if (mListUser == null || mListUser.isEmpty()){
            return;
        }
        boolean isHasUser = false;
        for(User user : mListUser){
            if (strTenDangNhap.equals(user.getTenDangNhap()) && strMatKhau.equals(user.getMatKhau())){
                isHasUser = true;
                mUser = user;
                break;
            }
        }
        //Nếu tên đăng nhập và mật khẩu đúng chuyển sang MainActivity
        if (isHasUser){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_user", mUser);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Tên đăng nhập hoặc Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListUsers(){
        ApiService.apiService.getListUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               mListUser = response.body();
                Log.e("List user", mListUser.size() + "");


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call API error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}