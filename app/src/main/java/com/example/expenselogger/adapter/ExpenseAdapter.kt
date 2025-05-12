package com.example.expenselogger.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expenselogger.database.Expense
import com.example.expenselogger.databinding.ExpensescardBinding
import com.example.expenselogger.databinding.ExpensesupdatecardBinding
import com.example.expenselogger.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExpenseAdapter(private val context: Context,private val expenseViewModel: ExpenseViewModel):RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ExpensescardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ExpensescardBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ExpenseArr.currentList.size
    }
    private val differcallback = object : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }


    }
    val ExpenseArr = AsyncListDiffer(this, differcallback)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = ExpenseArr.currentList[position]
        holder.binding.itemshowtitle.text = item.title
        holder.binding.itemshowdate.text = item.date
        holder.binding.itemshowamount.text = "Rs." + item.amount.toString()
         holder.binding.deletebtn.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Do You Really Want to Delete Expense?")
                .setPositiveButton("Yes"){dialog,which->
                    expenseViewModel.delete(item)
                }
                .setNegativeButton("No",){dialog,which->
                    dialog.dismiss()
                }
                .show()


         }

        holder.itemView.setOnClickListener {
              val binding= ExpensesupdatecardBinding.inflate(LayoutInflater.from(context))
            val dialog= Dialog(context)
            dialog.setContentView(binding.root)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            binding.enttitle.setText(item.title)
            binding.entAmount.setText(item.amount.toString())
            val dategot= item.date
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = sdf.parse(dategot) // now it's a Date object
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            binding.datepicker.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), // month is 0-indexed
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            binding.submitbtn.setOnClickListener {
                val title = binding.enttitle.text.toString()
                val amount = binding.entAmount.text.toString()
                val datePicker = binding.datepicker

                val year = datePicker.year
                val month = datePicker.month + 1
                val day = datePicker.dayOfMonth
                val selectedDate = "$day-$month-$year"

                if (title.isNotEmpty() && amount.isNotEmpty()) {
                    expenseViewModel.update(
                        Expense(item.id, title, amount.toDouble(),selectedDate)
                    )
                    Toast.makeText(context, "Expense Updated Successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            binding.closebtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()


        }

    }


}