package com.android.test

import com.android.contactsapp.Contact
import com.google.gson.JsonObject
import io.reactivex.Single

class Repository {

    private val networkService = Networking.create()

    fun getContacts(): Single<List<Contact>> {
        return networkService.getContacts()
    }

    fun postContact(contact: Contact): Single<JsonObject> {
        return networkService.saveContact(contact.firstname, contact.lastname, contact.email, contact.phone)
    }

}