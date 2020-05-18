package com.android.contactsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : AppCompatActivity() {
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        tvCancel.setOnClickListener { finish() }

        tvDone.setOnClickListener {
            if (validate()) {
                viewModel.postContact(
                    Contact(
                        firstname.text.toString(),
                        lastname.text.toString(),
                        email.text.toString(),
                        mobile.text.toString(),
                        false
                    )
                )
            } else
                Toast.makeText(this, "Please enter all details.", Toast.LENGTH_SHORT).show()
        }

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
            } else {
                finish()
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            }
        })
    }

    fun validate(): Boolean {
        if (firstname.text.toString().trim().isNotEmpty() && lastname.text.toString().trim().isNotEmpty() && mobile.text.toString().trim().isNotEmpty() && email.text.toString().trim().isNotEmpty())
            return true
        return false
    }

}
