package my.edu.tarc.ezcharge.Data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PumpItem")
data class PumpItem(
    @PrimaryKey @ColumnInfo(name = "Pump_ID") val Pump_ID: String,
    @ColumnInfo(name = "Pump_Name") val Pump_Name: String,
    @ColumnInfo(name = "Pump_No") val Pump_No: Int,
    @ColumnInfo(name = "Pump_Type") val Pump_Type: String,
    @ColumnInfo(name = "Pump_Address") val Pump_Address: String
)