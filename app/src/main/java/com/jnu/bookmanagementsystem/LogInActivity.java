package com.jnu.bookmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.jnu.bookmanagementsystem.data.DataSaver;

public class LogInActivity extends AppCompatActivity {
    public Button btn;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;
    private CheckBox checkbox5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btn = (Button) findViewById(R.id.btn_login);//绑定登录按钮
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(LogInActivity.this)
                        .setTitle("账号或密码错误")
                        .setMessage("请输入正确的账号和密码")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {//关闭对话框
                                dialogInterface.dismiss();
                                LogInActivity.this.finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });

        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
        //通过设置checkbox的监听事件来对checkbox是不是被选中
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //获得checkbox的文本内容
                    String text = checkbox2.getText().toString();
                    //显示消息
                    Toast.makeText(getApplicationContext(),text+"，该checkbox已经被你选中。",Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkbox3 = (CheckBox) findViewById(R.id.checkBox3);
        //通过设置checkbox的监听事件来对checkbox是不是被选中
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //获得checkbox的文本内容
                    String text = checkbox3.getText().toString();
                    //显示消息
                    Toast.makeText(getApplicationContext(),text+"，该checkbox已经被你选中。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkbox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkbox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //获得checkbox的文本内容
                    String text = checkbox5.getText().toString();
                    //显示消息
                    Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkbox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //获得checkbox的文本内容
                    String text = checkbox4.getText().toString();
                    //显示消息
                    Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}