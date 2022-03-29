package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.databinding.ActivityChargingCompleteBinding
import java.text.SimpleDateFormat
import java.util.*

class ChargingCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        val extras = intent.extras
        val totalPay = extras!!.getDouble("TOTAL_PRICE")
        val pumpNo = extras!!.getInt("PUMP_NO")
        val stationName = extras!!.getString("LOCATION_NAME")

        //Date
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        super.onCreate(savedInstanceState)
        binding = ActivityChargingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewPrice.text = String.format("%.2f", totalPay)
        binding.textViewTotal.text = String.format("%.2f", totalPay)
        binding.textViewTotalPriceAmount.text = String.format("RM %.2f", totalPay)
        binding.textViewPump.text = pumpNo.toString()
        binding.textViewLocation.text = stationName.toString()
        binding.textViewReceiptDateResult.text = currentDate


        binding.imageViewCloseButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}