package com.example.myapplication.features.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCountryBinding
import com.example.myapplication.features.home.country.CountryViewModel
import com.example.nattramn.core.resource.Status
import com.google.android.material.snackbar.Snackbar

class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private lateinit var countryViewModel: CountryViewModel
    private val args: CountryFragmentArgs by navArgs()

    private lateinit var country: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        country = args.countryName

        binding = FragmentCountryBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCountryInfo()
    }

    private fun showCountryInfo() {
        initWithDatabase()
        getRemoteData()
    }

    private fun getRemoteData() {
        countryViewModel.getCountry(country)
        countryViewModel.countryResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.country = it.data
                }
                Status.LOADING -> {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.loading_new_info_country),
                        Snackbar.LENGTH_SHORT
                    )
                }
                else -> {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.network_timeout),
                        Snackbar.LENGTH_SHORT
                    )
                }
            }
        })
    }

    private fun initWithDatabase() {
        countryViewModel.getCountryDb(country)
        countryViewModel.countryDbResult.observe(viewLifecycleOwner, Observer {
            binding.country = it
        })

        countryViewModel.getCountryInfoDb(country)
        countryViewModel.countryInfoDbResult.observe(viewLifecycleOwner, Observer {
            binding.country?.countryInfo = it
        })
    }

}