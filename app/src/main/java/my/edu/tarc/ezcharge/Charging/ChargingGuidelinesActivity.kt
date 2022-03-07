package my.edu.tarc.ezcharge.Charging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingGuidelinesBinding

class ChargingGuidelinesActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityChargingGuidelinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingGuidelinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Write your code below


    }
}