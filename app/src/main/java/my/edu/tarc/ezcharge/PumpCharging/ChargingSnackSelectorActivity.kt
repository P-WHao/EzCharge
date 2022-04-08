package my.edu.tarc.ezcharge.PumpCharging

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackSelectorBinding
import my.edu.tarc.ezcharge.login.DashboardAdminActivity

class ChargingSnackSelectorActivity : AppCompatActivity() {

    //Here need to retrieve current user login ID from JK OR JS
    //private val userID = "HELLO"
    private val stationR = "KL"
    private val addressR = "Addressss"
    private val walletBalance = 200.00
    private val ezChargeCardNo = "WOOWOWOWOW"
    //private val userPin = "111123"

    private lateinit var firebaseAuth: FirebaseAuth
    var userPass = ""
    var userUID = ""
    var userUIDFirst = ""

    //Share Preferences
    lateinit var sharedPreferences: SharedPreferences
    lateinit var preferences: SharedPreferences

    private lateinit var binding: ActivityChargingSnackSelectorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = Bundle()

        super.onCreate(savedInstanceState)
        binding = ActivityChargingSnackSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        ref.child(userUIDFirst)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userUID = snapshot.child("uid").value
                }

                override fun onCancelled(error: DatabaseError){

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
            extras.putDouble("WALLET_BALANCE", walletBalance)
            extras.putString("USER_PIN", userPass)
            val intent = Intent(this, ChargingGuidelinesActivity::class.java)
            intent.putExtras(extras)
            Toast.makeText(this, "Ready To Recharge", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.buttonToBuySnack.setOnClickListener {
            extras.putString("USER_ID", userUID)
            extras.putString("LOCATION_NAME", stationR)
            extras.putString("PUMP_ADDRESS", addressR)
            extras.putDouble("WALLET_BALANCE", walletBalance)
            extras.putString("USER_PIN", userPass)
            val intent = Intent(this, ChargingSnackActivity::class.java)
            intent.putExtras(extras)
            Toast.makeText(this, "Get Some Snack", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}