package com.example.carapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    val amount: Double,
    val date: String
)

@Entity(tableName = "fuel_entries")
data class FuelEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val liters: Double,
    val pricePerLiter: Double,
    val odometer: Int,
    val date: String
) {
    val totalCost: Double
        get() = liters * pricePerLiter
}

@Entity(tableName = "maintenance_records")
data class MaintenanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val details: String,
    val cost: Double,
    val date: String
)

@Entity(tableName = "paper_documents")
data class PaperDocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val expiryDate: String,
    val note: String
)
