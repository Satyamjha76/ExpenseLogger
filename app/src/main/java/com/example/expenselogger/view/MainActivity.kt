package com.example.expenselogger.view

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expenselogger.R
import com.example.expenselogger.adapter.ExpenseAdapter
import com.example.expenselogger.database.Expense
import com.example.expenselogger.databinding.ActivityMainBinding
import com.example.expenselogger.databinding.ExpenseaddcardBinding
import com.example.expenselogger.respository.ExpenseRepository
import com.example.expenselogger.viewmodel.ExpenseViewModel
import java.lang.Double
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var expenseAdapter: ExpenseAdapter
    private var totalexpenses: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        expenseAdapter = ExpenseAdapter(this,expenseViewModel)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = expenseAdapter
        expenseViewModel.allexpenses.observe(this) { expenses ->
            expenseAdapter.ExpenseArr.submitList(expenses)
            totalexpenses = expenses.size
        }

        binding.addexpensebtn.setOnClickListener {
            addExpenseDialog()
        }
    }

    fun addExpenseDialog() {
        val expenseaddcard = ExpenseaddcardBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(expenseaddcard.root)

        val inset= InsetDrawable(ColorDrawable(Color.TRANSPARENT),50)
        dialog.window?.setBackgroundDrawable(inset)

        expenseaddcard.closebtn.setOnClickListener {
            dialog.dismiss()
        }

        expenseaddcard.submitbtn.setOnClickListener {
            val title = expenseaddcard.enttitle.text.toString()
            val amount = expenseaddcard.entAmount.text.toString()
            val datePicker = expenseaddcard.datepicker

            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth
            val selectedDate = "$day-$month-$year"

            if (title.isNotEmpty() && amount.isNotEmpty()) {
                expenseViewModel.insert(
                    Expense(0, title, amount.toDouble(),selectedDate)
                )
                Toast.makeText(this, "Expense Entered Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
