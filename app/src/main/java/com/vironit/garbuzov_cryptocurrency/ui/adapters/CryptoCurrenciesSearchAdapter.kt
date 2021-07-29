package com.vironit.garbuzov_cryptocurrency.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import com.vironit.garbuzov_cryptocurrency.databinding.CryptoCurrencyCardBinding
import kotlin.math.roundToInt

class CryptoCurrenciesSearchAdapter :
    PagingDataAdapter<CryptoCurrency, CryptoCurrenciesSearchAdapter.CryptoCurrenciesSearchHolder>(
        CRYPTO_CURRENCY_COMPARATOR
    ) {

    val imageUrl = "https://res.cloudinary.com/dxi90ksom/image/upload/"

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

        @SuppressLint("SetTextI18n")
        fun bindCryptoCurrency(cryptoCurrency: CryptoCurrency) {
            binding.apply {
                Glide.with(itemView)
                    .load(imageUrl + cryptoCurrency.symbol.toString().toLowerCase() + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(symbolImageView)
                nameTextView.text = cryptoCurrency.name + " (" + cryptoCurrency.symbol + ")"
                priceTextView.text = "Price: $" + String.format(
                    "%,f",
                    cryptoCurrency.quote.getValue("USD").price
                )
                marketCapTextView.text = "Market Cap: $" + String.format(
                    "%,d",
                    cryptoCurrency.quote.getValue("USD").marketCap.roundToInt()
                )
                volume24HTextView.text = "Volume/24h: $" + String.format(
                    "%,d",
                    cryptoCurrency.quote.getValue("USD").volume24H.roundToInt()
                )
                percentChange1HTextView.text = String.format(
                    "%.2f",
                    cryptoCurrency.quote.getValue("USD").percentChange1H
                ) + "%"
                percentChange1HTextView.setTextColor(
                    Color.parseColor(
                        when {
                            cryptoCurrency.quote.getValue("USD").percentChange1H.toString()
                                .contains("-") -> "#ff0000"
                            else -> "#32CD32"
                        }
                    )
                )
                percentChange24HTextView.text = String.format(
                    "%.2f",
                    cryptoCurrency.quote.getValue("USD").percentChange24H
                ) + "%"
                percentChange24HTextView.setTextColor(
                    Color.parseColor(
                        when {
                            cryptoCurrency.quote.getValue("USD").percentChange24H.toString()
                                .contains("-") -> "#ff0000"
                            else -> "#32CD32"
                        }
                    )
                )
                percentChange7DTextView.text = String.format(
                    "%.2f",
                    cryptoCurrency.quote.getValue("USD").percentChange7D
                ) + "%"
                percentChange7DTextView.setTextColor(
                    Color.parseColor(
                        when {
                            cryptoCurrency.quote.getValue("USD").percentChange7D.toString()
                                .contains("-") -> "#ff0000"
                            else -> "#32CD32"
                        }
                    )
                )

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