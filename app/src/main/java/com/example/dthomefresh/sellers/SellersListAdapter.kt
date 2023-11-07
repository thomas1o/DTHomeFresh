//package com.example.dthomefresh.sellers
//
//import androidx.recyclerview.widget.RecyclerView
//import com.example.dthomefresh.data.Seller
//
//class SellersListAdapter: RecyclerView.Adapter<SellersListAdapter.SellersViewHolder> () {
//    val data = listOf<Seller>()
//
//    class SellersViewHolder(private var binding: GridViewItemBinding):
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(sellers: Seller) {
//            binding. = sellers
//            // This is important, because it forces the data binding to execute immediately,
//            // which allows the RecyclerView to make the correct view size measurements
//            binding.executePendingBindings()
//        }
//    }
//
////    override fun getItemCount() = data.size
//    override fun getItemCount() = 4
//
//}