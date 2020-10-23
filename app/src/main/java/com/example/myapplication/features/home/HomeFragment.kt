package com.example.myapplication.features.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.core.utils.setOnSingleClickListener
import com.example.myapplication.countryRow
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.features.country.CountryEntity
import com.example.nattramn.core.resource.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var countries: List<CountryEntity>

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
        filterFeedArticles()

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

        homeViewModel.allInfoResult.observe(viewLifecycleOwner, {
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
        homeViewModel.allInfoDbResult.observe(viewLifecycleOwner, {
            binding.info = it
        })
    }

    private fun getCountriesFromRemote() {
        homeViewModel.getAllCountries()

        homeViewModel.allCountriesResult.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS) {

                it.data?.let { all ->
                    countries = all.toList()
                }

                showRecycler(it.data?.toList())
            }
            hideSwipeRefreshProgress()
        })
    }

    private fun initCountriesWithDb() {
        homeViewModel.getAllCountriesDb()
        homeViewModel.allCountriesDbResult.observe(viewLifecycleOwner, {
            countries = it
            showRecycler(it)
        })
    }

    private fun showRecycler(list: List<CountryEntity>?) {

        recyclerHome.withModels {
            list?.forEachIndexed { _, country ->
                countryRow {
                    id(country.population)
                    country(country)
                    onClickListener { _ ->
                        country.countryInfo?.countryName?.let { countryArg ->
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToCountryFragment(
                                    countryArg
                                )
                            )
                        }
                    }
                }
            }
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

    private fun filterFeedArticles() {
        binding.textInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val target = charSequence.toString()
                if (target.isNotEmpty()) {
                    target[0].toUpperCase()
                }

                val filterResult = countries.filter {
                    it.country.contains(target)
                }
                showRecycler(filterResult)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    override fun onRefresh() {
        getInfoFromRemote()
        getCountriesFromRemote()
    }

}