package com.nickmonks.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import kotlinx.android.synthetic.main.activity_finish.*
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView : String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(toolbar_bmi_activity)
        val actionbar = supportActionBar
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true) // set back button
            actionbar.title = "CALCULATE YOUR BMI"
        }

        // If we press the back button...
        toolbar_bmi_activity.setNavigationOnClickListener{
            onBackPressed()
        }

        // button for BMI calculation
        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNITS_VIEW)){
                if (validateMetricUnits()){
                    val heightValue : Float = etMetricUnitHeight.text.toString().toFloat() /100 //m
                    val weightValue : Float = etMetricUnitWeight.text.toString().toFloat() //kg

                    val bmi = weightValue / (heightValue.pow(2))
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values", Toast.LENGTH_LONG).show()
                }
            }else{
                if (validateUsUnits()){
                    val usHeightValueFeet : String = etUSUnitHeightFeet.text.toString()
                    val usHeightValueInch : String = etUSUnitHeightInch.text.toString()
                    val usWeightValue : Float = etUSUnitWeight.text.toString().toFloat()

                    val heightValue = usHeightValueInch.toFloat() + usHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usWeightValue/(heightValue.pow(2)))
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values", Toast.LENGTH_LONG).show()
                }
            }

        }

        // On creation, we will have BY DEFAULT Metric system:
        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener{ group, checkedId ->
            if (checkedId == R.id.rbMetricUnits)
                makeVisibleMetricUnitsView()
            else
                makeVisibleUsUnitsView()
        }



    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        // Clear up the edit text layout
        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        // Make invisble US system:
        //IMPORTANT: WE Use gone; if we used invisibl the space that it occupies will still be fill
        tilUSUnitWeight.visibility = View.GONE
        llUsUnitsHeight.visibility = View.GONE //just make the Linear layout invisible

        llDiplayBMIResult.visibility = View.GONE


    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        // Clear up the edit text layout
        etUSUnitHeightFeet.text!!.clear()
        etUSUnitHeightInch.text!!.clear()
        etUSUnitWeight.text!!.clear()

        // Make invisble US system:
        tilUSUnitWeight.visibility = View.VISIBLE
        llUsUnitsHeight.visibility = View.VISIBLE //just make the Linear layout invisible

        llDiplayBMIResult.visibility = View.GONE


    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDiplayBMIResult.visibility = View.VISIBLE

//        tvYourBMI.visibility = View.VISIBLE
//        tvBMIValue.visibility = View.VISIBLE
//        tvBMIType.visibility = View.VISIBLE
//        tvBMIDescription.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        // If any of the two fields are empty return false
        when {
            etMetricUnitWeight.text.toString().isEmpty() -> isValid = false
            etMetricUnitHeight.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        // If any of the two fields are empty return false
        when {
            etUSUnitHeightFeet.text.toString().isEmpty() -> isValid = false
            etUSUnitHeightInch.text.toString().isEmpty() -> isValid = false
            etUSUnitWeight.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }
}