package my.edu.tarc.ezcharge.Data.Respository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import my.edu.tarc.ezcharge.Data.Dao.PumpItemDao
import my.edu.tarc.ezcharge.Data.Entity.PumpItem

class PumpItemRespository(private val PumpItemDao: PumpItemDao) {

    val alItems:Flow<MutableList<PumpItem>> = PumpItemDao.getItems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(Item: PumpItem){
        PumpItemDao.insert(Item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(Item: PumpItem){
        PumpItemDao.delete(Item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(Item: PumpItem){
        PumpItemDao.update(Item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getScanItem(scan_key:String): Flow<MutableList<PumpItem>> {
        return PumpItemDao.getScanItem(scan_key)
    }
}