package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListView extends RecyclerView.ViewHolder {
    public TextView header;
    public TextView text;
    public ListView(@NonNull View itemView) {
        super(itemView);
        header = itemView.findViewById(R.id.header);
        text = itemView.findViewById(R.id.text);
    }
}
