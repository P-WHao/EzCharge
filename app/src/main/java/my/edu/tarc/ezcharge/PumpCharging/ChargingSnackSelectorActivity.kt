package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingSnackSelectorBinding

class ChargingSnackSelectorActivity : AppCompatActivity() {

    //Here need to retrieve current user login ID from JK OR JS
    private val userID = "HELLO"

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
            Toast.makeText(this, "Ready To Recharge", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.buttonToBuySnack.setOnClickListener {
            val intent = Intent(this, ChargingSnackActivity::class.java)
            intent.putExtra("USER_ID", userID)
            Toast.makeText(this, "Get Some Snack", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}