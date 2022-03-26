package my.edu.tarc.ezcharge.PumpCharging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.databinding.ActivityChargingPlateBinding

class ChargingPlateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingPlateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingPlateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Ntg
    }
}