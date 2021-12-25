package com.posts.comm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.posts.z.R


class CommentAct : AppCompatActivity() {

    private lateinit var postId: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommentAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_comment)
        title="COMMENTS"

        postId =intent.getStringExtra("id")!!



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter= CommentAdapter(this)
        recyclerView.adapter=adapter

        val model: CommentVm by  viewModels()
        model.defineId(postId)

        model.comments.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })






    }


}