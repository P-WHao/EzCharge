package my.edu.tarc.ezcharge.Data.Dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import my.edu.tarc.ezcharge.Data.Entity.PumpItem

@Dao
interface PumpItemDao {
    //Select scan item
    @Query("SELECT * FROM PumpItem WHERE Pump_ID = :scan_key")
    fun getScanItem(scan_key: String): Flow<MutableList<PumpItem>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(PumpItem: PumpItem)

    @Delete
    suspend fun delete(PumpItem: PumpItem)

    @Update
    suspend fun update(PumpItem: PumpItem)

    //Select All
    @Query("SELECT * FROM PumpItem ORDER BY Pump_ID")
    fun getItems():Flow<MutableList<PumpItem>>
}