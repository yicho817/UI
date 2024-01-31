package com.example.ui.daily.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PatientRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientRecordDao(): PatientRecordDao
}
