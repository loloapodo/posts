package com.posts.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.posts.z.R
import com.posts.comm.CommentAct
import com.posts.cont.ContactAct

import android.view.View


class PostAct : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private val CODEREADCONTACTS= 22
    private val CODEWRITEEXTERNAL= 23


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_post)
        title="POSTS"









        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter= PostAdapter(this) {

            Log.e("clicked",it
            )
            val i =Intent(this, CommentAct::class.java)
            i.putExtra("id",it)
            startActivity(i)
        }









        recyclerView.adapter=adapter

        val model: PostVm by  viewModels()
        model.posts.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })










    }

    fun fabclick(view: View) {

        val i =Intent(this, ContactAct::class.java)
        startActivity(i)


    }


}