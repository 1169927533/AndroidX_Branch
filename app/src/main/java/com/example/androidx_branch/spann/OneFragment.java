package com.example.androidx_branch.spann;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidx_branch.R;

/**
 * @Author Yu
 * @Date 2021/7/2 17:12
 * @Description TODO
 */
public class OneFragment extends Fragment {
    String content = "";

    public OneFragment(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layput_one, container, false);
        TextView textView = view.findViewById(R.id.tv1);
        textView.setText(content);
        return view;
    }
}

