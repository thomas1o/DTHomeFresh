package com.example.dthomefresh.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dthomefresh.R
import com.example.dthomefresh.data.Item
import com.example.dthomefresh.databinding.PurchaseGridItemBinding

class PurchaseItemListAdapter(
    private val purchaseItemList: List<Item>
) : RecyclerView.Adapter<PurchaseItemListAdapter.ViewHolder>() {

    private lateinit var binding: PurchaseGridItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.purchase_grid_item, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val purchaseItem = purchaseItemList[position]

        holder.imageView.setImageResource(R.raw.image_template)
        holder.bind(purchaseItem)
//        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.card_fall_down)
//        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int {
        return purchaseItemList.size
    }

    inner class ViewHolder(binding: PurchaseGridItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = itemView.findViewById(R.id.purchase_item_image)
        fun bind(purchaseItem: Item) {
            binding.purchaseItem = purchaseItem
            binding.executePendingBindings()
        }
    }
}
