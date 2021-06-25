package com.example.androidx_branch.net

import com.example.androidx_branch.net.bean.OfficialListBean
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


/**
 * @Author Yu
 * @Date 2021/5/19 10:00
 * @Description TODO
 */
interface ApiInterFace {
    @GET("/wxarticle/chapters/json")
    suspend fun officialList(): ArrayList<OfficialListBean>

    // 下载文件
    @Streaming
    @GET
    suspend fun downloadFileWithDynamicUrlAsync(@Url fileUrl: String?): Response<ResponseBody>

    //文章列表
    @FormUrlEncoded
    @POST("/lg/uncollect_originId/2333/json")
    suspend fun getFileList(@Field("id") id:String): Response<ResponseBody>

}