package my.edu.tarc.ezcharge.Charging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingGuidelinesBinding
import my.edu.tarc.ezcharge.databinding.ActivityChargingPumpBinding

class ChargingPumpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingPumpBinding

    var count = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingPumpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonIn.setOnClickListener{
            if(count >= 55) count = 55
            else{
                count+=5
            }
            binding.chargeValue.text = count.toString()
        }

        binding.buttonDe.setOnClickListener{
            if(count <= 5) count = 5
            else{
                count-=5
            }
            binding.chargeValue.text = count.toString()
        }

    }

}