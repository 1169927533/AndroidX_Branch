package com.example.androidx_branch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author Yu
 * @Date 2021/2/22 13:56
 * @Description TODO
' */
inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(context: Context) {
    startActivity(Intent(context, T::class.java))
}

var lastTime = 0L
fun View.clickNoRepeat(interval: Long = 400, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastTime != 0L && (currentTime - lastTime < interval)) {
            return@setOnClickListener
        }
        lastTime = currentTime
        onClick(it)
    }
}