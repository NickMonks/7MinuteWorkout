package com.nickmonks.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

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

        // Set up the text to speech detector:
        tts = TextToSpeech(this, this)

        // Return the list of all the exercises when OnCreate is called
        // Put the variable here to avoid NullPointerException
        exerciseList = Constants.defaultExerciseList()

        // Set up the timer
        setupRestView()


    }

    override fun onDestroy() {
        // called whether the activity is destroyed or not on pause

        if (restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }


        super.onDestroy()

    }

    // Set the progress Bar during REST
    private fun setRestProgressBar() {
        progressBar.progress = restProgress
        // the timer is an anonymous object class, which is declared as shown in kotlin
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {

                progressBar.progress = 10 - restProgress
                // update the textview
                tvTimer.text = (10- restProgress).toString()

                // update the progress bar
                restProgress++
            }

            override fun onFinish() {
                // set the exercise
                 currentExercisePosition++
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
                if (currentExercisePosition < exerciseList?.size!! -1){
                    setupRestView()
                }else{
                    // When the exercise is finished...
                }
            }

        }.start()
    }

    private fun setupRestView(){

        // when we make it visibie the first time, we need to make it visible again!
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        // Show next exercise name
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition+1].getName()

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

        // Read text to speech from the exercise:
        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {
        // We need to check first if the device has access to text to speech
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "The language is not supported")
        }else{
            Log.e("TTS", "onInit: Iniialization failed!", )
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH, null, "")
    }
}