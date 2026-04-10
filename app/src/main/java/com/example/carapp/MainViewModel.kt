package com.example.carapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.carapp.data.CarRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(private val repository: CarRepository) : ViewModel() {
    val expenses = repository.expensesFlow
    val fuelEntries = repository.fuelEntriesFlow
    val maintenanceRecords = repository.maintenanceFlow
    val paperDocuments = repository.papersFlow

    fun addExpense(description: String, amount: String, date: String) {
        viewModelScope.launch {
            repository.addExpense(description.trim(), amount.toDoubleOrNull() ?: 0.0, date.ifBlank { currentDate() })
        }
    }

    fun addFuelEntry(liters: String, pricePerLiter: String, odometer: String, date: String) {
        viewModelScope.launch {
            repository.addFuelEntry(
                liters.toDoubleOrNull() ?: 0.0,
                pricePerLiter.toDoubleOrNull() ?: 0.0,
                odometer.toIntOrNull() ?: 0,
                date.ifBlank { currentDate() }
            )
        }
    }

    fun addMaintenanceRecord(title: String, details: String, cost: String, date: String) {
        viewModelScope.launch {
            repository.addMaintenanceRecord(title.trim(), details.trim(), cost.toDoubleOrNull() ?: 0.0, date.ifBlank { currentDate() })
        }
    }

    fun addPaperDocument(title: String, expiryDate: String, note: String) {
        viewModelScope.launch {
            repository.addPaperDocument(title.trim(), expiryDate.ifBlank { currentDate() }, note.trim())
        }
    }

    private fun currentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return formatter.format(Date())
    }

    class Factory(private val repository: CarRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
