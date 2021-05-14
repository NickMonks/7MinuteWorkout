package com.nickmonks.a7minuteworkout

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)
        val actionbar = supportActionBar
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true) // set back button
        }

        // If we press the back button...
        toolbar_finish_activity.setNavigationOnClickListener{
           onBackPressed()
        }

        btnFinish.setOnClickListener{
            finish()
        }

        // When the exercise is finished, the adding to the database will happen inmideately after
        // the creation of this activity:
        addDateToDatabase()

    }

    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        // FORMAT THE DATE
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        // create a db handler
        val dbHandler = SqliteHandler(this, null)
        dbHandler.addDate(date)
    }


}