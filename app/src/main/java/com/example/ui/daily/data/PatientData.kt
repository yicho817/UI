package com.example.ui.daily.data

// PatientData.kt



import android.util.Log
import com.example.ui.R
import com.example.ui.daily.*
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserRepository(private val csvFilePath: String) {
    private fun checkOrCreateCSVFile() {
        val file = File(csvFilePath)
        if (!file.exists()) {
            // 如果文件不存在，创建文件并写入初始数据
            file.createNewFile()
            writeInitialDataToCSV(file)
        }
    }

    private fun writeInitialDataToCSV(file: File) {
        // 创建初始数据列表
        val initialRecords = initRecords
        Log.d("CSV Write", "initRecord")
        // 打开CSV文件并写入初始数据
        val fileWriter = FileWriter(file)
        val csvWriter = CSVWriter(fileWriter)
        initialRecords.forEach { _ ->
            // 使用占位符或空字符串填充空值
            //val name = record.name ?: ""
            //val age = record.age ?: ""
            //val height = record.height ?: ""
            //val weight = record.weight ?: ""
            //val contact = record.contact ?: ""
            //val location = record.location ?: ""
            //val lastUpdate = record.lastUpdate ?: ""

            //val medicalRecords = record.medicalRecords?.flatMap { listOf(it.date ?: "", it.medical ?: "") } ?: listOf("", "")
            //val medicationRecords = record.medicationRecords?.flatMap { listOf(it.date ?: "", it.medication ?: "") } ?: listOf("", "")
            //val fallRecords = record.fallRecords?.flatMap { listOf(it.date ?: "", it.location ?: "") } ?: listOf("", "")
            //val symptomRecords = record.symptomRecords?.flatMap { listOf(it.date ?: "", it.symptoms ?: "") } ?: listOf("", "")

            csvWriter.writeNext(
                arrayOf(
                    //name, age, height, weight, contact, location, lastUpdate,
                    //*medicalRecords.toTypedArray(),
                    //*medicationRecords.toTypedArray(),
                    //*fallRecords.toTypedArray(),
                    //*symptomRecords.toTypedArray()
                )
            )
            //Log.d("CSV Write", "Record written: $name, $age, $height, $weight, $contact, $location, $lastUpdate, ...")
        }
        csvWriter.close()
    }

    fun readData(): List<PatientRecord> {
        // 在读取数据之前检查并创建CSV文件
        checkOrCreateCSVFile()

        val userRecords = mutableListOf<PatientRecord>()
        val file = File(csvFilePath)


        if (file.exists()) {
            val csvReader = CSVReader(FileReader(file))
            // 从 CSV 文件中读取数据
            while (true) {
                val record = csvReader.readNext() ?: break // 如果 record 为 null，退出循环
                // 输出到 Logcat
                Log.d("CSV Read", record.contentToString())

                // 将 CSV 记录转换为 MedicalRecord 对象
                if (record.size >= 2) { // 假設至少有8個字段
                    //val userInfo = parseRecords(record, 0, UserInfo::class.java)
                    val medicalRecords = parseRecords(record, 7, MedicalRecord::class.java)
                    val medicationRecords = parseRecords(record, 10, MedicationRecord::class.java)
                    val fallRecords = parseRecords(record, 13, FallRecord::class.java)
                    val symptomRecords = parseRecords(record, 16, SymptomRecord::class.java)

                    // 修改为允许嵌套列表中的记录包含空值
                    val patientRecord = PatientRecord(
                        //id = record[0]?.toIntOrNull(),
                        //name = record[1] ?: "",
                        //picture = record[2]?.toIntOrNull(),
                        //age = record[3] ?: "",
                        //height = record[4] ?: "",
                        //weight = record[5] ?: "",
                        //contact = record[6] ?: "",

                        // 其他屬性...
                        //userInfo = userInfo,
                        medicalRecords = medicalRecords,
                        medicationRecords = medicationRecords,
                        fallRecords = fallRecords,
                        symptomRecords = symptomRecords,
                        //location = record[7] ?: "",
                        //lastUpdate = record[8] ?: ""
                    )

                    userRecords.add(patientRecord)
                }
            }
            csvReader.close()
        }

        return userRecords
    }

    private fun <T> parseRecords(record: Array<String>, startIndex: Int, clazz: Class<T>): List<T> {
        val records = mutableListOf<T>()
        for (i in startIndex until record.size step 2) {
            val date = record[i]
            val info = record[i + 1]
            val constructor = clazz.getConstructor(String::class.java, String::class.java)
            val newRecord = constructor.newInstance(date, info)
            records.add(newRecord)
        }
        return records
    }
}



val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")!!
fun parseDateTimeFromString(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
    return LocalDateTime.parse(dateTimeString, formatter)
}

//val initRecords = mutableListOf<PatientRecord>()
val initRecords = mutableListOf(
    //PatientRecord(
    //userInfo = userInfo,
    //medicalRecords = medicalRecords,
    //medicationRecords = medicationRecords,
    //fallRecords = fallRecords,
    //symptomRecords = symptomRecords
    PatientRecord(
        userInfo = UserInfo(
            id = 1,
            name = "爺爺",
            picture = R.drawable.ya,
            age = "88",
            height = "175",
            weight = "70",
            contact = "爸爸 123-456-7890",
        ),

        medicalRecords = listOf(
            MedicalRecord(id =  1, date = LocalDateTime.parse("2023/01/01 12:30", dateTimeFormatter), medical = "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2023/01/01 12:30", dateTimeFormatter), medical = "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2023/02/15 12:30", dateTimeFormatter), medical = "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2023/10/08 12:30", dateTimeFormatter), "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2023/11/08 12:30", dateTimeFormatter), "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2023/12/08 12:30", dateTimeFormatter), "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2024/01/08 12:30", dateTimeFormatter), "何信醫院"),
            MedicalRecord(id =  1, date = LocalDateTime.parse("2024/01/29 12:30", dateTimeFormatter), "何信醫院"),
            MedicalRecord(2,date = LocalDateTime.parse("2023/01/01 12:30", dateTimeFormatter), medical = "Visited the hospital"),
            MedicalRecord(2,date = LocalDateTime.parse("2023/02/15 12:30", dateTimeFormatter), medical = "Follow-up appointment"),
            MedicalRecord(2,"2023/10/08 12:30", "何信醫院"),
            MedicalRecord(2,"2023/11/08 12:30", "何信醫院"),
            MedicalRecord(2,"2023/12/08 12:30", "何信醫院"),
            MedicalRecord(2,"2024/01/08 12:30", "何信醫院"),
            MedicalRecord(3,medical = "何信醫院", dateString = "2023/01/08 12:30")
        ),
        medicationRecords = listOf(
            MedicationRecord(id =  1, date = LocalDateTime.parse("2023/01/05 12:30", dateTimeFormatter), medication = "Aspirin"),
            MedicationRecord(id =  1, date = LocalDateTime.parse("2023/02/20 12:30", dateTimeFormatter), medication = "Antibiotics"),
            MedicationRecord(id =  1, date = LocalDateTime.parse("2023/10/08 12:30", dateTimeFormatter), medication = "心臟藥"),
            MedicationRecord(id =  1, date = LocalDateTime.parse("2023/11/08 12:30", dateTimeFormatter), medication = "心臟藥"),
            MedicationRecord(id =  1, date = LocalDateTime.parse("2023/12/08 12:30", dateTimeFormatter), medication = "心臟藥"),
            MedicationRecord(id =  1, date = LocalDateTime.parse("2024/01/08 12:30", dateTimeFormatter), medication = "心臟藥"),
            MedicationRecord(2,date = LocalDateTime.parse("2023/01/05 12:30", dateTimeFormatter), medication = "Aspirin"),
            MedicationRecord(2,date = LocalDateTime.parse("2023/02/20 12:30", dateTimeFormatter), medication = "Antibiotics"),
            MedicationRecord(2,"2023/10/08 12:30","心臟藥"),
            MedicationRecord(2,"2023/11/08 12:30","心臟藥"),
            MedicationRecord(2,"2023/12/08 12:30","心臟藥"),
            MedicationRecord(2,"2024/01/08 12:30","心臟藥")
        ),
        fallRecords = listOf(
            FallRecord(id =  1, dateString = "2023/03/10 12:30", location = "客廳"),
            FallRecord(id =  1, dateString = "2023/04/05 12:30", location = "廚房"),
            FallRecord(1,"2023/10/08 12:30", "客廳"),
            FallRecord(1,"2024/01/08 12:30", "浴室"),
            FallRecord(2, dateString = "2023/03/10 12:30", location = "Living room"),
            FallRecord(2, dateString = "2023/04/05 12:30", location = "Kitchen"),
            FallRecord(2,"2023/12/08 12:30", "浴室"),
            FallRecord(2,"2024/01/08 12:30", "客廳")
        ),
        symptomRecords = listOf(
            SymptomRecord(id =  1, dateString = "2023/03/01 12:30", symptoms = "Fever, headache"),
            SymptomRecord(id =  1, dateString = "2023/04/15 12:30", symptoms = "Cough, fatigue"),
            SymptomRecord(1,"2023/10/08 12:30","無異常"),
            SymptomRecord(1,"2023/11/08 12:30","無異常"),
            SymptomRecord(1,"2023/12/08 12:30","無異常"),
            SymptomRecord(1,"2024/01/08 12:30","無異常"),
            SymptomRecord(1,"2024/01/15 12:30","無異常"),
            SymptomRecord(1,"2024/01/30 12:30","無異常"),
            SymptomRecord(2,"2023/03/01 12:30","Fever, headache"),
            SymptomRecord(2,"2023/04/15 12:30","Cough, fatigue"),
            SymptomRecord(2,"2023/10/08 12:30","無異常"),
            SymptomRecord(2,"2023/11/08 12:30","無異常"),
            SymptomRecord(2,"2023/12/08 12:30","無異常"),
            SymptomRecord(2,"2024/01/08 12:30","無異常"),
            SymptomRecord(2,"2024/01/15 12:30","無異常"),
            SymptomRecord(2,"2024/01/29 12:30","無異常")
        )
    ),
    PatientRecord(
        UserInfo(
            id= 2 ,
            name = "奶奶",
            picture = R.drawable.nei,
            age = "88",
            height = "160",
            weight = "70.0",
            contact = "爸爸 123-456-7890"
        ),
    ),
    PatientRecord(
        UserInfo(
            id= 3 ,
            name = "爸爸",
            picture = R.drawable.pa,
            age = "60",
            contact = "媽媽"
        ),
    ),
    PatientRecord(
        UserInfo(
            id= 4 ,
            name = "媽媽",

            ),
    ),
    PatientRecord(
        UserInfo(
            id= 0 ,
            name = "",
            picture = 0,
            age = "",
            height = "",
            weight = "",
            contact = ""
        ),
    ),
    PatientRecord(
        UserInfo(
            id= 0 ,
            name = "",
            picture = 0,
            age = "",
            height = "",
            weight = "",
            contact = ""
        ),
    ),
    PatientRecord(
        UserInfo(
            id= 0 ,
            name = "",
            picture = 0,
            age = "",
            height = "",
            weight = "",
            contact = ""
        )
// 其他使用者...
    ),
)


/*
val initRecords = mutableListOf(
    // Your patient records here...

    PatientRecord(

        userInfo = UserInfo(
            id = 1,
            name = "爺爺",
            picture = R.drawable.ya,
            age = "88",
            height = "175",
            weight = "70",
            contact = "爸爸 123-456-7890",
        ),

        medicalRecords = listOf(
            MedicalRecord(userId =  1, date = "2023/01/01", medical = "何信醫院"),
            MedicalRecord(userId =  1, date = "2023/01/01", medical = "何信醫院"),
            MedicalRecord(userId =  1, date = "2023/02/15", medical = "何信醫院"),
            MedicalRecord(userId =  1, date = "2023/10/08", "何信醫院"),
            MedicalRecord(userId =  1, date = "2023/11/08", "何信醫院"),
            MedicalRecord(userId =  1, date = "2023/12/08", "何信醫院"),
            MedicalRecord(userId =  1, date = "2024/01/08", "何信醫院"),
            MedicalRecord(userId =  1, date = "2024/01/29", "何信醫院")
        ),
        medicationRecords = listOf(
            MedicationRecord(userId =  1, date = "2023/01/05", medication = "Aspirin"),
            MedicationRecord(userId =  1, date = "2023/02/20", medication = "Antibiotics"),
            MedicationRecord(userId =  1, date = "2023/10/08", medication = "心臟藥"),
            MedicationRecord(userId =  1, date = "2023/11/08", medication = "心臟藥"),
            MedicationRecord(userId =  1, date = "2023/12/08", medication = "心臟藥"),
            MedicationRecord(userId =  1, date = "2024/01/08", medication = "心臟藥")
        ),
        fallRecords = listOf(
            FallRecord(userId =  1, date = "2023/03/10", location = "客廳"),
            FallRecord(userId =  1, date = "2023/04/05", location = "廚房"),
            FallRecord(1,"2023/10/08", "客廳"),
            FallRecord(1,"2024/01/08", "浴室")
        ),
        symptomRecords = listOf(
            SymptomRecord(userId =  1,date = "2023/03/01", symptoms = "Fever, headache"),
            SymptomRecord(userId =  1,date = "2023/04/15", symptoms = "Cough, fatigue"),
            SymptomRecord(1,"2023/10/08","無異常"),
            SymptomRecord(1,"2023/11/08","無異常"),
            SymptomRecord(1,"2023/12/08","無異常"),
            SymptomRecord(1,"2024/01/08","無異常"),
            SymptomRecord(1,"2024/01/15","無異常"),
            SymptomRecord(1,"2024/01/30","無異常")
        ),
        location = "臥室",
        lastUpdate = "01/30 04:32"
    ),
    PatientRecord(

        userInfo = UserInfo(
            id= 2 ,
            name = "奶奶",
            picture = R.drawable.nei,
            age = "88",
            height = "160",
            weight = "70.0",
            contact = "爸爸 123-456-7890"
        ),
        medicalRecords = listOf(
            MedicalRecord(2,date = "2023/01/01", medical = "Visited the hospital"),
            MedicalRecord(2,date = "2023/02/15", medical = "Follow-up appointment"),
            MedicalRecord(2,"2023/10/08", "何信醫院"),
            MedicalRecord(2,"2023/11/08", "何信醫院"),
            MedicalRecord(2,"2023/12/08", "何信醫院"),
            MedicalRecord(2,"2024/01/08", "何信醫院")
        ),
        medicationRecords = listOf(
            MedicationRecord(2,date = "2023/01/05", medication = "Aspirin"),
            MedicationRecord(2,date = "2023/02/20", medication = "Antibiotics"),
            MedicationRecord(2,"2023/10/08","心臟藥"),
            MedicationRecord(2,"2023/11/08","心臟藥"),
            MedicationRecord(2,"2023/12/08","心臟藥"),
            MedicationRecord(2,"2024/01/08","心臟藥")
        ),
        fallRecords = listOf(
            FallRecord(2,date = "2023/03/10", location = "Living room"),
            FallRecord(2,date = "2023/04/05", location = "Kitchen"),
            FallRecord(2,"2023/12/08", "浴室"),
            FallRecord(2,"2024/01/08", "客廳")
        ),
        symptomRecords = listOf(
            SymptomRecord(2,date = "2023/03/01", symptoms = "Fever, headache"),
            SymptomRecord(2,date = "2023/04/15", symptoms = "Cough, fatigue"),
            SymptomRecord(2,"2023/10/08","無異常"),
            SymptomRecord(2,"2023/11/08","無異常"),
            SymptomRecord(2,"2023/12/08","無異常"),
            SymptomRecord(2,"2024/01/08","無異常"),
            SymptomRecord(2,"2024/01/15","無異常"),
            SymptomRecord(2,"2024/01/29","無異常")
        ),
        location = "客廳",
        lastUpdate = "01/29 10:32"
    ),

    PatientRecord(
        name = "爸爸",
        picture = R.drawable.pa,
        contact = "123-456-7890",
        medicalRecords = listOf(
            MedicalRecord(3,medical = "何信醫院",date = "2023/01/08"),),
        location = "何信醫院",
        lastUpdate = "01/08 10:32"
    ),
    PatientRecord(

    ),

    PatientRecord(

    ),

    // 可以添加更多的患者記錄
)
*/
