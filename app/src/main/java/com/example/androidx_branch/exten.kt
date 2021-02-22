package com.example.androidx_branch

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author Yu
 * @Date 2021/2/22 13:56
 * @Description TODO
' */
inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(context: Context) {
    startActivity(Intent(context, T::class.java))
}