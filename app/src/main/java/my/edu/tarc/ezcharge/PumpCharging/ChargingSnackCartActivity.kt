package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_charging_snack.*
import kotlinx.android.synthetic.main.activity_charging_snack_cart.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.adapter.MyCartAdapter
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackCartBinding
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackSelectorBinding
import my.edu.tarc.ezcharge.eventbus.UpdateCartEvent
import my.edu.tarc.ezcharge.listener.ICartLoadListener
import my.edu.tarc.ezcharge.model.CartModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//Need Check
//1. Switch button wont refresh

class ChargingSnackCartActivity : AppCompatActivity(), ICartLoadListener {
    private lateinit var binding: ActivityChargingSnackCartBinding

    var cartLoadListener: ICartLoadListener?= null

    private var refreshID = ""
    private var passToPay = 0.00
    val extras = Bundle()
//    private var plasticCharge = 0.00
//    private var plasticCharge1 = 0.00

    //Get intent from previous activity
    //private val userID = "1" //Then pass to MyCartAdapter
//    val userID = intent.getStringExtra("USER_ID").toString()

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
        loadCartFromFirebase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val userID = intent.getStringExtra("USER_ID").toString()
        refreshID = userID

        binding = ActivityChargingSnackCartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.switchPlasticBag.setOnCheckedChangeListener { buttonView, isChecked ->
//
//            if (binding.switchPlasticBag.isChecked) {
//                plasticCharge = 0.20
//            } else {
//                plasticCharge = 0.00
//            }
//            plasticCharge1 = plasticCharge
//
//        }
//        //plasticCharge1 = plasticCharge

        init()
        loadCartFromFirebase()
    }

    private fun loadCartFromFirebase() {
        val cartModels : MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("Cart").child(refreshID).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(cartSnapshot in snapshot.children){
                    val cartModel = cartSnapshot.getValue(CartModel::class.java)
                    cartModel!!.key = cartSnapshot.key
                    cartModels.add(cartModel)
                }
                cartLoadListener!!.onLoadCartSuccess(cartModels)
            }

            override fun onCancelled(error: DatabaseError) {
                cartLoadListener!!.onLoadCartFailed(error.message)
            }

        })
    }

    private fun init(){
        cartLoadListener = this
        val layoutManager = LinearLayoutManager(this)
        recycler_cart!!.layoutManager = layoutManager
        recycler_cart!!.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        btnClose!!.setOnClickListener{
            finish()
        }

        buttonPay!!.setOnClickListener {
            btmUpToPay()
        }

    }

    //Enter vehicle plate number
    private fun btmUpToPay() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme
        )

        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.activity_charging_plate, findViewById<LinearLayout>(R.id.plateBtmSheet)
        )

        bottomSheetView.findViewById<View>(R.id.imageViewClosePlate).setOnClickListener{
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<View>(R.id.buttonToPay).setOnClickListener{
            extras.putDouble("TOTAL_PRICE", passToPay)
            extras.putString("ACTIVITY", "NotCharge")
            val intent = Intent(this, ChargingPinActivity::class.java)
            intent.putExtras(extras)
            Toast.makeText(this, "TO PAY", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            //startActivity(Intent(this, ChargingPinActivity::class.java))
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    override fun onLoadCartSuccess(cartModelList: List<CartModel>) {
        var sum = 0.00
        var cartTotal = 0
        var sumTotal = 0.00

        //binding.totalPayBeforePlastic.text = plasticCharge1.toString()

        for (cartModel in cartModelList!!){
            sum += cartModel!!.totalPrice
            cartTotal += cartModel!!.quantity

        }

        //sumTotal = sum + plasticCharge
        totalPayBeforePlastic.text = String.format("%s %.2f", "RM ", sum)
        totalPay.text = String.format("%s %.2f", "RM ", sum) // Here need add plastic beg
        totalNum.text = String.format("%d", cartTotal)
        val adapter = MyCartAdapter(this, cartModelList, refreshID)
        recycler_cart!!.adapter = adapter
        passToPay = sum
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(chargingSnackLayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}