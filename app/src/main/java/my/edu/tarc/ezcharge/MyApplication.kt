package my.edu.tarc.ezcharge

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import my.edu.tarc.ezcharge.Data.Respository.PumpItemRespository
import my.edu.tarc.ezcharge.Data.RoomDB.PumpItemRoomDB

class MyApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    //item room db
    private val itemDatabase by lazy { PumpItemRoomDB.getDatabase(this, applicationScope) }
    val pumpItemRespository by lazy { PumpItemRespository(itemDatabase.pumpItemDao()) }
}