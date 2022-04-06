package my.edu.tarc.ezcharge.PumpCharging

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackSelectorBinding

class ChargingSnackSelectorActivity : AppCompatActivity() {

    //Here need to retrieve current user login ID from JK OR JS
    private val userID = "HELLO"
    private val stationR = "KL"
    private val addressR = "Addressss"
    private val walletBalance = 200.00
    private val ezChargeCardNo = "WOOWOWOWOW"
    private val userPin = "111123"

    //Share Preferences
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivityChargingSnackSelectorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = Bundle()

        super.onCreate(savedInstanceState)
        binding = ActivityChargingSnackSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("EZ_CHARGE_CARD", ezChargeCardNo)
        editor.apply()

        binding.imageViewCloseSelect.setOnClickListener {
            finish()
        }

        binding.buttonToCharge.setOnClickListener {
            extras.putDouble("WALLET_BALANCE", walletBalance)
            extras.putString("USER_PIN", userPin)
            val intent = Intent(this, ChargingGuidelinesActivity::class.java)
            intent.putExtras(extras)
            Toast.makeText(this, "Ready To Recharge", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.buttonToBuySnack.setOnClickListener {
            extras.putString("USER_ID", userID)
            extras.putString("LOCATION_NAME", stationR)
            extras.putString("PUMP_ADDRESS", addressR)
            extras.putDouble("WALLET_BALANCE", walletBalance)
            extras.putString("USER_PIN", userPin)
            val intent = Intent(this, ChargingSnackActivity::class.java)
            intent.putExtras(extras)
            Toast.makeText(this, "Get Some Snack", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}