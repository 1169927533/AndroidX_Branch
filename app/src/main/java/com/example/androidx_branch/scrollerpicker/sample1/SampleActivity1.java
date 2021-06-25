package com.example.androidx_branch.scrollerpicker.sample1;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidx_branch.R;
import com.example.androidx_branch.scrollerpicker.library.adapter.ScrollPickerAdapter;
import com.example.androidx_branch.scrollerpicker.library.view.ScrollPickerView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity1 extends AppCompatActivity {
    private ScrollPickerView mScrollPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample1);
        initView();
        initData();
    }

    private void initView() {
        mScrollPickerView = findViewById(R.id.scroll_picker_view);
        mScrollPickerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String itemData = "item: " + i;
            list.add(itemData);
        }

        ScrollPickerAdapter.ScrollPickerAdapterBuilder<String> builder =
                new ScrollPickerAdapter.ScrollPickerAdapterBuilder<String>(this)
                        .setDataList(list)
                        .selectedItemOffset(1)
                        .visibleItemNumber(3)
                        .setDivideLineColor("#E5E5E5")
                        .setItemViewProvider(null)
                        .setOnClickListener(new ScrollPickerAdapter.OnClickListener() {
                            @Override
                            public void onSelectedItemClicked(View v) {
                                String text = (String) v.getTag();
                                if (text != null) {
                                    Toast.makeText(SampleActivity1.this, text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        ScrollPickerAdapter mScrollPickerAdapter = builder.build();
        mScrollPickerView.setAdapter(mScrollPickerAdapter);
    }
}
