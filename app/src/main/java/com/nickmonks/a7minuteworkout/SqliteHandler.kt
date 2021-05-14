package com.nickmonks.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.prefs.PreferencesFactory

class SqliteHandler(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "SevenMinuteWorkout.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " +
                TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_COMPLETED_DATE + " TEXT)")

        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS"+ TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date: String){
        // Store the value we want to add in ContentValues
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)

        // retrieve the database of the class
        val db = this.writableDatabase

        // insert values
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    // read from database
    fun getAllCompletedDatesList() : ArrayList<String>{
        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        while (cursor.moveToNext()){
            // get the string date from every cursor position
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            list.add(dateValue)
        }

        cursor.close()
        return list
    }
}