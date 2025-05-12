package com.example.expenselogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Expense::class], version = 1)
abstract class ExpenseDatabase:RoomDatabase() {

abstract fun expenseDao():ExpenseDao

companion object{
    @Volatile
    private var Instance:ExpenseDatabase?=null
    //Made for Making only one instance of Database
    //Function to createDatabase
    fun getDatabase(context: Context):ExpenseDatabase{
        //if the Instance is already present in Instance variaable then that instance will be returned else synchrinized code will run
        return Instance ?: synchronized(this){
            //this block of code will run only by one thread to create the instalnce of room database
          val instance = Room.databaseBuilder(context.applicationContext,ExpenseDatabase::class.java,"ExpenseLogger").build()
        Instance=instance
           instance
        }
    }
}
}