package my.edu.tarc.ezcharge.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.eventbus.UpdateCartEvent
import my.edu.tarc.ezcharge.model.CartModel
import org.greenrobot.eventbus.EventBus

class MyCartAdapter(
    private val context: Context, private val cartModelList:List<CartModel>, private val userId: String
): RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> (){

    //Get userID from previous activity then pass in
    private val userID = userId

    class MyCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var btnMinus: ImageView? = null
        var btnPlus: ImageView? = null
        var imageView: ImageView? = null
        var btnDelete: ImageView? = null
        var txtName: TextView? = null
        var txtPrice: TextView? = null
        var txtQuantity: TextView? = null

        init{
            btnMinus = itemView.findViewById(R.id.btnMinus) as ImageView
            btnPlus = itemView.findViewById(R.id.btnPlus) as ImageView
            imageView = itemView.findViewById(R.id.imageView) as ImageView
            btnDelete = itemView.findViewById(R.id.btnDelete) as ImageView
            txtName = itemView.findViewById(R.id.txtName) as TextView
            txtPrice = itemView.findViewById(R.id.txtPrice) as TextView
            txtQuantity = itemView.findViewById(R.id.txtQuantity) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        return MyCartViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cart_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        Glide.with(context).load(cartModelList[position].image).into(holder.imageView!!)
        holder.txtName!!.text = StringBuilder().append(cartModelList[position].name)
        holder.txtPrice!!.text = StringBuilder("RM ").append(cartModelList[position].price)
        holder.txtQuantity!!.text = StringBuilder("").append(cartModelList[position].quantity)

        //Event
        holder.btnMinus!!.setOnClickListener{
            minusCartItem(holder, cartModelList[position])
        }

        holder.btnPlus!!.setOnClickListener{
            plusCartItem(holder, cartModelList[position])
        }

        holder.btnDelete!!.setOnClickListener{
            val dialog = AlertDialog.Builder(context)
                .setTitle("Delete Item")
                .setMessage("Do you really want to delete item")
                .setNegativeButton("CANCEL") {dialog,_ -> dialog.dismiss()}
                .setPositiveButton("Delete") {dialog,_ ->

                    notifyItemRemoved(position)
                    FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("Cart").child(userID).child(cartModelList[position].key!!)
                        .removeValue().addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) }


                }
                .create()
            dialog.show()
        }
    }

    private fun plusCartItem(holder: MyCartViewHolder, cartModel: CartModel) {
        cartModel.quantity += 1
        cartModel.totalPrice = cartModel.quantity * cartModel.price!!.toDouble()
        holder.txtQuantity!!.text = StringBuilder("").append(cartModel.quantity)
        updateFireBase(cartModel)

    }

    private fun minusCartItem(holder: MyCartViewHolder, cartModel: CartModel) {
        if(cartModel.quantity > 1){
            cartModel.quantity -= 1
            cartModel.totalPrice = cartModel.quantity * cartModel.price!!.toDouble()
            holder.txtQuantity!!.text = StringBuilder("").append(cartModel.quantity)
            updateFireBase(cartModel)
        }
    }

    private fun updateFireBase(cartModel: CartModel) {
        FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("Cart").child(userID).child(cartModel.key!!).setValue(cartModel).addOnSuccessListener {
            EventBus.getDefault().postSticky(UpdateCartEvent())
        }
    }

    override fun getItemCount(): Int {
        return cartModelList.size
    }
}

