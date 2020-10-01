package com.example.myapplication.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.features.country.CountryEntity
import com.example.nattramn.core.resource.Status
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), OnCountryClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var countriesAdapter: CountriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAllInfo()

        showCountriesList()

    }

    private fun showAllInfo() {
        homeViewModel.getAllInfo()

        homeViewModel.allInfoResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.info = it.data
                }
                Status.LOADING -> {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.please_wait),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.network_timeout),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun showCountriesList() {

        initListWithDb()
        getDataFromRemote()

    }

    private fun getDataFromRemote() {
        homeViewModel.getAllCountries()

        homeViewModel.allCountriesResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                showRecycler(it.data?.toList())
            }
        })
    }

    private fun initListWithDb() {
        homeViewModel.getAllCountriesDb()
        homeViewModel.allCountriesDbResult.observe(viewLifecycleOwner, Observer {
            showRecycler(it)
        })
    }

    private fun showRecycler(list: List<CountryEntity>?) {
        list?.let {
            countriesAdapter = CountriesAdapter(
                it,
                this
            )
        }

        binding.recyclerHome.apply {
            adapter = countriesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        hideProgressBar()

    }

    private fun hideProgressBar() {
        binding.homeProgress.visibility = View.GONE
    }

    override fun onItemClick(countryName: String) {

    }

}