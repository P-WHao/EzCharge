package my.edu.tarc.ezcharge.listener

import my.edu.tarc.ezcharge.model.DrinkModel

interface IDrinkLoadListener {
    fun onDrinkLoadSuccess(drinkModelList:List<DrinkModel>?)
    fun onDrinkLoadFailed(message:String?)
}