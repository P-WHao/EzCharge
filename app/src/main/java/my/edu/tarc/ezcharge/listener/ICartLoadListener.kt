package my.edu.tarc.ezcharge.listener

import my.edu.tarc.ezcharge.model.CartModel

interface ICartLoadListener {
    fun onLoadCartSuccess(cartModelList: List<CartModel>)
    fun onLoadCartFailed(message:String?)
}