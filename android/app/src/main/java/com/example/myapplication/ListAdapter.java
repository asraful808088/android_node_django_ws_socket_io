package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListView> {
    private ArrayList<CryptoData> list;
    private Context context;
    public ListAdapter(ArrayList<CryptoData> list, Context context){
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_card,parent,false);
        ListView listView =new ListView(view);
        return listView;
    }

    @Override
    public void onBindViewHolder(@NonNull ListView holder, int position) {
        holder.header.setText(list.get(position).header);
        holder.text.setText(list.get(position).text);
    }
    public void dataInject(ArrayList<CryptoData> list){
         this.list = list;
         notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
