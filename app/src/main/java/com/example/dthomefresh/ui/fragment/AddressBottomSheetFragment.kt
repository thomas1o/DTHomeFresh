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

        var ownerName: String
        var brandName: String
        var phoneNumber: String
        var whatsappNumber: String
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
            ownerName = binding.etOwnerName.text.toString()
            brandName = binding.etBrandName.text.toString()
            phoneNumber = binding.etPhoneNumber.text.toString()
            whatsappNumber = binding.etWhatsappNumber.text.toString()
            address = binding.etAddress.text.toString()
            nearbyLandmark = binding.etNearbyLandmark.text.toString()

            binding.tiOwnerName.error = validator.nameValidator(ownerName)
            binding.tiBrandName.error = validator.nameValidator(brandName)
            binding.tiPhoneNumber.error = validator.phoneNumberValidator(phoneNumber)
            binding.tiWhatsappNumber.error = validator.phoneNumberValidator(whatsappNumber)
            binding.tiAddress.error = validator.addressValidator(address)

            if (binding.tiOwnerName.error == null && binding.tiBrandName.error == null && binding.tiPhoneNumber.error == null
                && binding.tiWhatsappNumber.error == null && binding.tiAddress.error == null) {
                viewModel.onLoading()
                viewModel.writeNewUser(ownerName, brandName, phoneNumber, whatsappNumber, address, nearbyLandmark)
                viewModel.onLoadingDone()
                dismiss()
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}