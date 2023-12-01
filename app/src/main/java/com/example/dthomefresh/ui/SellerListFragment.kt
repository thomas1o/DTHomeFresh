package com.example.dthomefresh.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import com.example.dthomefresh.adapter.SellersListAdapter
import com.example.dthomefresh.data.Seller
import com.example.dthomefresh.databinding.FragmentSellerListBinding
import com.example.dthomefresh.viewmodel.SellerListViewModel
import com.example.dthomefresh.viewmodelfactory.SellerListViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SellerListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SellersListAdapter

    private lateinit var binding: FragmentSellerListBinding
    private lateinit var viewModel: SellerListViewModel
    private lateinit var viewModelFactory: SellerListViewModelFactory
    private lateinit var auth: FirebaseAuth
    private var loggedIn: Boolean = false

    override fun onStart() {
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
            inflater, R.layout.fragment_seller_list, container, false
        )

        viewModelFactory = SellerListViewModelFactory(SellerListFragmentArgs.fromBundle(requireArguments()).optionSelected)
        viewModel = ViewModelProvider(this, viewModelFactory)[SellerListViewModel::class.java]
        binding.sellerListViewModel = viewModel

        auth = Firebase.auth

        viewModel.readAllSellers()

        val animationView: LottieAnimationView = binding.lottieAnimationView
        val searchBar: SearchView = binding.searchBar

        binding.upButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_sellerListFragment_to_categoriesFragment)
        }

        binding.profileButton.setOnClickListener{
            if(loggedIn)
                Navigation.findNavController(it).navigate(R.id.action_sellerListFragment_to_profileFragment)
            else
                Navigation.findNavController(it).navigate(R.id.action_sellerListFragment_to_loginFragment)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.readAllSellers()
            viewModel.refreshingAnimation.observe(viewLifecycleOwner, Observer { refreshAnimate ->
                if(!refreshAnimate)
                    binding.swipeRefreshLayout.isRefreshing = false
            })
        }

        //FIXME(Bug)- it doesn't update when we click backspace
        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                if (!searchText.isNullOrBlank()) {
                    viewModel.filterSellerByNameAndAddress(searchText)
                } else {
                    viewModel.readAllSellers()
                }
                return true
            }

        })

        //TODO- navigate to sellerDetailsFragment

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

        viewModel.setUpRecyclerView.observe(viewLifecycleOwner, Observer { startSetUpRecyclerView ->
            if (startSetUpRecyclerView) {
                viewModel.sellersList.observe(viewLifecycleOwner, Observer { sellersList ->
                    setupRecyclerView(sellersList)
                })
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

    private fun setupRecyclerView(sellersList: List<Seller>) {
        recyclerView = binding.sellersList
        adapter = SellersListAdapter(sellersList)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

}