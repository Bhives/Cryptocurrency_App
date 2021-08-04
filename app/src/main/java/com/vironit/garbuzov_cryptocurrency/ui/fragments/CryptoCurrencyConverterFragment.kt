package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import com.vironit.garbuzov_cryptocurrency.databinding.CryptoCurrencyCardBinding
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.CryptoCurrencyConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_currency_converter.*
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class CryptoCurrencyConverterFragment :
    BaseFragment<CryptoCurrencyCardBinding>(R.layout.fragment_crypto_currency_converter) {

    override val viewModel by viewModels<CryptoCurrencyConverterViewModel>()
    lateinit var currentCryptoCurrency: ConvertedCryptoCurrency
    var layoutPositionFlag = 0

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayCurrencyRate()
        cryptoCurrencyInput.addTextChangedListener {
            if (!currencyInput.hasFocus()) {
                if (cryptoCurrencyInput.text.isEmpty()) {
                    currencyInput.text.clear()
                }
                convertFromCryptoCurrency()
            }
        }
        cryptoCurrencyTypesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    displayCurrencyRate()
                        convertFromCryptoCurrency()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        currencyInput.addTextChangedListener {
            if (!cryptoCurrencyInput.hasFocus()) {
                if (currencyInput.text.isEmpty()) {
                    cryptoCurrencyInput.text.clear()
                }
                convertToCryptoCurrency()
            }
        }
        currencyTypesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                        convertToCryptoCurrency()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        swapCurrenciesButton.setOnClickListener {
            swapCurrencies()
        }
    }

    @SuppressLint("SetTextI18n")
    fun displayCurrencyRate() {
        try {
            with(cryptoCurrencyTypesSpinner.selectedItem.toString()) {
                viewModel.getConvertedCryptoCurrency(
                    1.0,
                    this
                ).observe(viewLifecycleOwner, {
                    currentCryptoCurrency = it
                    rateValueTextView.text = "1 $this = ${
                        String.format(
                            "%.2f", currentCryptoCurrency.quote.getValue("USD").price
                        )
                    }$"
                    rateDateTextView.text =
                        "${requireContext().resources.getString(R.string.rate_date)} ${
                            currentCryptoCurrency.quote.getValue(
                                "USD"
                            ).lastUpdated
                        }"
                })
            }
        } catch (iOException: IOException) {
            iOException.printStackTrace()
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
        }
    }

    private fun convertFromCryptoCurrency() {
        with(currencyTypesSpinner.selectedItem) {
            try {
                if (cryptoCurrencyInput.text.toString().toDouble() > 0.0) {
                    currencyInput.setText(
                        String.format(
                            "%.2f",
                            currentCryptoCurrency.quote[this.toString()]?.price!! * cryptoCurrencyInput.text.toString()
                                .toDouble()
                        )
                    )
                }
            } catch (numberFormatException: NumberFormatException) {
                numberFormatException.printStackTrace()
            } catch (noSuchElementException: NoSuchElementException) {
                Toast.makeText(
                    context,
                    "No rate was found for the $this currency",
                    Toast.LENGTH_SHORT
                ).show()
                noSuchElementException.printStackTrace()
            }
        }
    }

    private fun convertToCryptoCurrency() {
        with(currencyTypesSpinner.selectedItem) {
            try {
                cryptoCurrencyInput.setText(
                    String.format(
                        "%.2f",
                        (currencyInput.text.toString()
                            .toDouble() / currentCryptoCurrency.quote[this.toString()]?.price!!)
                    )
                )
            } catch (numberFormatException: NumberFormatException) {
                numberFormatException.printStackTrace()
            } catch (noSuchElementException: NoSuchElementException) {
                Toast.makeText(
                    context,
                    "No rate was found for the $this currency",
                    Toast.LENGTH_SHORT
                ).show()
                noSuchElementException.printStackTrace()
            }
        }
    }

    private fun swapCurrencies() {
        val fromLayoutY = fromLayout.y
        val toLayoutY = toLayout.y
        val cryptoCurrencyText = cryptoCurrencyInput.text
        val currencyText = currencyInput.text
        fromLayout.y = toLayoutY
        toLayout.y = fromLayoutY
        cryptoCurrencyInput.text = currencyText
        currencyInput.text = cryptoCurrencyText
        when (layoutPositionFlag) {
            0 -> {
                convertToCryptoCurrency()
                layoutPositionFlag = 1
            }
            1 -> {
                convertFromCryptoCurrency()
                layoutPositionFlag = 0
            }
        }
    }
}