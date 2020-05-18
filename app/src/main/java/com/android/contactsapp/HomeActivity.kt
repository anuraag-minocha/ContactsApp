package com.android.contactsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel
    lateinit var contactsAdapter: ContactsAdapter
    var arrayList: ArrayList<Contact> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        contactsAdapter = ContactsAdapter(arrayListOf(), this)

        setupView()
        setUpObserver()

        viewModel.getContactList()
    }

    private fun setupView() {
        rvContacts.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvContacts.adapter = contactsAdapter

        fastscroller.setupWithRecyclerView(
            rvContacts,
            { position ->
                val item = arrayList[position] // Get your model object
                // or fetch the section at [position] from your database
                FastScrollItemIndicator.Text(
                    item.firstname.substring(
                        0,
                        1
                    ).toUpperCase() // Grab the first letter and capitalize it
                ) // Return a text indicator
            }
        )
        fastscroller_thumb.setupWithFastScroller(fastscroller)

        ivAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpObserver() {

        viewModel.contactList.observe(this, Observer {
            if (it.isNotEmpty()) {
                it.sortWith(Comparator { x, y ->
                    x.firstname.toLowerCase().compareTo(y.firstname.toLowerCase())
                })
                arrayList = it
                contactsAdapter.updateList(it)
            }
        })


        viewModel.loading.observe(this, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        viewModel.error.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

}
