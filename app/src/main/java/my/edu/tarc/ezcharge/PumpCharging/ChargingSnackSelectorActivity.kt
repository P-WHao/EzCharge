package my.edu.tarc.ezcharge.PumpCharging

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackSelectorBinding
import my.edu.tarc.ezcharge.login.DashboardAdminActivity

class ChargingSnackSelectorActivity : AppCompatActivity() {

    //Here need to get LY Address and Station Name
    //Here need to get JK Wallet Balance and Ez Charge Card No

    //private val userID = "HELLO"
    //private val stationR = "KL"
    private var addressR = ""
    private var walletBalance = 0.00
    private val ezChargeCardNo = "WOOWOWOWOW"
    //private val userPin = "111123"

    private lateinit var firebaseAuth: FirebaseAuth
    var userPass = ""
    var userUID = ""
    var userUIDFirst = ""
    private lateinit var dbref : DatabaseReference

    //Share Preferences
    lateinit var sharedPreferences: SharedPreferences
    lateinit var preferences: SharedPreferences

    private lateinit var binding: ActivityChargingSnackSelectorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = Bundle()

        super.onCreate(savedInstanceState)
        binding = ActivityChargingSnackSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stationR = intent.getStringExtra("EXTRA_location").toString()

        if(stationR == "Ez Charge Wangsa Maju"){
            addressR = "32-20, Jalan Metro Wangsa, Wangsa Maju, 53300 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur"
        }else if(stationR == "Ez Charge Taman Bunga Raya"){
            addressR = "Jalan Malinja, Taman Bunga Raya, 51300 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur"
        }else if(stationR == "Ez Charge Danau Kota"){
            addressR = "Danau Kota, 53100 Kuala Lumpur, Federal Territory of Kuala Lumpur"
        }else if(stationR == "Ez Charge Ampang Jaya"){
            addressR =  "Jalan Besar, Pekan Ampang, 68000 Ampang, Selangor"
        }else if(stationR == "Ez Charge Setiawangsa"){
            addressR = "2, Jalan Bukit Setiawangsa 5, Taman Setiawangsa, 54200 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur"
        }else if(stationR == "Ez Charge City Centre"){
            addressR = "Kuala Lumpur City Centre, 50050 Kuala Lumpur, Federal Territory of Kuala Lumpur"
        }


        firebaseAuth = FirebaseAuth.getInstance()
        //get current user, if is logged in
        val firebaseUser = firebaseAuth.currentUser

        val ref = FirebaseDatabase.getInstance().getReference("Users")

        //Get USER_PIN
        val userPin = FirebaseDatabase.getInstance().getReference("Pin")
        if (firebaseUser != null) {
            userUIDFirst = firebaseUser.uid
        }
        userPin.child(userUIDFirst).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userPinIn = snapshot.child("pin").value
                userPass = userPinIn.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //Get current ID
        ref.child(userUIDFirst)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userUIDPri = snapshot.child("uid").value
                    userUID = userUIDPri.toString()
                }

                override fun onCancelled(error: DatabaseError){

                }
            })

        //Get Wallet Balance
        dbref = FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("TopUpTotal")

        dbref.child(userUIDFirst)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val sum = snapshot.child("topupAmount").getValue(Double::class.java)
                    if (sum != null) {
                        walletBalance = sum
                    }
                    //binding.textViewMainBalance.text = String.format("RM%.2f",sumTotal)
                    //sumTotal = sumTotal
                }

                override fun onCancelled(error: DatabaseError) {
                    throw error.toException()
                }


            })

//        //Shared Preferences to get ezcharge card number
//        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
//        val userID = preferences.getString("USER_UID", "")
//        val userPin = preferences.getString("USER_PIN", "")

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("EZ_CHARGE_CARD", ezChargeCardNo)
        editor.apply()

        binding.imageViewCloseSelect.setOnClickListener {
            finish()
        }

        binding.buttonToCharge.setOnClickListener {

            if(walletBalance >= 200.00){
                extras.putDouble("WALLET_BALANCE", walletBalance)
                extras.putString("USER_ID", userUID)
                extras.putString("USER_PIN", userPass)
                val intent = Intent(this, ChargingGuidelinesActivity::class.java)
                intent.putExtras(extras)
                Toast.makeText(this, "Ready To Recharge", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                val intent = Intent(this, MainActivity::class.java) //To Top Up (NEED CHANGE)
                intent.putExtra("Login","1")
                Toast.makeText(this, getString(R.string.insufficient_money), Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }

        binding.buttonToBuySnack.setOnClickListener {
            if(walletBalance >= 200.00){
                extras.putString("USER_ID", userUID)
                extras.putString("LOCATION_NAME", stationR)
                extras.putString("PUMP_ADDRESS", addressR)
                extras.putDouble("WALLET_BALANCE", walletBalance)
                extras.putString("USER_PIN", userPass)
                val intent = Intent(this, ChargingSnackActivity::class.java)
                intent.putExtras(extras)
                Toast.makeText(this, "Get Some Snack", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                val intent = Intent(this, MainActivity::class.java) //To Top Up (NEED CHANGE)
                intent.putExtra("Login","1")
                Toast.makeText(this, getString(R.string.insufficient_money), Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }
    }
}