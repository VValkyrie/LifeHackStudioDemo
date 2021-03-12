package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.CompanyListItem
import com.squareup.picasso.Picasso

class DataAdapter(var items: List<CompanyListItem>,
                  val callback: Callback,
                  private val context: Context?) :
    RecyclerView.Adapter<DataAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.post_item,
            parent,
            false
        )
    )

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        Picasso
            .get()
            .load(context?.getString(R.string.base_url) + items[position].img)
            .fit()
            .into(holder.image)
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val id = itemView.findViewById<TextView>(R.id.id)
        private val title = itemView.findViewById<TextView>(R.id.title)
        internal val image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: CompanyListItem) {
            id.text = item.id.toString()
            title.text = item.name
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: CompanyListItem)
    }
}