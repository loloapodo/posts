package com.posts.post

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.posts.z.R

class PostAdapter(cxt: Context,val clickPost: (postId:String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  var mList: List<Post> =ArrayList()
    private var layoutInflater: LayoutInflater = LayoutInflater.from(cxt)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_post, parent, false)
        return PostH(view)
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {

        val holder = holderT as PostH
        //holder.idTextView.text= mList[position].id
        holder.title.text= mList[position].title
        holder.body.text= mList[position].body
        holder.itemView.setOnClickListener { clickPost(mList[position].id) }

    }

    override fun getItemCount(): Int {
        return mList.size
    }





    fun setList(list:List<Post>){
        mList=list
        Log.e("PostAdapter","setList ${list.size}")
    }


    inner class PostH(view: View) : RecyclerView.ViewHolder(view) {
        //var idTextView:TextView = view.findViewById(R.id.id_textview)
        var title:TextView = view.findViewById(R.id.title)
        var body:TextView = view.findViewById(R.id.body)

        init {

        }



    }

}
