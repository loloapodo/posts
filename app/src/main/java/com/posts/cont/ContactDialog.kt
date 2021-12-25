package com.posts.cont

import android.os.Bundle
import android.view.View

import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.posts.z.R

class ContactDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.edit_contact)


    }

    fun AcceptEditClick(view: View) {


        val nameEditText=findViewById<EditText>(R.id.edit_conctactname)
        val name=nameEditText.text.toString()
        val phoneEditText=findViewById<EditText>(R.id.edit_conctactphone)
        val phone=phoneEditText.text.toString()
        if (name.isNotBlank()&&phone.isNotBlank()){
            intent.putExtra("name",name)
            intent.putExtra("phone",phone)
            setResult(RESULT_OK, intent);
            finish();
        }else
        {
            Toast.makeText(this,"Fill the fields",Toast.LENGTH_SHORT).show()
        }



    }
    fun CancelEditClick(view: View) {

        setResult(RESULT_CANCELED)
        finish()

    }


}