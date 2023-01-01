package com.example.sptest;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int oldValue=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        buildRecyclerView();
        setInsertButton();
        setClearButton();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mExampleList);
        editor.putString("task list", json);
        editor.putString("old value", String.valueOf(oldValue));
        editor.apply();
    }

    private void loadData() {
        final TextView changingText = (TextView) findViewById(R.id.nTehdyt);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        String oV = sharedPreferences.getString("old value", null);
        if (oV!=null){
        oldValue=Integer.parseInt(oV);
        }else{
            oldValue=0;
            oV="0";
        }
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);

        if (mExampleList == null) {
            mExampleList = new ArrayList<>();
        }

        changingText.setText(oV);

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onNotifyClick(String notify) {
                openActivity2(notify);
                saveData();
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);

                changeText();

                saveData();
            }
        });}

    private void changeText() {
        final TextView changingText = (TextView) findViewById(R.id.nTehdyt);
        oldValue=oldValue+1;
        String s = Integer.toString(oldValue);
        changingText.setText(s);
    }


    public void openActivity2(String notify) {
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("keynotify",notify);
        startActivity(intent);
        finish();
    }

    private void setInsertButton() {
        Button buttonInsert = findViewById(R.id.insertBtn);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText line1 = findViewById(R.id.eTline1);
                EditText line2 = findViewById(R.id.eTline2);
                insertItem(line1.getText().toString(), line2.getText().toString());
                line1.getText().clear();
                line2.getText().clear();
                saveData();
            }
        });
    }

    private void setClearButton() {
        Button buttonClear = findViewById(R.id.clearBtn);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView changingText = (TextView) findViewById(R.id.nTehdyt);
                oldValue = 0;
                String s = Integer.toString(oldValue);
                changingText.setText(s);
                saveData();
            }
        });
    }

    private void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    private void insertItem(String line1, String line2) {
        if (Objects.equals(line1, "")) {
            Toast.makeText(this, "Ethän jätä tehtävää tyhjäksi!", Toast.LENGTH_SHORT).show();
            return;
        }
        mExampleList.add(new ExampleItem(line1, line2));
        mAdapter.notifyItemInserted(mExampleList.size());
    }
}