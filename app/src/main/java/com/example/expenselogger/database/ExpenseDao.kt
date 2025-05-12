package com.example.expenselogger.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface ExpenseDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)


    @Query("Select * from Expenses Order by date desc")
    fun getAllExpenses():LiveData<List<Expense>>

    @Query("Select sum(amount) from Expenses")
    fun getTotalAmount():LiveData<Double>
}