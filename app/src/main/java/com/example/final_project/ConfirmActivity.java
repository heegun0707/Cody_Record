package com.example.final_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_login;
    TextView tv_forgot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        // 로딩페이지로 이동
        Intent intent = new Intent(this, LoadingPageActivity.class);
        startActivity(intent);

        btn_login = findViewById(R.id.btn_login);
        et_login = findViewById(R.id.et_login);
        tv_forgot = findViewById(R.id.tv_forgot);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = et_login.getText().toString();
                if(pass.equals("heegun")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = View.inflate(getApplicationContext(), R.layout.dialog_login, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ConfirmActivity.this);
                dlg.setTitle("비밀번호를 잊어버리셨습니까?");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", null );/*new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pass = et_login.getText().toString();
                        Toast.makeText(getApplicationContext(), "패스워드는" + pass + "입니다.", Toast.LENGTH_SHORT).show();
                    }
                });*/
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

    }
}
