package com.example.myapplication.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.core.utils.setOnSingleClickListener
import com.example.myapplication.databinding.ItemCountryRowBinding
import com.example.myapplication.features.country.CountryEntity

class CountriesAdapter(
    var countries: List<CountryEntity>,
    private val onCountryClickListener: OnCountryClickListener
) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountryRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.country = countries[position]
    }

    override fun getItemCount() = countries.size

    inner class ViewHolder(val binding: ItemCountryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.countryName.setOnSingleClickListener {
                onCountryClickListener.onItemClick(countries[layoutPosition].country)
            }

            binding.countryFlag.setOnSingleClickListener {
                onCountryClickListener.onItemClick(countries[layoutPosition].country)
            }

        }

    }

}
