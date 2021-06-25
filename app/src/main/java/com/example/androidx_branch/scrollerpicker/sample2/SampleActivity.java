package com.example.androidx_branch.scrollerpicker.sample2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidx_branch.R;
import com.example.androidx_branch.scrollerpicker.library.adapter.ScrollPickerAdapter;
import com.example.androidx_branch.scrollerpicker.library.view.ScrollPickerView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
    private ScrollPickerView mScrollPickerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);
        initView();
        initData();
    }


    private void initView() {
        mScrollPickerView = findViewById(R.id.scroll_picker_view);
        mScrollPickerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        List<MyData> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MyData myData = new MyData();
            myData.id = i;
            myData.text = "我是第" + i + "个item";
            list.add(myData);
        }

        ScrollPickerAdapter.ScrollPickerAdapterBuilder<MyData> builder =
                new ScrollPickerAdapter.ScrollPickerAdapterBuilder<MyData>(this)
                        .setDataList(list)
                        .selectedItemOffset(2)
                        .visibleItemNumber(5)
                        .setDivideLineColor("#ED5275")
                        .setItemViewProvider(new MyViewProvider())
                        .setOnClickListener(new ScrollPickerAdapter.OnClickListener() {
                            @Override
                            public void onSelectedItemClicked(View v) {
                                MyData myData = (MyData) v.getTag();
                                if (myData != null) {
                                    Toast.makeText(SampleActivity.this, "id: " + myData.id + " text:" + myData.text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        ScrollPickerAdapter mScrollPickerAdapter = builder.build();
        mScrollPickerView.setAdapter(mScrollPickerAdapter);
    }
}
