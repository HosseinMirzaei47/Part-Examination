package com.example.myapplication.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.nattramn.core.resource.Status

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        binding = FragmentDetailsBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWithDatabase()
        getRemoteData()
    }

    private fun getRemoteData() {
        detailsViewModel.getAllInfo()
        detailsViewModel.allInfoResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.info = it.data
                }
                Status.LOADING -> {

                }
                else -> {
                }
            }
        })
    }

    private fun initWithDatabase() {
        detailsViewModel.getAllInfoDb()
        detailsViewModel.allInfoDbResult.observe(viewLifecycleOwner, Observer {
            binding.info = it
        })
    }

}