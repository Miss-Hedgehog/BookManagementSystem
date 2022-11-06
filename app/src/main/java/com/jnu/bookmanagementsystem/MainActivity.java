package com.jnu.bookmanagementsystem;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.bookmanagementsystem.data.ShopItem;
import com.jnu.bookmanagementsystem.data.DataSaver;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<ShopItem> shopItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    private DrawerLayout drawerLayout;//滑动菜单

    private ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        String author=bundle.getString("author");
                        String translator=bundle.getString("translator");
                        String publisher=bundle.getString("publisher");
                        String pubdate=bundle.getString("pubdate");
                        String isbn=bundle.getString("isbn");
                        int position=bundle.getInt("position");
                        shopItems.add(position, new ShopItem(title,author,translator,publisher,pubdate,isbn, R.drawable.book3) );
                        new DataSaver().Save(this,shopItems);
                        mainRecycleViewAdapter.notifyItemInserted(position);
                    }
                }
            });
    private ActivityResultLauncher<Intent> updateDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        String author=bundle.getString("author");
                        String translator=bundle.getString("translator");
                        String publisher=bundle.getString("publisher");
                        String pubdate=bundle.getString("pubdate");
                        String isbn=bundle.getString("isbn");
                        int position=bundle.getInt("position");
                        shopItems.get(position).setTitle(title);
                        shopItems.get(position).setAuthor(author);
                        shopItems.get(position).setTranslator(translator);
                        shopItems.get(position).setPublisher(publisher);
                        shopItems.get(position).setPubDate(pubdate);
                        shopItems.get(position).setIsbn(isbn);
                        new DataSaver().Save(this,shopItems);
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_main);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        DataSaver dataSaver=new DataSaver();
        shopItems=dataSaver.Load(this);
        if(shopItems.size()==0) {
            shopItems.add(new ShopItem(" 《信息安全数学基础》 "," 聂旭云 著， "," 科学出版社 "," translator1 ","        2022-11-4 "," isbn1 ", R.drawable.book_1));
            shopItems.add(new ShopItem(" 《软件项目管理案例教程》 "," 韩万江 著， "," 机械工业出版社 "," translator2 ","        2022-11-4 "," isbn2 ", R.drawable.book_2));
        }

        //2022/11/5添加悬浮按钮实现图书的添加
        Intent intent=new Intent(this, EditBookActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_item_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"add book clicked",Toast.LENGTH_SHORT).show();
                intent.putExtra("position",shopItems.size());
                addDataLauncher.launch(intent);
            }
        });

        /*
        //2022/11/6工具栏按钮点击显示左侧抽屉式菜单(有点问题)
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));*/

        mainRecycleViewAdapter= new MainRecycleViewAdapter(shopItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                Intent intent=new Intent(this, EditBookActivity.class);
                intent.putExtra("position",item.getOrder());
                addDataLauncher.launch(intent);
                break;
            case MENU_ID_UPDATE:
                Intent intentUpdate=new Intent(this, EditBookActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",shopItems.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("author",shopItems.get(item.getOrder()).getAuthor());
                intentUpdate.putExtra("translator",shopItems.get(item.getOrder()).getTranslator());
                intentUpdate.putExtra("publisher",shopItems.get(item.getOrder()).getPublisher());
                intentUpdate.putExtra("pubdate",shopItems.get(item.getOrder()).getPubDate());
                intentUpdate.putExtra("isbn",shopItems.get(item.getOrder()).getIsbn());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.ask_if_to_delete)
                        .setPositiveButton(R.string.yes_to_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shopItems.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this,shopItems);
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.not_to_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private ArrayList<ShopItem> localDataSet;
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewAuthor;
            private final TextView textViewTranslator;
            private final TextView textViewPublisher;
            private final TextView textViewPubDate;
            private final TextView textViewIsbn;
            private final ImageView imageViewImage;

            public ViewHolder(View view) {
                super(view);
                imageViewImage = view.findViewById(R.id.imageview_item_image);
                textViewTitle = view.findViewById(R.id.textView_item_title);
                textViewAuthor = view.findViewById(R.id.textView_item_author);
                textViewTranslator = view.findViewById(R.id.textView_item_translator);
                textViewPublisher = view.findViewById(R.id.textView_item_publisher);
                textViewPubDate = view.findViewById(R.id.textView_item_pubDate);
                textViewIsbn = view.findViewById(R.id.textView_item_isbn);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public TextView getTextViewAuthor() {
                return textViewAuthor;
            }

            public TextView getTextViewTranslator() {
                return textViewTranslator;
            }

            public TextView getTextViewPublisher() {
                return textViewPublisher;
            }

            public TextView getTextViewPubDate() {
                return textViewPubDate;
            }

            public TextView getTextViewIsbn() {
                return textViewIsbn;
            }
            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0, MENU_ID_ADD, getAdapterPosition(), "Add book here");
                contextMenu.add(0, MENU_ID_UPDATE, getAdapterPosition(), "Update this book");
                contextMenu.add(0, MENU_ID_DELETE, getAdapterPosition(), "Delete this book");
                //contextMenu.add(0, MENU_ID_DELETE, getAdapterPosition(), "Delete this book. " + getAdapterPosition());
            }
        }
        public MainRecycleViewAdapter(ArrayList<ShopItem> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main, viewGroup, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextViewAuthor().setText(localDataSet.get(position).getAuthor());
            viewHolder.getTextViewPublisher().setText(localDataSet.get(position).getPublisher());
            viewHolder.getTextViewPubDate().setText(localDataSet.get(position).getPubDate());
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());
            //viewHolder.getTextViewTranslator().setText(localDataSet.get(position).getTranslator());
            //viewHolder.getTextViewIsbn().setText(localDataSet.get(position).getPubDate());
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    //2022/11/6选项菜单option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_login:
                Toast.makeText(this,"登陆", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
                Toast.makeText(this,"退出", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }






}