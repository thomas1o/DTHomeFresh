package com.example.dthomefresh.sellers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.dthomefresh.R
import com.example.dthomefresh.data.Seller
import com.example.dthomefresh.databinding.FragmentSellersBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SellersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SellersListAdapter


    private lateinit var binding: FragmentSellersBinding
    private lateinit var viewModel: SellersViewModel
    private lateinit var viewModelFactory: SellersViewModelFactory
    private lateinit var auth: FirebaseAuth
    private var loggedIn: Boolean = false

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loggedIn = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sellers, container, false
        )

        viewModelFactory = SellersViewModelFactory(SellersFragmentArgs.fromBundle(requireArguments()).optionSelected)
        viewModel = ViewModelProvider(this, viewModelFactory)[SellersViewModel::class.java]
        binding.sellerViewModel = viewModel

        auth = Firebase.auth

        readAllSellers()

        val animationView: LottieAnimationView = binding.lottieAnimationView

        binding.upButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_categoriesFragment)
        }

        binding.profileButton.setOnClickListener{
            if(loggedIn)
                Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_profileFragment)
            else
                Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_loginFragment)
        }

        viewModel.options.observe(viewLifecycleOwner, Observer { newOptions ->
            if(newOptions[0] == true) {
                setDefaultColor()
                binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_2))
                binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_2))
            }
            else if(newOptions[1] == true) {
                setDefaultColor()
                binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_2))
                binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_2))
            }
            else if(newOptions[2] == true) {
                setDefaultColor()
                binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_2))
                binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_2))
            }
        })

        viewModel.loadingAnimation.observe(viewLifecycleOwner, Observer { shouldAnimate ->
            if (shouldAnimate) {
                animationView.playAnimation()
                animationView.visibility = View.VISIBLE
            } else {
                animationView.cancelAnimation()
                animationView.visibility = View.GONE
            }
        })

        return binding.root
    }
    private fun setDefaultColor() {
        binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_1))
        binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_4))
        binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_1))
        binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_4))
        binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_1))
        binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_4))
    }

    private fun readAllSellers() {

        val database = FirebaseDatabase.getInstance()
        database.reference.child("Sellers").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sellersList = mutableListOf<Seller>()

                for (sellerSnapshot in dataSnapshot.children) {
                    val seller = sellerSnapshot.getValue(Seller::class.java)
                    seller?.let { sellersList.add(it) }
                }
                setupRecyclerView(sellersList)
                viewModel.stopLoadingAnimation()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // TODO- Handle error
            }
        })

    }

    private fun setupRecyclerView(sellersList: List<Seller>) {
        recyclerView = binding.sellersList
        adapter = SellersListAdapter(sellersList)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

}