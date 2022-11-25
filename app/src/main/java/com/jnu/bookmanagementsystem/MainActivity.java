package com.jnu.bookmanagementsystem;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.acl.Group;
import java.util.ArrayList;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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
    private NavigationView navigationView ;
    private Spinner add_label_spinner;
    private ArrayAdapter<String> labelAdapter;
    private ArrayList<String> allLabels=new ArrayList<>();
    private PopupMenu popupMenu;
    private  ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
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
    private  ActivityResultLauncher<Intent> addLabelLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String new_label= bundle.getString("label");
                        if(new_label.contains("bookshelf")){
                            popupMenu.getMenu().add(Menu.NONE,Menu.NONE,0,new_label);
                            popupMenu.show();
                        }
                        Menu menu=navigationView.getMenu().getItem(2).getSubMenu();
                        int i=menu.size();
                        //给当前的标签菜单添加上新的标签
                        menu.add(R.id.nav_group1,menu.getItem(0).getItemId()+i,Menu.NONE,new_label).setIcon(R.drawable.icon_label);

                        //给label_spinner添加上新的标签
                        //add_label_spinner=findViewById(R.id.label_spinner);
                        //allLabels.add(new_label);
                        //labelAdapter = new ArrayAdapter<String>(this,
                                //android.R.layout.simple_spinner_dropdown_item,allLabels);
                        //labelAdapter.setDropDownViewResource(
                                //android.R.layout.simple_spinner_dropdown_item);
                        //add_label_spinner.setAdapter(labelAdapter);
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
            shopItems.add(new ShopItem("信息安全数学基础"," 聂旭云 著， "," 科学出版社 "," translator1 ","        2022-11-4 "," isbn1 ", R.drawable.book_1));
            shopItems.add(new ShopItem("软件项目管理案例教程"," 韩万江 著， "," 机械工业出版社 "," translator2 ","        2022-11-4 "," isbn2 ", R.drawable.book_2));
            dataSaver.Save(this,shopItems);
        }

        //2022/11/5添加悬浮按钮实现图书的添加
        Intent intent_add=new Intent(this, EditBookActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_item_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"add book clicked",Toast.LENGTH_SHORT).show();
                intent_add.putExtra("position",shopItems.size());
                addDataLauncher.launch(intent_add);
            }
        });

        //抽屉菜单的toolbar(成功实现)2022/11/7,点击对应toolBar打开抽屉菜单
        drawerLayout=findViewById(R.id.book_drawer_layout);
        Toolbar toolbar_openDrawer = findViewById(R.id.toolbar_openDrawer);
        toolbar_openDrawer.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));


        //在标题栏上用来搜索的toolBar,实现一个简单的搜索功能
        Toolbar toolbar_search = findViewById(R.id.toolbar_search);
        toolbar_search.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        //用来选择书架，添加新建书架
        Toolbar toolbar_bookshelf=findViewById(R.id.toolbar_all);
        toolbar_bookshelf.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        //点击扫描条形码的toolBar，实现扫描图书的isbn的功能
        Toolbar toolbar_scan=findViewById(R.id.toolbar_scan);
        toolbar_scan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });
        //2022/11/12 抽屉式菜单点击各个菜单项的响应函数
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //点击返回主书架界面（已经完成）
                    case R.id.item_books:
                        Toast.makeText(MainActivity.this,"Books clicked!Back to bookshelf!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                        //跳转到搜索图书信息的界面
                    case R.id.item_search:
                        Toast.makeText(MainActivity.this,"Search clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        break;
                        //某个标签
                    case R.id.item_law:
                        Toast.makeText(MainActivity.this,"Law clicked", Toast.LENGTH_SHORT).show();
                        break;
                        //创建标签
                    case R.id.item_create_label:
                        Toast.makeText(MainActivity.this,"Create new labels clicked", Toast.LENGTH_SHORT).show();
                        Intent intentAddLabel=new Intent(MainActivity.this, AddLabelActivity.class);
                        addLabelLauncher.launch(intentAddLabel);
                        break;
                        //设置,跳转到设置界面
                    case R.id.item_settings:
                        Toast.makeText(MainActivity.this,"Settings clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                        //about，弹出对话框
                    case R.id.item_about:
                        AlertDialog alertDialog;
                        alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("About")
                                .setMessage("BookShelf,Manage your books!")
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
                        break;
                        //分享
                    case R.id.item_share:
                        Toast.makeText(MainActivity.this,"Share clicked", Toast.LENGTH_SHORT).show();
                        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(MainActivity.this);
                        bottomSheetDialog.setTitle("Share with your friends");
                        bottomSheetDialog.setContentView(R.layout.bottom_dialog);
                        bottomSheetDialog.show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

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
                intentUpdate.putExtra("imageid",shopItems.get(item.getOrder()).getResourceId());
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
        this.getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_login:
                //Toast.makeText(this,"登陆", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LogInActivity.class));

                break;
            case R.id.menu_exit:
                Toast.makeText(this,"退出", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.all_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.all_create:
                        //创建新的书架(可以利用之前写好的添加标签的那个函数)
                        Intent intentAddLabel=new Intent(MainActivity.this, AddLabelActivity.class);
                        addLabelLauncher.launch(intentAddLabel);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                }
                return false;
            }
        });
        /*
        try { //popupmenu显示icon的关键
            Field mpopup=popupMenu.getClass().getDeclaredField("mPopup");
            mpopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mpopup.get(popupMenu);
            mPopup.setForceShowIcon(true);
        } catch (Exception e) {

        }*/
        popupMenu.show();
    }




}