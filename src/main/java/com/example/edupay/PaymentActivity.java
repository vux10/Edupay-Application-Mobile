package com.example.edupay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edupay.model.CreateOrder;
import com.example.edupay.model.MonHoc;
import com.example.edupay.model.User;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
     private TableLayout tableLayout;
     private Button btnzalopay;
    private TextView txtToken;
    private int totalAmount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tableLayout = findViewById(R.id.tableLayout);
        btnzalopay = findViewById(R.id.btn_zalopay);
        txtToken = findViewById(R.id.txtToken);
        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null){
            User user = (User) bundleReceive.get("object_user1");
            if (user != null){
                //Xử lý hiển thị môn học và tính tiền
                displayMonHoc(user);
            }
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Khởi tạo ZaloPay SDK
        ZaloPaySDK.init(553, Environment.SANDBOX);


        btnzalopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(String.valueOf(totalAmount));
                    String code = data.getString("returncode");
                    Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                    if (code.equals("1")) {
                        String token = data.getString("zptranstoken");
                        ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                txtToken.setText("Bạn đã thanh toán thành công");
                                btnzalopay.setVisibility(View.INVISIBLE);

                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                txtToken.setText("Tạm dừng thanh toán");
                                btnzalopay.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                txtToken.setText("Thanh toán không thành công");
                                btnzalopay.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayMonHoc(User user){

        for (MonHoc monHoc : user.getMonHoc()) {
            TableRow tableRow = new TableRow(this);

            TextView textViewMaMonHoc = new TextView(this);
            textViewMaMonHoc.setText(monHoc.getMaMonHoc());
            textViewMaMonHoc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewMaMonHoc.setTextSize(18);
            textViewMaMonHoc.setPadding(10,10,10,10);

            TextView textViewTenMonHoc = new TextView(this);
            textViewTenMonHoc.setText(monHoc.getTenMonHoc());
            textViewTenMonHoc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewTenMonHoc.setTextSize(18);
            textViewTenMonHoc.setPadding(10,10,10,10);

            TextView textViewSoTinChi = new TextView(this);
            textViewSoTinChi.setText(String.valueOf(monHoc.getSoTinChi()));
            textViewSoTinChi.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewSoTinChi.setTextSize(18);
            textViewSoTinChi.setPadding(10,10,10,10);

            TextView textViewSoTien = new TextView(this);
            textViewSoTien.setText(String.valueOf(monHoc.getSoTien()));
            textViewSoTien.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewSoTien.setTextSize(18);
            textViewSoTien.setPadding(10,10,10,10);

            tableRow.addView(textViewMaMonHoc);
            tableRow.addView(textViewTenMonHoc);
            tableRow.addView(textViewSoTinChi);
            tableRow.addView(textViewSoTien);

            tableLayout.addView(tableRow);
            totalAmount += monHoc.getSoTien(); // Tính tổng số tiền
        }

        // Thêm hàng tổng số tiền
        TableRow totalRow = new TableRow(this);
        TextView totalLabel = new TextView(this);
        totalLabel.setText("Tổng số tiền: ");
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 3; // Span qua 3 cột
        totalLabel.setLayoutParams(params);
        totalLabel.setTextSize(24);
        totalLabel.setPadding(10,30,10,10);
        totalLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalLabel.setTypeface(null, Typeface.BOLD);


        TextView totalValue = new TextView(this);
        totalValue.setText(String.valueOf(totalAmount));
        totalValue.setTextSize(24);
        totalValue.setPadding(10,30,10,10);
        totalValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalValue.setTextColor(Color.RED);
        totalValue.setTypeface(null, Typeface.BOLD);

        totalRow.addView(totalLabel);
        totalRow.addView(totalValue);

        tableLayout.addView(totalRow);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}