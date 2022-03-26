package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackSelectorBinding

class ChargingSnackSelectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingSnackSelectorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingSnackSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageViewCloseSelect.setOnClickListener {
            finish()
        }

        binding.buttonToCharge.setOnClickListener {
            val intent = Intent(this, ChargingGuidelinesActivity::class.java)
            startActivity(intent)
        }

        binding.buttonToBuySnack.setOnClickListener {
            val intent = Intent(this, ChargingSnackActivity::class.java)
            startActivity(intent)
        }
    }
}