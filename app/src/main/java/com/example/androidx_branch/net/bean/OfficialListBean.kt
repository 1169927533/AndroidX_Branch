package com.example.androidx_branch.net.bean

/**
 * @Author Yu
 * @Date 2021/5/19 10:05
 * @Description TODO
 */
data class OfficialListBean(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)