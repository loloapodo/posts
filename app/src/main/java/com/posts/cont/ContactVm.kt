package com.posts.cont

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import android.provider.ContactsContract.CommonDataKinds.Phone

import android.provider.ContactsContract.CommonDataKinds.StructuredName

import android.provider.ContactsContract.CommonDataKinds.Email
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern


class ContactVm:ViewModel() {


    /**
     * @param contentResolver from the activity
     * @param name name of the contact
     * @param number mobile phone number of contact
     * @param email work email address of contact
     * @param ContactId id of the contact which you want to update
     * @return true if contact is updated successfully<br></br>
     * false if contact is not updated <br></br>
     * false if phone number contains any characters(It should contain only digits)<br></br>
     * false if email Address is invalid <br></br><br></br>
     *
     * You can pass any one among the 3 parameters to update a contact.Passing all three parameters as **null** will not update the contact
     * <br></br><br></br>**Note: **This method requires permission **android.permission.WRITE_CONTACTS**<br></br>
     */
    fun updateContact(contentResolver: ContentResolver,name: String, number: String, email: String, ContactId: String): Boolean {
        var name = name
        var number = number
        var email = email
        var success = true
        val phnumexp = "^[0-9]*$"
        try {
            name = name.trim { it <= ' ' }
            email = email.trim { it <= ' ' }
            number = number.trim { it <= ' ' }
            if (name == "" && number == "" && email == "") {
                success = false
            } else if (number != "" && !match(number, phnumexp)) {
                success = false
            } else if (email != "" && !isEmailValid(email)) {
                success = false
            } else {

                val where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val emailParams = arrayOf(ContactId, Email.CONTENT_ITEM_TYPE)
                val nameParams = arrayOf(ContactId, StructuredName.CONTENT_ITEM_TYPE)
                val numberParams = arrayOf(ContactId, Phone.CONTENT_ITEM_TYPE)

                val ops = ArrayList<ContentProviderOperation>()

                /*if (email != "") {
                    ops.add(
                        ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, emailParams)
                            .withValue(Email.DATA, email)
                            .build()
                    )
                }*/
                if (name != "") {
                    ops.add(
                        ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, nameParams)
                            .withValue(StructuredName.DISPLAY_NAME, name)
                            .build()
                    )
                    ops.add(
                        ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, nameParams)
                            .withValue(StructuredName.DISPLAY_NAME_ALTERNATIVE, "")
                            .build()
                    )


                }
                if (number != "") {
                    ops.add(
                        ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, numberParams)
                            .withValue(Phone.NUMBER, number)
                            .build()
                    )
                }
                contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            success = false
        }
        return success
    }





    private fun isEmailValid(email: String): Boolean {
        val emailAddress = email.trim { it <= ' ' }
        return if (emailAddress == null) false else if (emailAddress == "") false else if (emailAddress.length <= 6) false else {
            val expression =
                "^[a-z][a-z|0-9|]*([_][a-z|0-9]+)*([.][a-z|0-9]+([_][a-z|0-9]+)*)?@[a-z][a-z|0-9|]*\\.([a-z][a-z|0-9]*(\\.[a-z][a-z|0-9]*)?)$"
            val inputStr: CharSequence = emailAddress
            val pattern: Pattern = Pattern.compile(
                expression,
                Pattern.CASE_INSENSITIVE
            )
            val matcher: Matcher = pattern.matcher(inputStr)
            if (matcher.matches()) true else false
        }
    }

    private fun match(stringToCompare: String, regularExpression: String): Boolean {
        var success = false
        val pattern: Pattern = Pattern.compile(regularExpression)
        val matcher: Matcher = pattern.matcher(stringToCompare)
        if (matcher.matches()) success = true
        return success
    }



}