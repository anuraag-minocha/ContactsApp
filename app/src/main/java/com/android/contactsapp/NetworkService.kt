package com.android.test

import com.android.contactsapp.Contact
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST


interface NetworkService {

    @GET("/contacts")
    fun getContacts(): Single<List<Contact>>

    @POST("/contacts")
    fun saveContact(
        @Field("firstName") fname: String, @Field("lastName") lname: String, @Field("email") email: String, @Field(
            "phone"
        ) phone: String
    ): Single<JsonObject>

}