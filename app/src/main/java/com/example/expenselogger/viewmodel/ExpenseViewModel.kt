package com.example.expenselogger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expenselogger.database.Expense
import com.example.expenselogger.database.ExpenseDatabase
import com.example.expenselogger.respository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application):AndroidViewModel(application) {
    private val repository:ExpenseRepository
    val  allexpenses: LiveData<List<Expense>>
    val  totalamount: LiveData<Double>

    init{
        val expensedao= ExpenseDatabase.getDatabase(application).expenseDao()
        repository= ExpenseRepository(expensedao)
        allexpenses=repository.getAllExpenses()
        totalamount=repository.getTotalAmount()
    }
    fun insert(expense: Expense){
        viewModelScope.launch {
            repository.insertexpense(expense)
        }
    }
    fun update(expense: Expense) {
        viewModelScope.launch {
            repository.updateexpense(expense)
        }
    }

    fun delete(expense: Expense) {
        viewModelScope.launch {
            repository.deleteexpense(expense)
        }
    }


}