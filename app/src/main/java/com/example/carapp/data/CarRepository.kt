package com.example.carapp.data

class CarRepository(private val database: CarDatabase) {
    val expensesFlow = database.expenseDao().getAllExpenses()
    val fuelEntriesFlow = database.fuelEntryDao().getAllFuelEntries()
    val maintenanceFlow = database.maintenanceDao().getAllMaintenanceRecords()
    val papersFlow = database.paperDocumentDao().getAllPaperDocuments()

    suspend fun addExpense(description: String, amount: Double, date: String) {
        database.expenseDao().insert(
            ExpenseEntity(
                description = description.ifBlank { "Expense" },
                amount = amount,
                date = date
            )
        )
    }

    suspend fun addFuelEntry(liters: Double, pricePerLiter: Double, odometer: Int, date: String) {
        database.fuelEntryDao().insert(
            FuelEntryEntity(
                liters = liters,
                pricePerLiter = pricePerLiter,
                odometer = odometer,
                date = date
            )
        )
    }

    suspend fun addMaintenanceRecord(title: String, details: String, cost: Double, date: String) {
        database.maintenanceDao().insert(
            MaintenanceEntity(
                title = title.ifBlank { "Maintenance" },
                details = details,
                cost = cost,
                date = date
            )
        )
    }

    suspend fun addPaperDocument(title: String, expiryDate: String, note: String) {
        database.paperDocumentDao().insert(
            PaperDocumentEntity(
                title = title.ifBlank { "Paper Document" },
                expiryDate = expiryDate,
                note = note
            )
        )
    }
}
