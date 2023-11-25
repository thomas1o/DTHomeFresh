package com.example.dthomefresh.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dthomefresh.R
import com.example.dthomefresh.data.Seller
import com.example.dthomefresh.databinding.SellerItemBinding


class SellersListAdapter(private val sellersList: List<Seller>) : RecyclerView.Adapter<SellersListAdapter.ViewHolder>() {

    private lateinit var binding: SellerItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.seller_item, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val seller = sellersList[position]
        holder.imageView.setImageResource(R.raw.image_1)
        holder.bind(seller)
    }

    override fun getItemCount(): Int {
        return sellersList.size
    }

    inner class ViewHolder(binding: SellerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = itemView.findViewById(R.id.mars_image)
        fun bind(seller: Seller) {
            binding.seller = seller
            binding.executePendingBindings()
        }
    }
}
