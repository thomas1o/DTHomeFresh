package com.example.dthomefresh.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dthomefresh.R
import com.example.dthomefresh.data.Seller
import com.example.dthomefresh.data.adapter.SellersListAdapter
import com.example.dthomefresh.databinding.FragmentSellerDetailsBinding
import com.example.dthomefresh.utils.openDialPad
import com.example.dthomefresh.utils.openWhatsapp
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SellerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSellerDetailsBinding
    private lateinit var adapter: SellersListAdapter
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var seller: Seller? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_seller_details, container, false
        )

        binding.btUp.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_sellerDetailsFragment_to_sellerListFragment)
        }

        //testing
        val sellerUid = "bauo4k4F4BgEST3imlc417BRH8r2"

        fetchSellerData(sellerUid) { seller ->
            if (seller != null) {
                binding.brandName.text = seller.brandName
                binding.sellerAddress.text = seller.address
            } else {
                binding.brandName.text = "---"
                binding.sellerAddress.text = "---"
            }
        }

        binding.btCall.setOnClickListener {
            if (seller != null)
                openDialPad(requireContext(), seller!!.phoneNumber)
            else
                Snackbar.make(requireView(), "Phone number is not available for this seller", Snackbar.LENGTH_SHORT).show()
        }

        binding.btWhatsapp.setOnClickListener {
            if (seller != null)
                openWhatsapp(requireContext(), "91" + seller!!.whatsappNumber)
            else
                Snackbar.make(requireView(), "Whatsapp is not available for this seller", Snackbar.LENGTH_SHORT).show()
        }


        return binding.root
    }

    private fun fetchSellerData(uid: String, callback: (Seller?) -> Unit) {
        val sellerRef: DatabaseReference = database.getReference("Sellers").child(uid)

        sellerRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot: DataSnapshot? = task.result
                seller = snapshot?.getValue(Seller::class.java)
                callback(seller)
            } else {
                // Handle unsuccessful fetch
                callback(null)
            }
        }
    }


}