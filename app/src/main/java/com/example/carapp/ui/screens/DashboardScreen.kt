package com.example.carapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carapp.data.ExpenseEntity
import com.example.carapp.data.FuelEntryEntity
import com.example.carapp.data.MaintenanceEntity
import com.example.carapp.data.PaperDocumentEntity

@Composable
fun DashboardScreen(
    expenses: List<ExpenseEntity>,
    fuelEntries: List<FuelEntryEntity>,
    maintenanceRecords: List<MaintenanceEntity>,
    paperDocuments: List<PaperDocumentEntity>
) {
    val totalExpense = expenses.sumOf { it.amount }
    val totalFuelCost = fuelEntries.sumOf { it.totalCost }
    val totalMaintenanceCost = maintenanceRecords.sumOf { it.cost }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(text = "Dashboard", style = MaterialTheme.typography.h5)
        }
        item {
            SummaryCard("Expenses", expenses.size, "Total: ${formatCurrency(totalExpense)}")
        }
        item {
            SummaryCard("Fuel", fuelEntries.size, "Total: ${formatCurrency(totalFuelCost)}")
        }
        item {
            SummaryCard("Maintenance", maintenanceRecords.size, "Total: ${formatCurrency(totalMaintenanceCost)}")
        }
        item {
            SummaryCard("Documents", paperDocuments.size, "Saved documents: ${paperDocuments.size}")
        }
    }
}

@Composable
private fun SummaryCard(title: String, count: Int, subtitle: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, style = MaterialTheme.typography.h6)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Entries: $count", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.body2)
        }
    }
}

private fun formatCurrency(value: Double): String = "\$${"%.2f".format(value)}"
