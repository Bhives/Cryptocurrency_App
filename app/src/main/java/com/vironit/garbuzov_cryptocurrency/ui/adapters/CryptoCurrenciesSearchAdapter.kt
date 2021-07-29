package com.vironit.garbuzov_cryptocurrency.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import com.vironit.garbuzov_cryptocurrency.databinding.CryptoCurrencyCardBinding

class CryptoCurrenciesSearchAdapter :
    PagingDataAdapter<CryptoCurrency, CryptoCurrenciesSearchAdapter.CryptoCurrenciesSearchHolder>(
        CRYPTO_CURRENCY_COMPARATOR
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoCurrenciesSearchHolder {
        val binding =
            CryptoCurrencyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoCurrenciesSearchHolder(binding)
    }

    override fun onBindViewHolder(searchHolder: CryptoCurrenciesSearchHolder, position: Int) {
        val currentCryptoCurrency = getItem(position)
        if (currentCryptoCurrency != null) {
            searchHolder.bindCryptoCurrency(currentCryptoCurrency)
        }
    }

    inner class CryptoCurrenciesSearchHolder(private val binding: CryptoCurrencyCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                }
            }
        }

        fun bindCryptoCurrency(cryptoCurrency: CryptoCurrency) {
            binding.apply {
                Glide.with(itemView)
                    .load(cryptoCurrency.symbol)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(symbolImageView)
                nameTextView.text = cryptoCurrency.name
                priceTextView.text = cryptoCurrency.quote.getValue("USD").price.toString()
                marketCapTextView.text = cryptoCurrency.quote.getValue("USD").marketCap.toString()
                volume24HTextView.text = cryptoCurrency.quote.getValue("USD").volume24H.toString()
                percentChange1HTextView.text = cryptoCurrency.quote.getValue("USD").percentChange1H.toString()
                percentChange24HTextView.text = cryptoCurrency.quote.getValue("USD").percentChange24H.toString()
                percentChange7DTextView.text = cryptoCurrency.quote.getValue("USD").percentChange7D.toString()
            }
        }
    }

    companion object {
        private val CRYPTO_CURRENCY_COMPARATOR = object : DiffUtil.ItemCallback<CryptoCurrency>() {
            override fun areItemsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency) =
                oldItem == newItem
        }
    }
}