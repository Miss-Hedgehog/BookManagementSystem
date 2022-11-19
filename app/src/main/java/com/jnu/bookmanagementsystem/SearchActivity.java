package com.jnu.bookmanagementsystem;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jnu.bookmanagementsystem.data.DataSaver;
import com.jnu.bookmanagementsystem.data.ShopItem;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private String[] mStrings = new String[]{""," "," "};
    private ArrayList<ShopItem> searchItems;

    private ActivityResultLauncher<Intent> searchResultDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {

                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView msearchView = findViewById(R.id.searchView);
        final ListView mlistView = findViewById(R.id.listView);
        //在listView中加载显示已经存在的书籍的title
        DataSaver dataSaver = new DataSaver();
        searchItems = dataSaver.Load(this);
        for (int i = 0; i < searchItems.size(); i++) {
            mStrings[i] = searchItems.get(i).getTitle();
        }
        mlistView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStrings));
        mlistView.setTextFilterEnabled(true);

        msearchView.setIconifiedByDefault(false);
        msearchView.setSubmitButtonEnabled(true);
        msearchView.setQueryHint("请输入所查找的书名");
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //输入完信息，点击搜索键后
                boolean flag = false;
                int position;
                for (position = 0; position < searchItems.size(); position++) {
                    if (searchItems.get(position).getTitle() == s) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    //当搜索的内容不存在时,添加图书信息，还需完善
                    Toast.makeText(SearchActivity.this, "您输入的书名是：" + s + ",该书不存在! ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SearchActivity.this, EditBookActivity.class));
                } else {
                    //当搜索的图书存在的时候，跳转到显示图书信息的界面
                    Toast.makeText(SearchActivity.this, "您输入的书名是：" + s + ",即将为您展示该书的详细信息! ", Toast.LENGTH_SHORT).show();
                    Intent search_result_intent = new Intent(SearchActivity.this, EditBookActivity.class);
                    search_result_intent.putExtra("position", position);
                    search_result_intent.putExtra("title", searchItems.get(position).getTitle());
                    search_result_intent.putExtra("author", searchItems.get(position).getAuthor());
                    search_result_intent.putExtra("publisher", searchItems.get(position).getPublisher());
                    search_result_intent.putExtra("translator", searchItems.get(position).getTranslator());
                    search_result_intent.putExtra("pubdate", searchItems.get(position).getPubDate());
                    search_result_intent.putExtra("isbn", searchItems.get(position).getIsbn());
                    search_result_intent.putExtra("imageid", searchItems.get(position).getResourceId());
                    searchResultDataLauncher.launch(search_result_intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    mlistView.clearTextFilter();
                } else {
                    mlistView.setFilterText(s);
                }
                return false;
            }
        });
        //给listView中的每一个item设置监听，若点击已经存在的item选项也会跳转到显示图书信息的界面
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchActivity.this, "Click item：" + i, Toast.LENGTH_SHORT).show();
                Intent search_result_intent = new Intent(SearchActivity.this, EditBookActivity.class);
                search_result_intent.putExtra("position", i);
                search_result_intent.putExtra("title", searchItems.get(i).getTitle());
                search_result_intent.putExtra("author", searchItems.get(i).getAuthor());
                search_result_intent.putExtra("publisher", searchItems.get(i).getPublisher());
                search_result_intent.putExtra("translator", searchItems.get(i).getTranslator());
                search_result_intent.putExtra("pubdate", searchItems.get(i).getPubDate());
                search_result_intent.putExtra("isbn", searchItems.get(i).getIsbn());
                search_result_intent.putExtra("imageid", searchItems.get(i).getResourceId());
                searchResultDataLauncher.launch(search_result_intent);
            }
        });


    }
}