package com.example.dthomefresh.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentContactUsBinding
import com.example.dthomefresh.utils.openDialPad
import com.example.dthomefresh.utils.openMail
import com.example.dthomefresh.utils.openWhatsapp

class ContactUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentContactUsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_us, container, false
        )

//        binding.btUp.setOnClickListener {
//            Navigation.findNavController(requireView())
//                .navigate(R.id.action_contactUsFragment_to_categoriesFragment)
//        }

        binding.btWhatsapp.setOnClickListener {
            onWhatsAppButtonClicked(requireView())
        }

        //TODO- complete this
        binding.btMail.setOnClickListener {
            onMailButtonClicked(requireView())
        }

        binding.btCall.setOnClickListener {
            onCallButtonClicked(requireView())
        }


        return binding.root
    }

    private fun onWhatsAppButtonClicked(view: View) {
        openWhatsapp(requireContext(), "+919961539011")
    }

    private fun onMailButtonClicked(view: View) {
        openMail(requireContext(), "thomasbiju43210@gmail.com")
    }

    private fun onCallButtonClicked(view: View) {
        openDialPad(requireContext(), "+919961539011")
    }

}