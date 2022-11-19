package com.jnu.bookmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private Switch mswitch_wifi;
    private TextView wifi_text;
    private Switch mswitch_bt;
    private TextView bluetooth_text;
    private SeekBar seek_voice;
    private TextView voice_text;
    private SeekBar seek_light;
    private TextView light_text;
    private Spinner spin_ziti;
    private TextView ziti_text;
    private Spinner spin_language;
    private TextView language_text;
    private Spinner spin_mode;
    private TextView mode_text;
    private Button but_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //给wifi的设置添加监听
        mswitch_wifi = (Switch) findViewById(R.id.sw_wifi);
        wifi_text=(TextView)findViewById(R.id.set_wifi_text);
        mswitch_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    wifi_text.setText("开启");
                }else {
                    wifi_text.setText("关闭");
                }
            }
        });

        //给蓝牙的设置添加监听
        mswitch_bt=(Switch)findViewById(R.id.sw_bluetooth);
        bluetooth_text=(TextView)findViewById(R.id.set_bluetooth_text);
        mswitch_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bluetooth_text.setText("开启");
                }else {
                    bluetooth_text.setText("关闭");
                }
            }
        });
        //给声音的进度条添加监听
        seek_voice=(SeekBar) findViewById(R.id.seekbar_voice);
        voice_text=(TextView) findViewById(R.id.set_voice_text);
        seek_voice.setOnSeekBarChangeListener(this);
        seek_voice.setProgress(50);

        //给亮度的设置添加监听
        seek_light=(SeekBar) findViewById(R.id.seekbar_light);
        light_text=(TextView) findViewById(R.id.set_light_text);
        seek_light.setOnSeekBarChangeListener(this);
        seek_light.setProgress(50);

        //给字体的设置添加监听
        spin_ziti=(Spinner)findViewById(R.id.set_ziti);
        spin_ziti.setSelection(0);		//初始化，默认选择列表中第0个元素
        ziti_text=(TextView)findViewById(R.id.set_ziti_text) ;
        spin_ziti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                if (pos==0){
                    ziti_text.setText("当前的字体大小为：Small");
                }
                else if(pos==1){
                    ziti_text.setText("当前的字体大小为：Medium");
                }
                else{
                    ziti_text.setText("当前的字体大小为：Big");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //给语言的设置添加监听
        spin_language=(Spinner) findViewById(R.id.set_language);
        spin_language.setSelection(0);
        language_text=(TextView)findViewById(R.id.set_language_text) ;
        spin_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                if (pos==0){
                    language_text.setText("当前的语言种类为：Chinese");
                }
                else{
                    language_text.setText("当前的语言种类为：English");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //给模式的设置添加监听
        spin_mode=(Spinner) findViewById(R.id.set_mode);
        spin_mode.setSelection(0);
        mode_text=(TextView)findViewById(R.id.set_mode_text) ;
        spin_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                if (pos==0){
                    mode_text.setText("当前的模式为：Day");
                }
                else{
                    mode_text.setText("当前的模式为：Night");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //给恢复出厂设置的按钮添加监听
        but_reset=(Button) findViewById(R.id.button_reset);
        but_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Warning")
                        .setMessage("Are you sure to reset your app?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(SettingActivity.this,"已放弃恢复出厂设置！", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(SettingActivity.this,"已恢复出厂设置！", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar==seek_voice){
            String desc = "当前声音为:"+seekBar.getProgress()+"，最大声音为:"+seekBar.getMax();
            voice_text.setText(desc);
        }else{
            String desc = "当前亮度为:"+seekBar.getProgress()+"，最大亮度为:"+seekBar.getMax();
            light_text.setText(desc);
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}