package com.example.carapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ExpenseEntity::class, FuelEntryEntity::class, MaintenanceEntity::class, PaperDocumentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CarDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun fuelEntryDao(): FuelEntryDao
    abstract fun maintenanceDao(): MaintenanceDao
    abstract fun paperDocumentDao(): PaperDocumentDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_data.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
