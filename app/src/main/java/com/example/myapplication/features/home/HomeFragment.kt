package com.example.myapplication.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.core.utils.setOnSingleClickListener
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.features.country.CountryEntity
import com.example.nattramn.core.resource.Status
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), OnCountryClickListener, SwipeRefreshLayout.OnRefreshListener {

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
        binding.swipeLayout.setOnRefreshListener(this)
        setOnAllInfoClick()

        showAllInfo()
        showCountriesList()
    }

    private fun showAllInfo() {
        initInfoWithDatabase()
        getInfoFromRemote()
    }

    private fun showCountriesList() {

        initCountriesWithDb()
        getCountriesFromRemote()

    }

    private fun getInfoFromRemote() {
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
            hideSwipeRefreshProgress()
        })
    }

    private fun hideSwipeRefreshProgress() {
        binding.swipeLayout.isRefreshing = false
    }

    private fun initInfoWithDatabase() {
        homeViewModel.getAllInfoDb()
        homeViewModel.allInfoDbResult.observe(viewLifecycleOwner, Observer {
            binding.info = it
        })
    }

    private fun getCountriesFromRemote() {
        homeViewModel.getAllCountries()

        homeViewModel.allCountriesResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                showRecycler(it.data?.toList())
            }
            hideSwipeRefreshProgress()
        })
    }

    private fun initCountriesWithDb() {
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

    private fun setOnAllInfoClick() {

        binding.allDetails.setOnSingleClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
        }

    }

    private fun hideProgressBar() {
        binding.homeProgress.visibility = View.GONE
    }

    override fun onItemClick(countryName: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCountryFragment(
                countryName
            )
        )
    }

    override fun onRefresh() {
        getInfoFromRemote()
        getCountriesFromRemote()
    }

}