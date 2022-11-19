package com.jnu.bookmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddLabelActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 666;
    public static final int  RESULT_CODE_UNSUCCESS= 555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_label);
        EditText editTextNewLabel=findViewById(R.id.text_add_new_label);
        Button buttonSure=findViewById(R.id.button_add_ok);
        Button buttonBack=findViewById(R.id.button_add_no);

        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("label",editTextNewLabel.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                AddLabelActivity.this.finish();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(RESULT_CODE_UNSUCCESS,intent);
                AddLabelActivity.this.finish();
            }
        });

    }
}