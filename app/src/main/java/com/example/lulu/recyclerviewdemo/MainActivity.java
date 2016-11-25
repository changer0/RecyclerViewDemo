package com.example.lulu.recyclerviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnChildClickListener {

    private static final String TAG = "MainActivity";
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
//            list.add(String.format(Locale.CHINA, "第%03d条数据%s", i, i % 2 == 0 ? "" : "-------------------------"));
            list.add(String.format(Locale.CHINA, "第%03d条数据", i));
        }
        adapter = new MyAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {//第0个Item占满整行(3列)
                    return 3;
                } else if (position == 3) {//第3个Item占满2列
                    return 2;
                }
                return 1;
            }
        });
        //瀑布流
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recycler.setLayoutManager(linearLayoutManager);
        // RecyclerView的动画系统
        //DefaultItemAnimator animator = new DefaultItemAnimator();
        //animator.setRemoveDuration(3000);
        MyItemAnimator animator = new MyItemAnimator();
        recycler.setItemAnimator(animator);
        adapter.setOnChildClickListener(this);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            //所有控件绘制之前调用的绘制方法, 会更加的灵活
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawColor(Color.GREEN);
            }

            //前景绘制, 就是在所有的控件之上绘制
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                //不要绘制方法中写该方法 , 此处只是为了做测试
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                c.drawBitmap(bitmap, 400, 400, null);

            }

            // 行间距
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.set(0, 5 * position, 0, 5 * position);
            }
        });

    }


    @Override
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        //adapter.remove(position);
        adapter.add(position, "新增数据" + position);
    }
}
