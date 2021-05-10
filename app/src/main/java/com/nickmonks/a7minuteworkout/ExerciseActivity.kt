package com.nickmonks.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        // In case the user press the back button, to avoid going back
        toolbar_exercise_activity.setNavigationOnClickListener{
            onBackPressed()
        }

        // Set up the timer
        setupRestView()
    }

    override fun onDestroy() {
        // called whether the activity is destroyed or not on pause

        if (restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        super.onDestroy()

    }

    // Set the progress Bar during REST
    private fun setRestProgressBar() {
        progressBar.progress = restProgress
        // the timer is an anonymous object class, which is declared as shown in kotlin
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                // update the progress bar
                restProgress++
                progressBar.progress = 10 - restProgress
                // update the textview
                tvTimer.text = (10- restProgress).toString()
            }

            override fun onFinish() {
                // What is executed once the timer is over
                setupExerciseView()
            }

        }.start()
    }

    // Set the progress Bar during exercise
    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress
        // the timer is an anonymous object class, which is declared as shown in kotlin
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                // update the progress bar
                exerciseProgress++
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress
                // update the textview
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                // What is executed once the timer is over
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here we will start next rest timer",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }.start()
    }

    private fun setupRestView(){
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    private fun setupExerciseView(){

        // set visibility of rest timer:
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
    }
}