package com.example.cupcake.model

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

// top-level private constant
private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity:LiveData<Int> get() = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavour:LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date:LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    // using Transformations.map() method to format the price to use the local currency format into String
    val price:LiveData<String> = _price.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()


    fun resetOrder(){
        Log.d("Checking","reset")
        _quantity.value = 0
        _date.value = dateOptions[0]
        _price.value = 0.0
        _flavor.value = ""
    }

    init {
        //using the init block to initialize
        // the properties when an instance of OrderViewModel is created.
        Log.d("Checking","INIT")
        resetOrder()
    }

    fun setQuantity(numberOfCupcakes:Int){
        Log.d("Checking","quantity")
        _quantity.value = numberOfCupcakes
        //update the price variable when the quantity is set.
        updatePrice()
    }

    fun setFlavour(desiredFlavor:String){
        _flavor.value =  desiredFlavor
        Log.d("Checking","flavour")
    }

    fun setDate(pickupDate: String) {
        Log.d("Checking","date")
        _date.value = pickupDate
        // checking and updating the price If the user selected the first option (today) for pickup, add the surcharge
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String>{
        val options = mutableListOf<String>()
        // "E MMM d" stands for "Tue July 25"
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        /*
        * repeat this block of code 4 times. This repeat block will format a date,
        * add it to the list of date options, and then increment the calendar by 1 day.
        */
        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE,1)
        }
        Log.d("Checking","getPick")
        return options
    }

    private fun updatePrice(){
    //The elvis operator (?:) means that if the expression on the left is not null,
    // then use it. Otherwise if the expression on the left is null,
    // then use the expression to the right of the elvis operator (which is 0 in this case).
        var calculatedPrice = (_quantity.value ?: 0)* PRICE_PER_CUPCAKE

        // If the user selected the first option (today) for pickup, add the surcharge
        if(dateOptions[0] == _date.value){
            calculatedPrice +=  PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice

    }

}