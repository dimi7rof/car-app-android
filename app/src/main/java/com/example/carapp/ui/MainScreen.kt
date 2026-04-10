package com.example.carapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import com.example.carapp.ui.screens.DashboardScreen
import com.example.carapp.ui.screens.ExpensesScreen
import com.example.carapp.ui.screens.FuelScreen
import com.example.carapp.ui.screens.MaintenanceScreen
import com.example.carapp.ui.screens.PapersScreen
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Receipt
import com.example.carapp.MainViewModel
import com.example.carapp.ui.screens.DashboardScreen
import com.example.carapp.ui.screens.ExpensesScreen
import com.example.carapp.ui.screens.FuelScreen
import com.example.carapp.ui.screens.MaintenanceScreen
import com.example.carapp.ui.screens.PapersScreen

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val expenses by viewModel.expenses.collectAsState(initial = emptyList())
    val fuelEntries by viewModel.fuelEntries.collectAsState(initial = emptyList())
    val maintenanceRecords by viewModel.maintenanceRecords.collectAsState(initial = emptyList())
    val paperDocuments by viewModel.paperDocuments.collectAsState(initial = emptyList())
    var selectedTab by rememberSaveable { mutableStateOf(AppSection.Dashboard) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Car Expense Tracker") })
        },
        bottomBar = {
            BottomNavigation {
                AppSection.values().forEach { section ->
                    BottomNavigationItem(
                        icon = { Icon(imageVector = section.icon, contentDescription = section.title) },
                        label = { Text(section.title) },
                        selected = section == selectedTab,
                        onClick = { selectedTab = section },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                AppSection.Dashboard -> DashboardScreen(expenses, fuelEntries, maintenanceRecords, paperDocuments)
                AppSection.Expenses -> ExpensesScreen(expenses, viewModel::addExpense)
                AppSection.Fuel -> FuelScreen(fuelEntries, viewModel::addFuelEntry)
                AppSection.Maintenance -> MaintenanceScreen(maintenanceRecords, viewModel::addMaintenanceRecord)
                AppSection.Papers -> PapersScreen(paperDocuments, viewModel::addPaperDocument)
            }
        }
    }
}

private enum class AppSection(val title: String, val icon: ImageVector) {
    Dashboard("Dashboard", Icons.Default.Home),
    Expenses("Expenses", Icons.Default.Receipt),
    Fuel("Fuel", Icons.Default.LocalGasStation),
    Maintenance("Maintenance", Icons.Default.Build),
    Papers("Papers", Icons.Default.Description)
}
