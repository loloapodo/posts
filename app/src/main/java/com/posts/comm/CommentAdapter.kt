package com.posts.comm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.posts.z.R

class CommentAdapter(cxt:Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  var mList: List<Comment> = ArrayList()
    private var layoutInflater: LayoutInflater = LayoutInflater.from(cxt)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_comment, parent, false)
        return CommentH(view)
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {

        val holder = holderT as CommentH

        //holder.idTextView.text= mList[position].id
        holder.name.text=mList[position].name
        holder.email.text=mList[position].email
        holder.body.text=mList[position].body


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(list:List<Comment>){
        Log.e("SetListComments",list.size.toString())
        mList=list
    }


    inner class CommentH(view: View) : RecyclerView.ViewHolder(view) {
        //var idTextView:TextView = view.findViewById(R.id.id_textview)
        var name:TextView = view.findViewById(R.id.name)
        var email:TextView = view.findViewById(R.id.email)
        var body:TextView = view.findViewById(R.id.body)
    }

}
