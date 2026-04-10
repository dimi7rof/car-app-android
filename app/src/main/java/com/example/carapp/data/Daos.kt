package com.example.carapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY id DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insert(expense: ExpenseEntity)
}

@Dao
interface FuelEntryDao {
    @Query("SELECT * FROM fuel_entries ORDER BY id DESC")
    fun getAllFuelEntries(): Flow<List<FuelEntryEntity>>

    @Insert
    suspend fun insert(entry: FuelEntryEntity)
}

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM maintenance_records ORDER BY id DESC")
    fun getAllMaintenanceRecords(): Flow<List<MaintenanceEntity>>

    @Insert
    suspend fun insert(record: MaintenanceEntity)
}

@Dao
interface PaperDocumentDao {
    @Query("SELECT * FROM paper_documents ORDER BY id DESC")
    fun getAllPaperDocuments(): Flow<List<PaperDocumentEntity>>

    @Insert
    suspend fun insert(document: PaperDocumentEntity)
}
