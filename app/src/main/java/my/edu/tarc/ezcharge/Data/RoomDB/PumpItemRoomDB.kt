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
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00001","EzCharge Wangsa Maju",1,"universal", "32-20 Jalan Metro Wangsa Wangsa Maju 53300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00002","EzCharge Wangsa Maju",2,"universal", "32-20 Jalan Metro Wangsa Wangsa Maju 53300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00003","EzCharge Wangsa Maju",3,"universal", "32-20 Jalan Metro Wangsa Wangsa Maju 53300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C1-00004","EzCharge Wangsa Maju",4,"universal", "32-20 Jalan Metro Wangsa Wangsa Maju 53300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))

                    //C2 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00001","Ez Charge Taman Bunga Raya",1,"multi", "Jalan Malinja Taman Bunga Raya 51300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00002","Ez Charge Taman Bunga Raya",2,"multi", "Jalan Malinja Taman Bunga Raya 51300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00003","Ez Charge Taman Bunga Raya",3,"multi", "Jalan Malinja Taman Bunga Raya 51300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C2-00004","Ez Charge Taman Bunga Raya",4,"multi", "Jalan Malinja Taman Bunga Raya 51300 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))

                    //C3 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00001","Ez Charge Danau Kota",1,"many", "Danau Kota 53100 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00002","Ez Charge Danau Kota",2,"many", "Danau Kota 53100 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00003","Ez Charge Danau Kota",3,"many", "Danau Kota 53100 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C3-00004","Ez Charge Danau Kota",4,"many", "Danau Kota 53100 Kuala Lumpur Federal Territory of Kuala Lumpur"))

                    //C4 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C4-00001","Ez Charge Ampang Jaya",1,"many", "Jalan Besar Pekan Ampang 68000 Ampang, Selangor"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C4-00002","Ez Charge Ampang Jaya",2,"many", "Jalan Besar Pekan Ampang 68000 Ampang, Selangor"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C4-00003","Ez Charge Ampang Jaya",3,"many", "Jalan Besar Pekan Ampang 68000 Ampang, Selangor"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C4-00004","Ez Charge Ampang Jaya",4,"many", "Jalan Besar Pekan Ampang 68000 Ampang, Selangor"))

                    //C5 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C5-00001","Ez Charge Setiawangsa",1,"many", "2 Jalan Bukit Setiawangsa 5 Taman Setiawangsa 54200 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C5-00002","Ez Charge Setiawangsa",2,"many", "2 Jalan Bukit Setiawangsa 5 Taman Setiawangsa 54200 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C5-00003","Ez Charge Setiawangsa",3,"many", "2 Jalan Bukit Setiawangsa 5 Taman Setiawangsa 54200 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C5-00004","Ez Charge Setiawangsa",4,"many", "2 Jalan Bukit Setiawangsa 5 Taman Setiawangsa 54200 Kuala Lumpur Wilayah Persekutuan Kuala Lumpur"))

                    //C6 Item
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C6-00001","Ez Charge City Centre",1,"many", "Kuala Lumpur City Centre 50050 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C6-00002","Ez Charge City Centre",2,"many", "Kuala Lumpur City Centre 50050 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C6-00003","Ez Charge City Centre",3,"many", "Kuala Lumpur City Centre 50050 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                    itemRoomDB.pumpItemDao().insert(PumpItem("E-C6-00004","Ez Charge City Centre",4,"many", "Kuala Lumpur City Centre 50050 Kuala Lumpur Federal Territory of Kuala Lumpur"))
                }
            }
        }
    }
}