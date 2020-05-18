package com.android.contactsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        contact = intent.getParcelableExtra("item")

        name.text = contact.firstname + " " + contact.lastname
        email.text = contact.email
        phone.text = contact.phone

        message.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:" + contact.phone)
            startActivity(sendIntent)
        }
        call.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_DIAL)
            sendIntent.setData(Uri.parse("tel:" + contact.phone))
            startActivity(sendIntent)
        }
        llEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.email))
            startActivity(intent)
        }
        tvCancel.setOnClickListener { finish() }
    }
}
