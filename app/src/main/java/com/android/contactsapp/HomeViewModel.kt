package com.android.contactsapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.test.Repository
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val repository = Repository()
    private val compositeDisposable = CompositeDisposable()
    val contactList = MutableLiveData<ArrayList<Contact>>()
    val loading = MutableLiveData<Boolean>()

    fun getContactList() {
        loading.postValue(true)
        val disposable = object : DisposableSingleObserver<List<Contact>>() {
            override fun onSuccess(t: List<Contact>) {
                loading.postValue(false)
                if (!t.isNullOrEmpty())
                    contactList.postValue(t as ArrayList<Contact>)
                else {
                    contactList.postValue(arrayListOf())
                }
            }

            override fun onError(e: Throwable) {
                loading.postValue(false)
            }
        }
        repository.getContacts().subscribeOn(Schedulers.io())
            .subscribe(disposable)
        compositeDisposable.add(disposable)

    }

    fun postContact(contact: Contact) {
        loading.postValue(true)
        val disposable = object : DisposableSingleObserver<JsonObject>() {
            override fun onSuccess(t: JsonObject) {
                loading.postValue(false)
            }

            override fun onError(e: Throwable) {
                loading.postValue(false)
            }
        }
        repository.postContact(contact).subscribeOn(Schedulers.io())
            .subscribe(disposable)
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}