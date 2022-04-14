package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingPinBinding

class ChargingPinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChargingPinBinding
    //TO DO
    //Get wallet balance
    //Get wallet pin number
    //Deduce the wallet balance and Update deducted balance
    //If insufficient then terminate, back to home

    var extras = Bundle()

    var number_list = ArrayList<String>()
    var activity: String? = ""
    var totalPay = 0.00
    var endTime: String? = ""
    var duration = 0
    var stationPumpR = 0
    var stationNameR: String? = ""
    var passAddress: String? = ""
    var types: String? = ""
    var userID: String? = ""
    var walletAmount = 0.00
    var userPin: String? = ""
    var passcode = ""
    var num_01: String? = null
    var num_02: String? = null
    var num_03: String? = null
    var num_04: String? = null
    var num_05: String? = null
    var num_06: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializeCom()

        //get the intent in the target activity
        val intent = intent

        //get the attached bundle from the intent
        val extras = intent.extras

        //Extracting the stored data from the bundle
        activity = extras!!.getString("ACTIVITY")
        totalPay = extras.getDouble("TOTAL_PRICE")
        duration = extras.getInt("DURATION")
        endTime = extras.getString("END_TIME")
        stationNameR = extras.getString("LOCATION_NAME")
        stationPumpR = extras.getInt("PUMP_NO")
        passAddress = extras.getString("PUMP_ADDRESS")
        types = extras.getString("CONNECTOR_TYPES")
        userID = extras.getString("USER_ID")
        walletAmount = extras.getDouble("WALLET_BALANCE")
        userPin = extras.getString("USER_PIN")

        binding.btn1.setOnClickListener {
            number_list.add("1")
            passNumber(number_list)
        }
        binding.btn2.setOnClickListener {
            number_list.add("2")
            passNumber(number_list)
        }
        binding.btn3.setOnClickListener {
            number_list.add("3")
            passNumber(number_list)
        }
        binding.btn4.setOnClickListener {
            number_list.add("4")
            passNumber(number_list)
        }
        binding.btn5.setOnClickListener {
            number_list.add("5")
            passNumber(number_list)
        }
        binding.btn6.setOnClickListener {
            number_list.add("6")
            passNumber(number_list)
        }
        binding.btn7.setOnClickListener {
            number_list.add("7")
            passNumber(number_list)
        }
        binding.btn8.setOnClickListener {
            number_list.add("8")
            passNumber(number_list)
        }
        binding.btn9.setOnClickListener {
            number_list.add("9")
            passNumber(number_list)
        }
        binding.btn0.setOnClickListener {
            number_list.add("0")
            passNumber(number_list)
        }
        binding.btnClear.setOnClickListener {
            number_list.clear()
            passNumber(number_list)
        }

        binding.imageViewBackPin.setOnClickListener {
            finish()
        }

    }


    private fun passNumber(number_list: ArrayList<String>) {
        if (number_list.size == 0) {
            binding.dot1.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot2.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot3.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot4.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot5.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot6.setBackgroundResource(R.drawable.pin_dot_grey)
        } else {
            when (number_list.size) {
                1 -> {
                    num_01 = number_list[0]
                    binding.dot1.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                2 -> {
                    num_02 = number_list[1]
                    binding.dot2.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                3 -> {
                    num_03 = number_list[2]
                    binding.dot3.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                4 -> {
                    num_04 = number_list[3]
                    binding.dot4.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                5 -> {
                    num_05 = number_list[4]
                    binding.dot5.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                6 -> {
                    num_06 = number_list[5]
                    binding.dot6.setBackgroundResource(R.drawable.pin_dot_enter)
                    passcode = num_01 + num_02 + num_03 + num_04 + num_05 + num_06
                    matchPassCode()
                }
            }
        }
    }

    private fun matchPassCode() {
        if (passcode == userPin) { //Here need to integrate with user pin
            if (walletAmount >= totalPay) {
                walletAmount -= totalPay
                if (activity == "Charge") { //if null then view go to charging progress bar
                    Toast.makeText(this, "Paid", Toast.LENGTH_SHORT).show()
                    //Pass Value
                    extras.putDouble("TOTAL_PAY", totalPay)
                    extras.putString("CONNECTOR_TYPES", types)
                    extras.putString("END_TIME", endTime)
                    extras.putInt("DURATION", duration)
                    extras.putString("ACTIVITY", activity)
                    extras.putString("LOCATION_NAME", stationNameR)
                    extras.putInt("PUMP_NO", stationPumpR)
                    extras.putString("PUMP_ADDRESS", passAddress)
                    extras.putDouble("WALLET_AMOUNT", walletAmount)
                    extras.putString("USER_ID", userID)
                    val intent = Intent(this, ChargingActivity::class.java)
                    intent.putExtras(extras)
                    startActivity(intent)
                } else { //if not null then view go to receipt
                    extras.putDouble("TOTAL_PRICE", totalPay)
                    extras.putString("CONNECTOR_TYPES", activity)
                    extras.putString("LOCATION_NAME", stationNameR)
                    extras.putString("PUMP_ADDRESS", passAddress)
                    extras.putDouble("WALLET_AMOUNT", walletAmount)
                    extras.putString("USER_ID", userID)
                    //Remove all item in firebase
                    FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("Cart").child(userID!!).removeValue()
                    val intent = Intent(this, ChargingCompleteActivity::class.java)
                    intent.putExtras(extras)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, getString(R.string.insufficient_money), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.pin_not_match), Toast.LENGTH_SHORT).show()
        }
    }
}