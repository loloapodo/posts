package com.posts.comm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.posts.api.ApiInterface


class CommentVm() : ViewModel() {

    lateinit var mParamPostId:String


     val comments: MutableLiveData<List<Comment>> by lazy { MutableLiveData<List<Comment>>() }

    fun defineId(id:String){

        Log.e("defineId",id)
        mParamPostId=id
        loadComments()

    }




    private fun loadComments() {

        val apiInterface = ApiInterface.create().getComments(mParamPostId)

        apiInterface.enqueue( object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>?) {

                if(response?.body() != null) {
                comments.value=response.body()
                }
            }



            override fun onFailure(call: Call<List<Comment>>, t: Throwable?) {
                Log.e("fallo one comment","fallo one comment")
                t?.printStackTrace()
            }
        })



    }




}