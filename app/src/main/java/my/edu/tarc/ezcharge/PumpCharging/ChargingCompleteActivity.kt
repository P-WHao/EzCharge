package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.databinding.ActivityChargingCompleteBinding
import java.text.SimpleDateFormat
import java.util.*

class ChargingCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingCompleteBinding

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        val extras = intent.extras
        val totalPay = extras!!.getDouble("TOTAL_PRICE")
        val pumpNo = extras!!.getInt("PUMP_NO")
        val stationName = extras!!.getString("LOCATION_NAME")
        val passAddress = extras!!.getString("PUMP_ADDRESS")
        val types = extras!!.getString("CONNECTOR_TYPES")
        val walletAmount = extras!!.getDouble("WALLET_AMOUNT")

        //Shared Preferences to get ezcharge card number
        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val ezChargeCardNo = preferences.getString("EZ_CHARGE_CARD", "")

        //Date
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        super.onCreate(savedInstanceState)
        binding = ActivityChargingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewProduct.text = types
        binding.textViewPrice.text = String.format("%.2f", totalPay)
        binding.textViewTotal.text = String.format("%.2f", totalPay)
        binding.textViewTotalPriceAmount.text = String.format("RM %.2f", totalPay)
        binding.textViewPump.text = pumpNo.toString()
        binding.textViewLocation.text = stationName.toString()
        binding.textViewReceiptDateResult.text = currentDate
        binding.textViewLocationAddr.text = passAddress

        binding.textViewWalletBalance.text = String.format("RM %.2f", walletAmount)
        binding.textViewEzChargeNoResult.text = ezChargeCardNo

        binding.imageViewCloseButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}