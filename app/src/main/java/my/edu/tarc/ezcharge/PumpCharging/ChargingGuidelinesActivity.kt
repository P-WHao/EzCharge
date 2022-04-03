package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingGuidelinesBinding

class ChargingGuidelinesActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityChargingGuidelinesBinding
    val extras = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingGuidelinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val extras = intent.extras

        val walletBalance = extras!!.getDouble("WALLET_BALANCE", 0.00)
        val userPin = extras!!.getString("USER_PIN", "")

        val isCheck = binding.checkBoxGuidelines
        //Write your code below
        binding.buttonAccept.setOnClickListener {

            if(isCheck.isChecked){
                val intent = Intent(this, ChargingScanActivity::class.java)
                extras.putDouble("WALLET_BALANCE", walletBalance)
                extras.putString("USER_PIN", userPin)
                intent.putExtras(extras)
                startActivity(intent)
            }else{
                Toast.makeText(this, getString(R.string.not_check), Toast.LENGTH_SHORT).show()
            }

        }

        binding.imageViewCloseButton.setOnClickListener {
            finish()
        }

    }
}