package com.example.model_windscreen

import android.animation.ObjectAnimator

/**
 * @Author Yu
 * @Date 2021/8/29 9:01
 * @Description 飘萍bean
 */
class WindScreenBean {
    var alpha = 1f
    public var translationX = 0f
    var objectAnimator: ObjectAnimator? = null
    var name = ""

    constructor(translationX: Float, name: String) {
        this.translationX = translationX
        this.name = name
        this.objectAnimator = ObjectAnimator.ofFloat(this, "translation", 0f, 1080f).apply {
            duration = 5000
        }

    }

    fun setTranslation(tx: Float) {
        translationX = tx
    }
}