package my.edu.tarc.ezcharge.Data.ViewModel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import my.edu.tarc.ezcharge.Data.Entity.PumpItem
import my.edu.tarc.ezcharge.Data.Respository.PumpItemRespository

class PumpItemViewModel (private val PumpItemRespository: PumpItemRespository) : ViewModel() {

    val allItems : LiveData<MutableList<PumpItem>> = PumpItemRespository.alItems.asLiveData()

    fun getScanItem(scan_key: String): LiveData<MutableList<PumpItem>>{
        return PumpItemRespository.getScanItem(scan_key).asLiveData()
    }

    //Function to instantiate and return the ViewModel object that survives configuration changes.
    class PumpItemViewModelFactory(private val PumpItemRespository: PumpItemRespository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PumpItemViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return PumpItemViewModel(PumpItemRespository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}