package com.example.expenselogger.respository

import com.example.expenselogger.database.Expense
import com.example.expenselogger.database.ExpenseDao

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun insertexpense(expense: Expense)= expenseDao.insert(expense)
    suspend fun updateexpense(expense: Expense)=expenseDao.update(expense)
    suspend fun deleteexpense(expense: Expense)=expenseDao.delete(expense)

  fun getAllExpenses()=expenseDao.getAllExpenses()
  fun getTotalAmount()=expenseDao.getTotalAmount()
}