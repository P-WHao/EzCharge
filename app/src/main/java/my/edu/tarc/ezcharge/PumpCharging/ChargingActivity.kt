package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingBinding

class ChargingActivity : AppCompatActivity() {

    //TO DO
    //Duration, Finish at, Price (Want use shareprefenrence?)

    private lateinit var binding: ActivityChargingBinding
    private var progr = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                autoAdd()
                handler.postDelayed(this, 1000)
            }
        }

        handler.postDelayed(runnable, 1000)

//        binding.buttonIncr.setOnClickListener {
//            if(progr <= 90){
//                progr += 10
//                updateProgressBar()
//            }
//        }
//
//        binding.buttonDecr.setOnClickListener {
//            if(progr >= 10){
//                progr -= 10
//                updateProgressBar()
//            }
//        }

        binding.buttonStopDone.setOnClickListener {
            val intent = Intent(this, ChargingCompleteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun autoAdd(){
        if(progr != 100){
            progr += 10
            updateProgressBar()
        }
        else{
            Toast.makeText(this, getString(R.string.charging_settle), Toast.LENGTH_SHORT).show();
        }
    }

    private fun updateProgressBar(){
        binding.progressBar.progress = progr
        binding.textViewProgress.text = "$progr%"
    }


}