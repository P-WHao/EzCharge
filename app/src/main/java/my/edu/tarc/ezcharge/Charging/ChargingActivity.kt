package my.edu.tarc.ezcharge.Charging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingBinding

class ChargingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChargingBinding
    private var progr = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateProgressBar()

        binding.buttonIncr.setOnClickListener {
            if(progr <= 90){
                progr += 10
                updateProgressBar()
            }
        }

        binding.buttonDecr.setOnClickListener {
            if(progr >= 10){
                progr -= 10
                updateProgressBar()
            }
        }
    }

    private fun updateProgressBar(){
        binding.progressBar.progress = progr
        binding.textViewProgress.text = "$progr%"
    }
}