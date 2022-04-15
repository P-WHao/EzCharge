package my.edu.tarc.ezcharge.PumpCharging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingHistoryDetailsBinding

class ChargingHistoryDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChargingHistoryDetailsBinding

    val extras = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingHistoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val extras = intent.extras

        val histTypes = extras!!.getString("HISTORY_TYPES", "")
        val locations = extras!!.getString("LOCATIONS", "")
        val types = extras!!.getString("CONNECT_TYPES", "")
        val pay = extras!!.getString("PAY", "")
        val timeDate = extras!!.getString("TIME_DATE", "")

        binding.textView15.text = String.format("-RM" + pay + "0")
        binding.textView20.text = histTypes.toString()
        binding.textView21.text = locations.toString()

        if(types == "Ez Snack"){
            binding.textView18.text = getString(R.string.hist_ezTypes)
        }

        binding.textView22.text = types.toString()
        binding.textView23.text = timeDate.toString()
        binding.textView25.text = "Successful"

        binding.textView27.text = "ezWallet Balance"

        binding.backToHistBtn1.setOnClickListener {
            finish()
        }
    }
}