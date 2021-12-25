package com.posts.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.posts.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostVm: ViewModel() {



     val posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadPosts()
        }
    }

    private fun loadPosts() {
        val apiInterface = ApiInterface.create().getPosts()
        apiInterface.enqueue( object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {

                if (response != null) {
                    posts.value=response.body()
                    Log.e("Cant Posts:", response.body()?.size.toString())
                }



            }
            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

    }

}