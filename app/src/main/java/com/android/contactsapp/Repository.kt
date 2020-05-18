package com.android.test

import com.android.contactsapp.Contact
import com.google.gson.JsonObject
import io.reactivex.Single

class Repository {

    private val networkService = Networking.create()

    fun getContacts(): Single<List<Contact>> {
        return networkService.getContacts()
    }

    fun postContact(contact: Contact): Single<String> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("firstName", contact.firstname)
        jsonObject.addProperty("lastName", contact.lastname)
        jsonObject.addProperty("email", contact.email)
        jsonObject.addProperty("phone", contact.phone)
        return networkService.saveContact(jsonObject)
    }

}