package com.example.androidx_branch.net

import com.example.androidx_branch.net.bean.OfficialListBean
import retrofit2.http.GET

/**
 * @Author Yu
 * @Date 2021/5/19 10:00
 * @Description TODO
 */
interface ApiInterFace {
    @GET("/wxarticle/chapters/json")
    suspend fun officialList(): ArrayList<OfficialListBean>
}