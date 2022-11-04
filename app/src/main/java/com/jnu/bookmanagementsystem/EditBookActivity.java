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
        EditText editTextAuthor=findViewById(R.id.edittext_shop_item_author);
        EditText editTextPublisher=findViewById(R.id.edittext_shop_item_publisher);
        EditText editTextTranslator=findViewById(R.id.edittext_shop_item_translator);
        EditText editTextPubDate=findViewById(R.id.edittext_shop_item_pub_date);
        EditText editTextIsbn=findViewById(R.id.edittext_shop_item_isbn);

        Button buttonSure=findViewById(R.id.button_ok);
        Button buttonCancel=findViewById(R.id.button_no);
        String title=this.getIntent().getStringExtra("title");
        String author=this.getIntent().getStringExtra("author");
        String publisher=this.getIntent().getStringExtra("publisher");
        String translator=this.getIntent().getStringExtra("translator");
        String pubdate=this.getIntent().getStringExtra("pubdate");
        String isbn=this.getIntent().getStringExtra("isbn");

        if(null!=title)
        {
            editTextTitle.setText(title);
            editTextAuthor.setText(author);
            editTextPublisher.setText(publisher);
            editTextTranslator.setText(translator);
            editTextPubDate.setText(pubdate);
            editTextIsbn.setText(isbn);
        }

        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("author",editTextAuthor.getText().toString());
                bundle.putString("publisher",editTextPublisher.getText().toString());
                bundle.putString("translator",editTextTranslator.getText().toString());
                bundle.putString("pubdate",editTextPubDate.getText().toString());
                bundle.putString("isbn",editTextIsbn.getText().toString());
                int position=getIntent().getIntExtra("position",0);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                EditBookActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookActivity.this.finish();
            }
        });
    }
}
