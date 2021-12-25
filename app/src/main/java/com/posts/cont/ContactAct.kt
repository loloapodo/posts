package com.posts.cont


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



import android.util.Log

import androidx.activity.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

import android.content.pm.PackageManager
import android.os.Handler
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import android.app.SearchManager


import android.view.Menu
import androidx.appcompat.widget.SearchView

import androidx.core.view.MenuItemCompat
import com.posts.z.R


class ContactAct : AppCompatActivity() {


    private lateinit var model: ContactVm
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter

    private var posTouched:Int = 0
    private var idTouched:String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_contact)
        title="CONTACTS"

        val model:ContactVm by viewModels()
        this.model=model



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.layoutManager= LinearLayoutManager(this)


        PERMISION.askPermision(this)



}



     fun YesToPermisionRead() {
        PERMISION.askPermision2(this)
    }
    fun InitAdapter() {

        adapter= ContactAdapter(this,contentResolver,resources, {id,name,number,pos->

            ShowDialog(id,pos)
        },{uri,pos,face->

             var undo=false

            val mySnackbar = Snackbar.make(findViewById(R.id.recyclerview),R.string.str_recycled, Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.undo_string, {undo=true})
            mySnackbar.show()
            face.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_dissatisfied_black_24dp))

            val obAnim=getObjAnim(face)
            obAnim.start()

            Handler().postDelayed({
                mySnackbar.dismiss()
                if (undo==false){
                    if (contentResolver.delete(uri,null,null)>0){
                        adapter.notifyItemRemoved(pos)
                        Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    obAnim.cancel()
                    face.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_neutral_black_24dp))
                }
            }, 3000)
            return@ContactAdapter
        })
        recyclerView.adapter=adapter
    }





    private fun ShowDialog(id:String,pos:Int) {
        posTouched=pos
        idTouched=id
        startActivityForResult(Intent(this,ContactDialog::class.java),1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== RESULT_OK) {

            val name = data?.getStringExtra("name")!!
            val phone = data?.getStringExtra("phone")!!
            Log.d("RESULT_OK",name)
           val succes= model.updateContact(contentResolver, name, phone, "anyemail@gmail.com", idTouched)

            if (succes){
                adapter.notifyItemChanged(posTouched,data)
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }


        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISION.RE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                    YesToPermisionRead()
                } else {
                    Toast.makeText(this, "Permission denied to read your contacts", Toast.LENGTH_SHORT).show()
                }
                return
            }
            PERMISION.WR -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    InitAdapter()

                } else {
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        // Retrieve the SearchView and plug it into SearchManager
        val searchView: SearchView =
            MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {

                Toast.makeText(this@ContactAct,"TO DO",Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onQueryTextSubmit(qString: String): Boolean {
                Toast.makeText(this@ContactAct,"TO DO",Toast.LENGTH_SHORT).show()






                return true
            }
        })
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        return true
    }

    private fun getObjAnim(view: ImageView): ObjectAnimator {
        val obAnim = ObjectAnimator.ofPropertyValuesHolder(view,PropertyValuesHolder.ofFloat("scaleX", 0.5f), PropertyValuesHolder.ofFloat("scaleY", 0.5f))
        obAnim.duration = 500
        obAnim.repeatMode = ValueAnimator.REVERSE
        obAnim.repeatCount = ValueAnimator.INFINITE
        return obAnim
    }
}