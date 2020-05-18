package com.android.contactsapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_contact.view.*

class ContactsAdapter(var list: ArrayList<Contact>, var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ContactViewHolder
        viewHolder.itemView.tvName.text = list[position].firstname + " " + list[position].lastname

        if (list[position].favorite)
            viewHolder.itemView.ivFavorite.visibility = View.VISIBLE
        else
            viewHolder.itemView.ivFavorite.visibility = View.GONE
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("item", list[adapterPosition])
                context.startActivity(intent)
            }
        }

    }

    fun updateList(arrayList: ArrayList<Contact>) {
        list.addAll(arrayList)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
    }
}
