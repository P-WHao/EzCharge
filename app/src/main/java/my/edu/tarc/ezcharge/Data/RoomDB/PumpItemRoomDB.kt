package my.edu.tarc.ezcharge.Data.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import my.edu.tarc.ezcharge.Data.Dao.PumpItemDao
import my.edu.tarc.ezcharge.Data.Entity.PumpItem

@Database(entities = [PumpItem::class], version = 1, exportSchema = false)
public abstract class PumpItemRoomDB : RoomDatabase(){

    abstract fun pumpItemDao() : PumpItemDao

    companion object {

        @Volatile
        private var INSTANCE : PumpItemRoomDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PumpItemRoomDB {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PumpItemRoomDB::class.java,
                    "item_database"
                ).addCallback(ItemCallback(scope))
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

    //Function to setup data when app installed
    private class ItemCallback(val scope: CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { itemRoomDB ->
                scope.launch {

                    //pre-populate data
                    //C1 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00001","Melawati",1,"universal", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00002","TARC",2,"universal", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00003","TBR AD",3,"universal", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00004","PV",4,"universal", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))

                    //C2 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00001","Aman Puri",1,"multi", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00002","Kepong",2,"multi", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00003","MRR2",3,"multi", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00004","Desa",4,"multi", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))

                    //C3 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00001","PJ",1,"many", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00002","Ehsan",2,"many", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00003","Suria",3,"many", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00004","Botani",4,"many", "Lot 44665 Mukim Batu Kepong Bandar Menjalara 52200 Kuala Lumpur"))
                }
            }
        }
    }
}