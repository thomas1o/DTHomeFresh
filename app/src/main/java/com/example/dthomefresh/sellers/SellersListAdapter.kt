package com.example.dthomefresh.sellers
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.example.dthomefresh.R
//import com.example.dthomefresh.data.Seller
//import com.example.dthomefresh.databinding.SellerItemBinding
//
//
//class SellersListAdapter(private val sellers: Seller): RecyclerView.Adapter<SellersListAdapter.SellersViewHolder> () {
//    val data = listOf<Seller>()
//
//    class SellersViewHolder(private var binding: SellerItemBinding):
//        ViewHolder(binding.root) {
//        fun bind() {
////            binding.name = name
//            // This is important, because it forces the data binding to execute immediately,
//            // which allows the RecyclerView to make the correct view size measurements
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SellersViewHolder {
//        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.seller_item, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = 4
//
//    override fun onBindViewHolder(holder: SellersViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
////    override fun getItemCount() = data.size
//}