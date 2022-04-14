package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

//Cant solve the enter car plate

class ChargingSnackCartActivity : AppCompatActivity(), ICartLoadListener {
    private lateinit var binding: ActivityChargingSnackCartBinding

    private lateinit var sharedPreferences: SharedPreferences

    var cartLoadListener: ICartLoadListener?= null

    private var refreshID = ""
    private var passToPay = 0.00

    private var stationR = ""
    private var addressR = ""
    val extras = Bundle()

    private var plasticCharge = 0.00
    private var plasticCharge1 = 0.00

    private var checkCartTotal = 0 //validation

    private var walletBalance = 0.00
    private var userPin = ""

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
        val location = intent.getStringExtra("LOCATION_NAME").toString()
        val addressRR = intent.getStringExtra("PUMP_ADDRESS").toString()
        val walletBalance1 = intent.getDoubleExtra("WALLET_BALANCE", 0.00)
        val userPin1 = intent.getStringExtra("USER_PIN").toString()

        refreshID = userID
        stationR = location
        addressR = addressRR
        walletBalance = walletBalance1
        userPin = userPin1

        binding = ActivityChargingSnackCartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.switchPlasticBag.setOnCheckedChangeListener { buttonView, isChecked ->

            if (binding.switchPlasticBag.isChecked) {
                plasticCharge = 0.20
            } else {
                plasticCharge = 0.00
            }
            plasticCharge1 = plasticCharge
            EventBus.getDefault().postSticky(UpdateCartEvent())
        }
        //plasticCharge1 = plasticCharge

        init()
        loadCartFromFirebase()
    }

    companion object {
        const val PLATE = "my.edu.tarc.ezcharge.plate"
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

        val bottomSheetFragment = ChargingBottomSheetFragment()

        buttonPay!!.setOnClickListener {
            if(checkCartTotal !=0 ){
                extras.putDouble("TOTAL_PRICE", passToPay)
                extras.putString("ACTIVITY", "Ez Snack")
                extras.putString("USER_ID", refreshID)
                extras.putString("LOCATION_NAME", stationR)
                extras.putString("PUMP_ADDRESS", addressR)
                extras.putDouble("WALLET_BALANCE", walletBalance)
                extras.putString("USER_PIN", userPin)
                bottomSheetFragment.arguments = extras
                bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
            }else{
                Toast.makeText(this, getString(R.string.empty_cart), Toast.LENGTH_SHORT).show()
            }
        }
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

        checkCartTotal = cartTotal

        totalPayBeforePlastic.text = String.format("%s %.2f", "RM ", sum)
        totalPay.text = String.format("%s %.2f", "RM ", sum + plasticCharge1) // Here need add plastic beg
        totalNum.text = String.format("%d", cartTotal)
        userWalletBalance.text = String.format("%s %.2f", "RM ", walletBalance)
        val adapter = MyCartAdapter(this, cartModelList, refreshID)
        recycler_cart!!.adapter = adapter
        passToPay = sum + plasticCharge1
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(chargingSnackLayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}