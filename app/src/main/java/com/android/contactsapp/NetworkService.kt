package com.android.test

import com.android.contactsapp.Contact
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface NetworkService {

    @GET("/contacts")
    fun getContacts(): Single<List<Contact>>

    @POST("/contacts")
    fun saveContact(
        @Body json: JsonObject
    ): Single<String>

}