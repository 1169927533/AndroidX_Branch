package com.example.androidx_branch.fulldialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

/**
 * @Author Yu
 * @Date 2021/3/29 17:08
 * @Description TODO
 */
public class ActivityViewModel extends AppCompatActivity {
    void setActivityViewModel() {

        MyViewModle myViewModle = new ViewModelProvider(this).get(MyViewModle.class);

    }
}
