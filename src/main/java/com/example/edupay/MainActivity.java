package com.example.edupay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edupay.model.MonHoc;
import com.example.edupay.model.User;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private TextView tvMSSV,tvTenSV,tvLopHoc,tvNganhHoc;
    private Button btn_payment;
    private User myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMSSV = findViewById(R.id.tvMSSV);
        tvTenSV = findViewById(R.id.tvTenSV);
        tvLopHoc = findViewById(R.id.tvLopHoc);
        tvNganhHoc = findViewById(R.id.tvNganhHoc);
        btn_payment = findViewById(R.id.btn_thanhToan);

        //Get dữ liệu từ bundle
        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null){
            User user = (User) bundleReceive.get("object_user");
            myUser = user;
            if (user != null){
                tvMSSV.setText(user.getMaSinhVien());
                tvTenSV.setText(user.getTenSinhVien());
                tvLopHoc.setText(user.getLopHoc());
                tvNganhHoc.setText(user.getNganhHoc());
            }
        }
        //Xử lý sang trang thanh toán
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_user1", myUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}