package com.example.lulu.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lulu on 2016/11/24.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    private OnChildClickListener listener;
    private RecyclerView recyclerView;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    //相当于convertView为空的使用调用一次
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1. 载入一个View
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);
        //2. 创建一个ViewHolder返回就可以了
        return new MyViewHolder(view);
    }

    // 当被连接到一个RecyclerView上
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    // 断开连接
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    //不管是新创建的还是后来的都会调用
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //对itemView的绑定
        holder.textView.setText(list.get(position));
    }

    //返回RecyclerView中
    @Override
    public int getItemCount() {
        return list.size();
    }

    // -----------------------
    // View的点击事件
    @Override
    public void onClick(View v) {
        if (recyclerView != null && listener != null) {
            int position = recyclerView.getChildAdapterPosition(v);
            if (position >= 0) {//此处判断position
                listener.onChildClick(recyclerView, v, position, list.get(position));
            }
        }
    }

    // 指定位置删除
    public void remove(int position) {
        list.remove(position);
        //notifyDataSetChanged();//不要使用这种方式
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.item_text));
        }
    }

    public interface OnChildClickListener {
        void onChildClick(RecyclerView parent, View view, int position, String data);
    }
}
