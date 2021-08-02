package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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
        lifecycleScope.launch {
            convertCryptoCurrency()
        }
    }

    private suspend fun convertCryptoCurrency() {
        cryptoCurrencyInput.addTextChangedListener {
            lifecycleScope.launch {
                with(cryptoCurrencyTypesSpinner.selectedItem) {
                    try {
                        currencyInput.setText(
                            viewModel.convertCryptoCurrency(
                                cryptoCurrencyInput.text.toString().toDouble(),
                                this.toString()
                            ).toString()
                        )
                    } catch (iOException: IOException) {
                        iOException.printStackTrace()
                    } catch (httpException: HttpException) {
                        httpException.printStackTrace()
                    } catch (numberFormatException: NumberFormatException) {
                        numberFormatException.printStackTrace()
                    }
                }
            }
        }
    }
}