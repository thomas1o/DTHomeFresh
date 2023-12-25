package com.example.dthomefresh.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentAddressBottomSheetBinding
import com.example.dthomefresh.utils.Validator
import com.example.dthomefresh.viewmodel.AddressBottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddressBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddressBottomSheetBinding
    private lateinit var viewModel: AddressBottomSheetViewModel

    private val validator = Validator()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_address_bottom_sheet,
            container,
            false
        )

        viewModel = ViewModelProvider(this)[AddressBottomSheetViewModel::class.java]

        var name: String
        var phoneNumber: String
        var address: String
        var nearbyLandmark: String

        viewModel.loadingDone.observe(viewLifecycleOwner, Observer { loadingDone ->
            if (!loadingDone) {
                binding.animLoading.visibility = View.VISIBLE
                binding.animLoading.playAnimation()
            } else {
                binding.animLoading.cancelAnimation()
                binding.animLoading.visibility = View.INVISIBLE
            }
        })

        binding.btSave.setOnClickListener {
            name = binding.etName.text.toString()
            phoneNumber = binding.etPhoneNumber.text.toString()
            address = binding.etAddress.text.toString()
            nearbyLandmark = binding.etNearbyLandmark.text.toString()

            binding.tiName.error = validator.nameValidator(name)
            binding.tiPhoneNumber.error = validator.phoneNumberValidator(phoneNumber)
            binding.tiAddress.error = validator.addressValidator(address)

            if (binding.tiName.error == null && binding.tiPhoneNumber.error == null && binding.tiAddress.error == null) {
                viewModel.onLoading()
                viewModel.writeNewUser(name, phoneNumber, address, nearbyLandmark)
                viewModel.onLoadingDone()
                dismiss()
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}