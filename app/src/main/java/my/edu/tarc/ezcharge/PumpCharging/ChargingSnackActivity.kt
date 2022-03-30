package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_charging_snack.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.adapter.MyDrinkAdapter
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackBinding
import my.edu.tarc.ezcharge.eventbus.UpdateCartEvent
import my.edu.tarc.ezcharge.listener.ICartLoadListener
import my.edu.tarc.ezcharge.listener.IDrinkLoadListener
import my.edu.tarc.ezcharge.model.CartModel
import my.edu.tarc.ezcharge.model.DrinkModel
import my.edu.tarc.ezcharge.utils.SpaceItemDecoration
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ChargingSnackActivity : AppCompatActivity(), IDrinkLoadListener, ICartLoadListener {

    lateinit var drinkLoadListener: IDrinkLoadListener
    lateinit var cartLoadListener: ICartLoadListener

    private lateinit var binding: ActivityChargingSnackBinding

    private var refreshID = ""

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if(EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java)){
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        }
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onUpdateCartEvent(event: UpdateCartEvent){
        countCartFromFireBase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val userID = intent.getStringExtra("USER_ID").toString()
        refreshID = userID
        super.onCreate(savedInstanceState)
        binding = ActivityChargingSnackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.titleSnack.text = userID
        init()
        loadDrinkFromFireBase()
        countCartFromFireBase()
    }

    private fun countCartFromFireBase() {
        val cartModels : MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("Cart").child(refreshID).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(cartSnapshot in snapshot.children){
                    val cartModel = cartSnapshot.getValue(CartModel::class.java)
                    cartModel!!.key = cartSnapshot.key
                    cartModels.add(cartModel)
                }
                cartLoadListener.onLoadCartSuccess(cartModels)
            }

            override fun onCancelled(error: DatabaseError) {
                cartLoadListener.onLoadCartFailed(error.message)
            }

        })
    }

    private fun loadDrinkFromFireBase(){
        val drinkModels : MutableList<DrinkModel> = ArrayList()
        FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("Drink").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(drinkSnapShot in snapshot.children){
                        val drinkModel = drinkSnapShot.getValue(DrinkModel::class.java)
                        drinkModel!!.key = drinkSnapShot.key
                        drinkModels.add(drinkModel)
                    }
                    drinkLoadListener.onDrinkLoadSuccess(drinkModels)
                }else{
                    drinkLoadListener.onDrinkLoadFailed("Drink items not exists")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                drinkLoadListener.onDrinkLoadFailed(error.message)
            }

        })
    }

    private fun init(){
        drinkLoadListener = this
        cartLoadListener = this

        val gridLayoutManager = GridLayoutManager(this, 2)
        recycler_drinks.layoutManager = gridLayoutManager
        recycler_drinks.addItemDecoration(SpaceItemDecoration())

        btnCart.setOnClickListener{
            //pass intent (userID) to next activity
            val intent = Intent(this, ChargingSnackCartActivity::class.java)
            intent.putExtra("USER_ID", refreshID)
            startActivity(intent)
            //startActivity(Intent(this, ChargingSnackCartActivity::class.java))
        }

        imageViewBack!!.setOnClickListener{
            finish()
        }
    }

    override fun onDrinkLoadSuccess(drinkModelList: List<DrinkModel>?) {
        val adapter = MyDrinkAdapter(this, drinkModelList!!, cartLoadListener, refreshID)
        recycler_drinks.adapter = adapter
    }

    override fun onDrinkLoadFailed(message: String?) {
        Snackbar.make(chargingSnackLayout, message!!, Snackbar.LENGTH_LONG).show()
    }

    override fun onLoadCartSuccess(cartModelList: List<CartModel>) {
        var cartSum = 0
        var cartTotalPrice = 0.00
        for (cartModel in cartModelList!!) cartSum += cartModel!!.quantity
        badge!!.setNumber(cartSum)
        for (cartModel in cartModelList!!) cartTotalPrice += cartModel!!.totalPrice
        totalPrice!!.text = String.format("%s %.2f", "RM ", cartTotalPrice)
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(chargingSnackLayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}