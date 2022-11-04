package com.jnu.bookmanagementsystem;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class EditBookActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 666;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        EditText editTextTitle=findViewById(R.id.edittext_shop_item_title);
        EditText editTextPrice=findViewById(R.id.edittext_shop_item_price);
        Button buttonSure=findViewById(R.id.button_ok);
        Button buttonCancel=findViewById(R.id.button_no);
        String title=this.getIntent().getStringExtra("title");
        Double price=this.getIntent().getDoubleExtra("price",0);
        if(null!=title)
        {
            editTextTitle.setText(title);
            editTextPrice.setText(price.toString());
        }
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                double price=Double.parseDouble( editTextPrice.getText().toString());
                bundle.putDouble("price",price);
                int position=getIntent().getIntExtra("position",0);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                EditBookActivity.this.finish();
            }
        });
    }
}
