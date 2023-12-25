package com.example.dthomefresh.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentMapBottomSheetBinding
import com.example.dthomefresh.viewmodel.MapAndBottomSheetViewModel
import com.example.dthomefresh.viewmodel.MapBottomSheetViewModel

class MapBottomSheet : Fragment() {

    lateinit var binding: FragmentMapBottomSheetBinding
    lateinit var viewModel: MapBottomSheetViewModel
    private lateinit var sharedViewModel: MapAndBottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_bottom_sheet, container, false)

        viewModel = ViewModelProvider(this)[MapBottomSheetViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[MapAndBottomSheetViewModel::class.java]

        binding.btEnterAddress.setOnClickListener {
            val addressBottomSheetFragment = AddressBottomSheetFragment()
            addressBottomSheetFragment.show(parentFragmentManager, "AddressBottomSheetFragment")
        }

        sharedViewModel.animationOn.observe(viewLifecycleOwner, Observer { isAnimationOn ->
            if (isAnimationOn == true) {
                setDetailsInvisibleAndStartAnim()
            } else {
                setDetailsVisibleAndStopAnim()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.address.observe(viewLifecycleOwner) { newAddress ->
            binding.placeName.text = newAddress
            sharedViewModel.stopAnimation()
        }
    }

    private fun setDetailsVisibleAndStopAnim() {
        binding.shimmerMapBottomSheet.stopShimmer()
        binding.shimmerMapBottomSheet.visibility = View.GONE
        binding.placeDetails.visibility = View.VISIBLE
        binding.btEnterAddress.visibility = View.VISIBLE
    }
    private fun setDetailsInvisibleAndStartAnim() {
        binding.shimmerMapBottomSheet.startShimmer()
        binding.shimmerMapBottomSheet.visibility = View.VISIBLE
        binding.placeDetails.visibility = View.INVISIBLE
        binding.btEnterAddress.visibility = View.INVISIBLE
    }

}