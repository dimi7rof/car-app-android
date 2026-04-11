package com.example.carapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carapp.data.ExpenseEntity
import com.example.carapp.data.FuelEntryEntity
import com.example.carapp.data.MaintenanceEntity
import com.example.carapp.data.PaperDocumentEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DashboardScreen(
    expenses: List<ExpenseEntity>,
    fuelEntries: List<FuelEntryEntity>,
    maintenanceRecords: List<MaintenanceEntity>,
    paperDocuments: List<PaperDocumentEntity>
) {
    var selectedTimeframe by remember { mutableStateOf(Timeframe.Monthly) }

    val filteredExpenses = filterByTimeframe(expenses, { it.date }, selectedTimeframe)
    val filteredFuel = filterByTimeframe(fuelEntries, { it.date }, selectedTimeframe)
    val filteredMaintenance = filterByTimeframe(maintenanceRecords, { it.date }, selectedTimeframe)

    val totals = mapOf(
        "General" to filteredExpenses.sumOf { it.amount },
        "Fuel" to filteredFuel.sumOf { it.totalCost },
        "Maintenance" to filteredMaintenance.sumOf { it.cost }
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(text = "Dashboard", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
        }

        item {
            TimeframeSelector(selectedTimeframe) { selectedTimeframe = it }
        }

        item {
            ExpenseChart(totals)
        }

        item {
            SummaryTable(totals)
        }

        item {
            Text(text = "Quick Stats", style = MaterialTheme.typography.h6, modifier = Modifier.padding(top = 8.dp))
        }

        item {
            SummaryCard("Active Documents", paperDocuments.size.toString(), "Documents in storage")
        }
    }
}

@Composable
private fun TimeframeSelector(selected: Timeframe, onSelect: (Timeframe) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Timeframe.values().forEach { timeframe ->
            Button(
                onClick = { onSelect(timeframe) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selected == timeframe) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
                    contentColor = if (selected == timeframe) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
                ),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(timeframe.name)
            }
        }
    }
}

@Composable
private fun ExpenseChart(totals: Map<String, Double>) {
    val maxVal = totals.values.maxOrNull()?.coerceAtLeast(1.0) ?: 1.0
    
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Expense Distribution", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(16.dp))
            
            totals.forEach { (label, value) ->
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(label, fontSize = 12.sp)
                        Text(formatCurrency(value), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth((value / maxVal).toFloat())
                                .height(12.dp)
                                .background(MaterialTheme.colors.primary, RoundedCornerShape(6.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryTable(totals: Map<String, Double>) {
    Card(elevation = 2.dp, shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                Text("Category", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Total Amount", fontWeight = FontWeight.Bold)
            }
            Divider()
            totals.forEach { (label, value) ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                    Text(label, modifier = Modifier.weight(1f))
                    Text(formatCurrency(value))
                }
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                Text("Total", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text(formatCurrency(totals.values.sum()), fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String, subtitle: String) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 2.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.subtitle2, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
            Text(text = subtitle, style = MaterialTheme.typography.caption)
        }
    }
}

private enum class Timeframe { Monthly, Yearly }

private fun <T> filterByTimeframe(
    items: List<T>,
    dateSelector: (T) -> String,
    timeframe: Timeframe
): List<T> {
    val now = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Assumed format based on typical usage

    return items.filter { item ->
        try {
            val date = LocalDate.parse(dateSelector(item), formatter)
            when (timeframe) {
                Timeframe.Monthly -> date.month == now.month && date.year == now.year
                Timeframe.Yearly -> date.year == now.year
            }
        } catch (e: Exception) {
            false // Filter out invalid dates
        }
    }
}

private fun formatCurrency(value: Double): String = "$${"%.2f".format(value)}"
