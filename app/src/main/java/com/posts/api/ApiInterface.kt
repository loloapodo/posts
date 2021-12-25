package com.posts.api

import com.posts.comm.Comment
import com.posts.post.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("posts")
    fun getPosts() : Call<List<Post>>

    @GET("comments")
    fun getComments() : Call<List<Comment>>


    @GET("comments")
    fun getComments(@Query("postId") postId:String): Call<List<Comment>>

    companion object {
        var BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}