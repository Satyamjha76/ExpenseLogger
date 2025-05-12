package com.example.expenselogger.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val amount:Double,

    val date:String

)