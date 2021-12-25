package com.posts.cont

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.posts.z.R

class ContactAdapter(cxt: Context,val cr:ContentResolver,val resources: Resources,
                     val editContact: (Id:String, name:String, number:String,pos:Int) -> Unit,
                     val delContact: (uri:Uri,pos:Int,button: ImageView) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var cur: Cursor? =
        cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)


    private var layoutInflater: LayoutInflater = LayoutInflater.from(cxt)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_contact, parent, false)
        return ContactH(view)
    }


    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holderT, position, payloads)

        if(payloads.isNotEmpty()) {

                val i=  payloads[0] as Intent
                val holder = holderT as ContactH
                holder.name.text= i.getStringExtra("name")
                holder.phone.text=i.getStringExtra("phone")


        }else {
            super.onBindViewHolder(holderT,position, payloads);
        }
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {


        val holder = holderT as ContactH


        Log.e("posicion",position.toString())
        if (position<0){return}

        holder.face.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_neutral_black_24dp))
        try {

    if (cur!=null) {
        cur?.moveToPosition(position)

        val id: String = cur!!.getString(cur!!.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
        val lookupkey: String =
            cur!!.getString(cur!!.getColumnIndexOrThrow(ContactsContract.Contacts.LOOKUP_KEY))
        val name: String =
            cur!!.getString(cur!!.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))

        if (cur!!.getInt(cur!!.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
            val pCur: Cursor? = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf(id),
                null
            )
            if (pCur != null) {
                while (pCur.moveToNext()) {
                    val phoneNo: String = pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    //contacts.value?.add(Contact("$id", "$name", "$$phoneNo"))

                    holder.name.text = name
                    holder.phone.text=phoneNo

                    holder.editBut.setOnClickListener {
                        editContact(id, name, phoneNo,position)
                        //val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupkey)
                        //println("The uri is $uri")
                        //cr.delete(uri, null, null)
                    }
                    holder.deleteBut.setOnClickListener {
                        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupkey)
                        Log.d("The uri is", uri.toString())
                        delContact(uri,position,holder.face)

                    }


                }

                pCur.close()
            }
        }
    }
        }catch (e:Exception){e.printStackTrace()

        }


    }



    override fun getItemCount(): Int {

        if (cur==null){return 0}
        else {return cur!!.count}
    }






    inner class ContactH(view: View) : RecyclerView.ViewHolder(view) {
        //var idTextView:TextView = view.findViewById(R.id.id_textview)
        var name:TextView = view.findViewById(R.id.contactname)
        var phone:TextView = view.findViewById(R.id.conctactphone)
        var face:ImageView = view.findViewById(R.id.face)
        var editBut:ImageView=view.findViewById(R.id.modify)
        var deleteBut:ImageView=view.findViewById(R.id.delete)

        init {
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        cur?.close()
        super.onDetachedFromRecyclerView(recyclerView)
    }













}
