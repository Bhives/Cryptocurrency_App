package com.vironit.garbuzov_cryptocurrency.ui.fragments.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
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

@AndroidEntryPoint
class AddNotificationFragment :
    BaseFragment<FragmentAddNotificationBinding>(R.layout.fragment_add_notification) {

    override val viewModel by viewModels<NotificationsViewModel>()
    lateinit var currentCryptoCurrency: ConvertedCryptoCurrency

    @SuppressLint("InlinedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingActivity.bottomNavigationMenu.isVisible = false
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
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
        priceHigherEditText.addTextChangedListener()
        {
            if (priceHigherEditText.text.isEmpty()) {
                percentHigherEditText.text.clear()
            }
            calculateRisingPercent()
            priceLowerEditText.text.clear()
            percentLowerEditText.text.clear()
        }
        percentHigherEditText.addTextChangedListener()
        {
            if (percentHigherEditText.text.isEmpty()) {
                priceHigherEditText.text.clear()
            }
            calculateRisingPrice()
            priceLowerEditText.text.clear()
            percentLowerEditText.text.clear()
        }
        priceLowerEditText.addTextChangedListener()
        {
            if (priceLowerEditText.text.isEmpty()) {
                percentLowerEditText.text.clear()
            }
            calculateLoweringPercent()
            priceHigherEditText.text.clear()
            percentHigherEditText.text.clear()
        }
        percentLowerEditText.addTextChangedListener()
        {
            if (percentLowerEditText.text.isEmpty()) {
                priceLowerEditText.text.clear()
            }
            calculateLoweringPrice()
            priceHigherEditText.text.clear()
            percentHigherEditText.text.clear()
        }
        createNotificationButton.setOnClickListener()
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                ),
                100
            )
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
            noSuchElementException.printStackTrace()
        } catch (nullPointerException: NullPointerException) {
            Toast.makeText(
                context,
                "No rate was found for the $this currency",
                Toast.LENGTH_SHORT
            ).show()
            nullPointerException.printStackTrace()
        }
    }

    private fun calculateRisingPercent() {
        try {
            if (priceHigherEditText.text.toString().toDouble() > 0.0) {
                val currentCryptoCurrencyValue =
                    currentCryptoCurrency.quote.getValue(currencyTypesSpinner.selectedItem.toString()).price
                val newCryptoCurrencyValue = priceHigherEditText.text.toString().toDouble()
                if (newCryptoCurrencyValue > currentCryptoCurrencyValue) {
                    percentHigherEditText.setText(
                        String.format(
                            "%.2f",
                            (newCryptoCurrencyValue - currentCryptoCurrencyValue) / currentCryptoCurrencyValue * 100
                        )
                    )
                }
            }
        } catch (numberFormatException: NumberFormatException) {
            numberFormatException.printStackTrace()
        } catch (noSuchElementException: NoSuchElementException) {
            noSuchElementException.printStackTrace()
        }
    }

    private fun calculateRisingPrice() {
        try {
            if (percentHigherEditText.text.toString().toDouble() > 0.0) {
                val currentCryptoCurrencyValue =
                    currentCryptoCurrency.quote.getValue(currencyTypesSpinner.selectedItem.toString()).price
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
            noSuchElementException.printStackTrace()
        } catch (nullPointerException: NullPointerException) {
            Toast.makeText(
                context,
                "No rate was found for the $this currency",
                Toast.LENGTH_SHORT
            ).show()
            nullPointerException.printStackTrace()
        }
    }

    private fun calculateLoweringPercent() {
        try {
            if (priceLowerEditText.text.toString().toDouble() > 0.0) {
                val currentCryptoCurrencyValue =
                    currentCryptoCurrency.quote.getValue(currencyTypesSpinner.selectedItem.toString()).price
                val newCryptoCurrencyValue = priceLowerEditText.text.toString().toDouble()
                if (newCryptoCurrencyValue < currentCryptoCurrencyValue) {
                    percentLowerEditText.setText(
                        String.format(
                            "%.2f",
                            (currentCryptoCurrencyValue - newCryptoCurrencyValue) / currentCryptoCurrencyValue * 100
                        )
                    )
                }
            }
        } catch (numberFormatException: NumberFormatException) {
            numberFormatException.printStackTrace()
        } catch (noSuchElementException: NoSuchElementException) {
            noSuchElementException.printStackTrace()
        } catch (nullPointerException: NullPointerException) {
            Toast.makeText(
                context,
                "No rate was found for the $this currency",
                Toast.LENGTH_SHORT
            ).show()
            nullPointerException.printStackTrace()
        }
    }

    private fun calculateLoweringPrice() {
        try {
            if (percentLowerEditText.text.toString().toDouble() > 0.0) {
                val currentCryptoCurrencyValue =
                    currentCryptoCurrency.quote.getValue(currencyTypesSpinner.selectedItem.toString()).price
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
            noSuchElementException.printStackTrace()
        } catch (nullPointerException: NullPointerException) {
            Toast.makeText(
                context,
                "No rate was found for the $this currency",
                Toast.LENGTH_SHORT
            ).show()
            nullPointerException.printStackTrace()
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
                val percentHigher = String.format(
                    Locale.ENGLISH,
                    "%.2f",
                    percentHigherEditText.text
                ).toDouble()
                viewModel.createNotification(
                    requireContext(),
                    "${currentCryptoCurrency.symbol}, +$percentHigher%",
                    percentHigher,
                    currentCryptoCurrency.symbol.toString(),
                    currencyTypesSpinner.selectedItem.toString(),
                    false,
                    1
                )
                findNavController().navigate(AddNotificationFragmentDirections.actionAddNotificationFragmentToNotificationsFragment())
            }
            !percentLowerEditText.text.isNullOrEmpty() -> {
                val percentLower = String.format(
                    Locale.ENGLISH,
                    "%.2f",
                    percentLowerEditText.text
                ).toDouble()
                viewModel.createNotification(
                    requireContext(),
                    "${currentCryptoCurrency.symbol}, +$percentLower%",
                    percentLower,
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