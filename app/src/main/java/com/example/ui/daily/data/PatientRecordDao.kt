package com.example.ui.daily.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ui.daily.data.PatientRecord

@Dao
interface PatientRecordDao {
    @Insert
    suspend fun insert(patientRecord: PatientRecord)

    @Update
    fun update(patientRecord: PatientRecord)

    @Delete
    fun delete(patientRecord: PatientRecord)

    @Query("SELECT * FROM patient_records")
    suspend fun getAllPatientRecords(): List<PatientRecord>
}
