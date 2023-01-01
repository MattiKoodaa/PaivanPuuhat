package com.example.sptest;


import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onNotifyClick(String notify);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mAddNotify;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;
        public RelativeLayout mRelativeLayout;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.tVLine1);
            mTextView2 = itemView.findViewById(R.id.tVLine2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mAddNotify = itemView.findViewById(R.id.image_notify);
            mRelativeLayout =itemView.findViewById(R.id.relativeLayout);


            mAddNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notify = mTextView1.getText().toString();
                    listener.onNotifyClick(notify);
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        holder.mTextView1.setText(currentItem.getLine1());
        holder.mTextView2.setText(currentItem.getLine2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}