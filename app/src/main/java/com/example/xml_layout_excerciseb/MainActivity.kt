package com.example.xml_layout_excerciseb


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import com.example.xml_layout_excerciseb.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import java.lang.NumberFormatException
import java.lang.StringBuilder
import kotlin.math.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var calculateTemperatureB: com.google.android.material.button.MaterialButton
    private lateinit var temperatureRadioGroup: RadioGroup
    private lateinit var roundUpSwitchB: SwitchMaterial
    private lateinit var temperatureDisplay : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calculateTemperatureB = binding.CalculateTemperature
        temperatureRadioGroup = binding.RadioGroupContainer
        roundUpSwitchB = binding.RoundUp
        temperatureDisplay = binding.TemperatureDisplay


        calculateTemperatureB.setOnClickListener()
        {
            calculateTemperature()
        }

    }

 /*   override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
    */

    private fun calculateTemperature() {
            val temperatureString : String?
            val roundUp : Boolean = roundUpSwitchB.isChecked
            var temperature : Double? = 0.0
            var selectedTemperatureConversion : Double? = 0.0
        try {
            temperatureString = binding.temperatureInputEditText.text.toString()
            temperature = temperatureString.toDouble()
        }catch (e : NumberFormatException){
                Log.w("Exception", e.message!!)
        }
        selectedTemperatureConversion = when (temperatureRadioGroup.checkedRadioButtonId) {
            //Problem solved on 08-06-2023 1:38
            /*
             *** Code that was written ***
             R.id.fahrenheit ->32.0
             R.id.kevlin -> 273.15
             else -> 491.67
             *
             * This code would give me issues with trying to calculation this instruction, after it
             * had executed the code before it.
             * var convertedTemperature : Double = temperature * selectedTemperatureConversion
             *
             * Essentially the reason compiler doesn't allow me to use the selectedTemperatureConversion variable
             * is because kotlin is a strongly null type safety programming language. The compiler will not allow me
             * to read that variable because I do not write code before that read, that it is possible for that value to be
             * null. Soooooo.....
             *
             * In the when statement I have to assign all possible values to my possibly null variable,
             * that actually just meaning that I just need to assign it as null in one condition.
             */
            //Solution
            R.id.fahrenheit -> 32.0
            R.id.kelvin -> 273.15
            R.id.rankine-> 491.67
           else->null
        }

        var convertedTemperature : Double? = 0.0
        if (temperatureRadioGroup.checkedRadioButtonId == R.id.fahrenheit)
        {
            convertedTemperature = temperature!! * 9/5
            convertedTemperature += selectedTemperatureConversion!!
        }else if (temperatureRadioGroup.checkedRadioButtonId == R.id.kelvin)
        {
            convertedTemperature =  temperature!! + selectedTemperatureConversion!!

        }else if (temperatureRadioGroup.checkedRadioButtonId == R.id.rankine)
        {
            convertedTemperature = temperature!! * 9/5 + selectedTemperatureConversion!!
        }
        displayCalculation(convertedTemperature!!,roundUp)
    }

    private fun displayCalculation(temperature_: Double, round_: Boolean)
    {
        var temperature = temperature_
        if (round_) {
            temperature = ceil(temperature)
        }
        var temperatureStringValue : String = temperature.toString()
        val fSymbol : String = getString(R.string.fahrenheit_Symbol)
        val kSymbol : String = getString(R.string.kevlin_Symbol)
        val rSymbol : String = getString(R.string.rankine_Symbol)
        if (temperatureRadioGroup.checkedRadioButtonId == R.id.fahrenheit) {
            temperatureStringValue = StringBuilder().append(temperatureStringValue).append(fSymbol).toString()
        } else if (temperatureRadioGroup.checkedRadioButtonId == R.id.kelvin){
            temperatureStringValue = StringBuilder().append(temperatureStringValue).append(kSymbol).toString()
        } else if (temperatureRadioGroup.checkedRadioButtonId == R.id.rankine){
            temperatureStringValue = StringBuilder().append(temperatureStringValue).append(rSymbol).toString()
        }
        temperatureDisplay.text=temperatureStringValue
    }
}