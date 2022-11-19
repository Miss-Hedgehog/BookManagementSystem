package com.jnu.bookmanagementsystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

public class CaptureActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN_GALLERY = 0;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        initScan();
    }
    public void initScan() {
        ScanOptions scanOptions=new ScanOptions();
        scanOptions.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
        scanOptions.setCaptureActivity(CaptureActivity.class); //设置打开摄像头的Activity
        scanOptions.setPrompt("请对准二维码"); //底部的提示文字，设为""可以置空
        scanOptions.setCameraId(1); //前置或者后置摄像头
        scanOptions.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
        scanOptions.setBarcodeImageEnabled(true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 4937) {
            ScanIntentResult scanResult= ScanIntentResult.parseActivityResult(resultCode,intent);
            if (scanResult != null && scanResult.getContents() != null) {
                String result = scanResult.getContents();
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(CaptureActivity.this)
                        .setTitle("Scan Result")
                        .setMessage(result)
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                alertDialog.show();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}