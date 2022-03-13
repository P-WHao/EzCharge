package my.edu.tarc.ezcharge.Charging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.databinding.ActivityChargingCompleteBinding

class ChargingCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}