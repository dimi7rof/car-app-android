package com.example.carapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carapp.data.ExpenseEntity
import com.example.carapp.ui.components.AddExpenseDialog

@Composable
fun ExpensesScreen(expenses: List<ExpenseEntity>, onAddExpense: (String, String, String) -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        AddExpenseDialog(onDismiss = { showDialog = false }, onSave = { description, amount, date ->
            onAddExpense(description, amount, date)
            showDialog = false
        })
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Expenses", style = MaterialTheme.typography.h5)
            Button(onClick = { showDialog = true }) {
                Text(text = "Add")
            }
        }

        if (expenses.isEmpty()) {
            Text(text = "No expenses yet. Add one to get started.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(expenses) { expense ->
                    ExpenseRow(expense)
                }
            }
        }
    }
}

@Composable
private fun ExpenseRow(expense: ExpenseEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = expense.description, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Amount: $${"%.2f".format(expense.amount)}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Date: ${expense.date}", style = MaterialTheme.typography.body2)
        }
    }
}
