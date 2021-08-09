package com.vironit.garbuzov_cryptocurrency.ui.fragments.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentAddNotificationBinding
import com.vironit.garbuzov_cryptocurrency.ui.bindingActivity
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_notification.*
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import kotlin.NoSuchElementException

@AndroidEntryPoint
class AddNotificationFragment :
    BaseFragment<FragmentAddNotificationBinding>(R.layout.fragment_add_notification) {

    override val viewModel by viewModels<NotificationsViewModel>()
    lateinit var currentCryptoCurrency: ConvertedCryptoCurrency

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingActivity.bottomNavigationMenu.isVisible = false
        cryptoCurrencyTypesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    displayCurrencyRate()
                    calculateRisingPercent()
                    calculateRisingPrice()
                    calculateLoweringPercent()
                    calculateLoweringPrice()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
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
                    calculateRisingPercent()
                    calculateRisingPrice()
                    calculateLoweringPercent()
                    calculateLoweringPrice()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        priceHigherEditText.addTextChangedListener {
            if (priceHigherEditText.text.isEmpty()) {
                percentHigherEditText.text.clear()
            }
            calculateRisingPercent()
            priceLowerEditText.text.clear()
            percentLowerEditText.text.clear()
        }

        percentHigherEditText.addTextChangedListener {
            if (percentHigherEditText.text.isEmpty()) {
                priceHigherEditText.text.clear()
            }
            calculateRisingPrice()
            priceLowerEditText.text.clear()
            percentLowerEditText.text.clear()
        }

        priceLowerEditText.addTextChangedListener {
            if (priceLowerEditText.text.isEmpty()) {
                percentLowerEditText.text.clear()
            }
            calculateLoweringPercent()
            priceHigherEditText.text.clear()
            percentHigherEditText.text.clear()
        }

        percentLowerEditText.addTextChangedListener {
            if (percentLowerEditText.text.isEmpty()) {
                priceLowerEditText.text.clear()
            }
            calculateLoweringPrice()
            priceHigherEditText.text.clear()
            percentHigherEditText.text.clear()
        }

        createNotificationButton.setOnClickListener {
            addNotification()
        }
    }

    @SuppressLint("SetTextI18n")
    fun displayCurrencyRate() {
        try {
            with(currencyTypesSpinner.selectedItem.toString()) {
                viewModel.getConvertedCryptoCurrency(
                    1.0,
                    cryptoCurrencyTypesSpinner.selectedItem.toString(),
                    currencyTypesSpinner.selectedItem.toString()
                ).observe(viewLifecycleOwner, {
                    currentCryptoCurrency = it
                    rateValueTextView.text = "1 ${cryptoCurrencyTypesSpinner.selectedItem} = ${
                        String.format(
                            "%.2f",
                            currentCryptoCurrency.quote.getValue(this).price
                        )
                    } $this"
                })
            }
        } catch (iOException: IOException) {
            iOException.printStackTrace()
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
        } catch (noSuchElementException: NoSuchElementException) {
            Toast.makeText(
                context,
                "No rate was found for the $this currency",
                Toast.LENGTH_SHORT
            ).show()
            noSuchElementException.printStackTrace()
        }
    }

    private fun calculateRisingPercent() {
        with(currencyTypesSpinner.selectedItem) {
            try {
                if (priceHigherEditText.text.toString().toDouble() > 0.0) {
                    val currentCryptoCurrencyValue =
                        currentCryptoCurrency.quote.getValue(this.toString()).price
                    val newCryptoCurrencyValue = priceHigherEditText.text.toString().toDouble()
                    if (newCryptoCurrencyValue > currentCryptoCurrencyValue) {
                        percentHigherEditText.setText(
                            String.format(
                                Locale.ENGLISH,
                                "%.2f",
                                (newCryptoCurrencyValue - currentCryptoCurrencyValue) / currentCryptoCurrencyValue * 100
                            )
                        )
                    }
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

    private fun calculateRisingPrice() {
        with(currencyTypesSpinner.selectedItem) {
            try {
                if (percentHigherEditText.text.toString().toDouble() > 0.0) {
                    val currentCryptoCurrencyValue =
                        currentCryptoCurrency.quote.getValue(this.toString()).price
                    val percentRising = percentHigherEditText.text.toString().toDouble()
                    priceHigherEditText.setText(
                        String.format(
                            "%.2f",
                            (currentCryptoCurrencyValue + (currentCryptoCurrencyValue * percentRising / 100))
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

    private fun calculateLoweringPercent() {
        with(currencyTypesSpinner.selectedItem) {
            try {
                if (priceLowerEditText.text.toString().toDouble() > 0.0) {
                    val currentCryptoCurrencyValue =
                        currentCryptoCurrency.quote.getValue(this.toString()).price
                    val newCryptoCurrencyValue = priceLowerEditText.text.toString().toDouble()
                    if (newCryptoCurrencyValue < currentCryptoCurrencyValue) {
                        percentLowerEditText.setText(
                            String.format(
                                Locale.ENGLISH,
                                "%.2f",
                                (currentCryptoCurrencyValue - newCryptoCurrencyValue) / currentCryptoCurrencyValue * 100
                            )
                        )
                    }
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

    private fun calculateLoweringPrice() {
        with(currencyTypesSpinner.selectedItem) {
            try {
                if (percentLowerEditText.text.toString().toDouble() > 0.0) {
                    val currentCryptoCurrencyValue =
                        currentCryptoCurrency.quote.getValue(this.toString()).price
                    val percentLowering = percentLowerEditText.text.toString().toDouble()
                    priceLowerEditText.setText(
                        String.format(
                            "%.2f",
                            (currentCryptoCurrencyValue - (currentCryptoCurrencyValue * percentLowering / 100))
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

    @SuppressLint("NewApi")
    fun addNotification() {
        when {
            percentHigherEditText.text.isNullOrEmpty() && percentLowerEditText.text.isNullOrEmpty() -> {
                percentHigherEditText.highlightColor = requireContext().getColor(R.color.error_red)
                percentLowerEditText.highlightColor = requireContext().getColor(R.color.error_red)
            }
            !percentHigherEditText.text.isNullOrEmpty() -> {
                viewModel.createNotification(
                    requireContext(),
                    "${currentCryptoCurrency.symbol}, +${percentHigherEditText.text}%",
                    percentHigherEditText.text.toString().toDouble(),
                    currentCryptoCurrency.symbol.toString(),
                    currencyTypesSpinner.selectedItem.toString(),
                    false,
                    1
                )
                findNavController().navigate(AddNotificationFragmentDirections.actionAddNotificationFragmentToNotificationsFragment())
            }
            !percentLowerEditText.text.isNullOrEmpty() -> {
                viewModel.createNotification(
                    requireContext(),
                    "${currentCryptoCurrency.symbol}, +${percentLowerEditText.text}%",
                    percentLowerEditText.text.toString().toDouble(),
                    currentCryptoCurrency.symbol.toString(),
                    currencyTypesSpinner.selectedItem.toString(),
                    false,
                    0
                )
                findNavController().navigate(AddNotificationFragmentDirections.actionAddNotificationFragmentToNotificationsFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingActivity.bottomNavigationMenu.isVisible = true
    }
}