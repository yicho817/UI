package com.example.ui.daily.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "patient_records")
data class PatientRecord(
    @PrimaryKey(autoGenerate = true)
    //val id: Int? = 0,
    //val name: String? = "",
    // ... 其他屬性
    val userInfo: UserInfo? = null,
    val medicalRecords: List<MedicalRecord>? = null,
    val medicationRecords: List<MedicationRecord>? = null,
    val fallRecords: List<FallRecord>? = null,
    val symptomRecords: List<SymptomRecord>? = null,
    val location: String? = "",
    val lastUpdate: LocalDateTime? = null
)

@Entity
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String? = "",
    val picture: Int? = null,
    val age: String? = "",
    val height: String? = "",
    val weight: String? = "",
    val contact: String? = ""
)
@Entity
data class MedicalRecord(
    val id: Int? = 0,      // 使用者的唯一標識符
    //val date: String? = "",
    val date: LocalDateTime,
    val medical: String? = ""
){
    constructor(id: Int, dateString: String, medical: String) : this(
        id = id,
        date = parseDateTimeFromString(dateString),
        medical = medical
    )
}
@Entity
data class MedicationRecord(
    val id: Int? = 0,      // 使用者的唯一標識符
    val date: LocalDateTime,
    val medication: String? = ""
){
    constructor(id: Int, dateString: String, medication: String) : this(
        id = id,
        date = parseDateTimeFromString(dateString),
        medication = medication
    )
}
@Entity
data class FallRecord(
    val id: Int? = 0,      // 使用者的唯一標識符
    val date: LocalDateTime,
    val location: String? = ""
) {
    constructor(id: Int, dateString: String, location: String) : this(
        id = id,
        date = parseDateTimeFromString(dateString),
        location = location
    )
}
@Entity
data class SymptomRecord(
    val id: Int? = 0,      // 使用者的唯一標識符
    val date: LocalDateTime,
    val symptoms: String? = ""
) {
    constructor(id: Int, dateString: String, symptoms: String) : this(
        id = id,
        date = parseDateTimeFromString(dateString),
        symptoms = symptoms
    )
}



