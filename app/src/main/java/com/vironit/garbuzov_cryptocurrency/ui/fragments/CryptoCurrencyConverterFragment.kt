package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.CryptoCurrencyCardBinding
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.CryptoCurrencyConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_currency_converter.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class CryptoCurrencyConverterFragment :
    BaseFragment<CryptoCurrencyCardBinding>(R.layout.fragment_crypto_currency_converter) {

    override val viewModel by viewModels<CryptoCurrencyConverterViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rateDateTextView.text = rateDateTextView.text.toString() + viewModel.getCurrentDate()
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
                    if (!currencyInput.hasFocus()) {
                        convertFromCryptoCurrency()
                    }
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
                    displayCurrencyRate()
                    if (!cryptoCurrencyInput.hasFocus()) {
                        convertToCryptoCurrency()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    @SuppressLint("SetTextI18n")
    fun displayCurrencyRate() {
        lifecycleScope.launch {
            with(cryptoCurrencyTypesSpinner.selectedItem) {
                rateValueTextView.text = "1 $this = ${
                    String.format(
                        "%.2f", viewModel.convertCryptoCurrency(
                            1.0,
                            this.toString(),
                            "USD"
                        )
                    )
                }$"
            }
        }
    }

    private fun convertFromCryptoCurrency() {
        lifecycleScope.launch {
            with(cryptoCurrencyTypesSpinner.selectedItem) {
                try {
                    currencyInput.setText(
                        String.format(
                            "%.2f",
                            viewModel.convertCryptoCurrency(
                                cryptoCurrencyInput.text.toString().toDouble(),
                                this.toString(),
                                currencyTypesSpinner.selectedItem.toString()
                            )
                        )
                    )
                } catch (iOException: IOException) {
                    iOException.printStackTrace()
                } catch (httpException: HttpException) {
                    httpException.printStackTrace()
                } catch (numberFormatException: NumberFormatException) {
                    numberFormatException.printStackTrace()
                } catch (noSuchElementException: NoSuchElementException) {
                    Toast.makeText(
                        context,
                        "No rate was found for the ${currencyTypesSpinner.selectedItem} currency",
                        Toast.LENGTH_SHORT
                    ).show()
                    noSuchElementException.printStackTrace()
                }
            }
        }
    }

    private fun convertToCryptoCurrency() {
        lifecycleScope.launch {
            with(currencyTypesSpinner.selectedItem) {
                try {
                    cryptoCurrencyInput.setText(
                        String.format(
                            "%.2f",
                            (currencyInput.text.toString()
                                .toDouble() / viewModel.convertCryptoCurrency(
                                1.0,
                                cryptoCurrencyTypesSpinner.selectedItem.toString(),
                                this.toString()
                            ))
                        )
                    )
                } catch (iOException: IOException) {
                    iOException.printStackTrace()
                } catch (httpException: HttpException) {
                    httpException.printStackTrace()
                } catch (numberFormatException: NumberFormatException) {
                    numberFormatException.printStackTrace()
                } catch (noSuchElementException: NoSuchElementException) {
                    Toast.makeText(
                        context,
                        "No rate was found for the ${currencyTypesSpinner.selectedItem} currency",
                        Toast.LENGTH_SHORT
                    ).show()
                    noSuchElementException.printStackTrace()
                }
            }
        }
    }
}