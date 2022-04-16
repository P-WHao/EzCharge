package my.edu.tarc.ezcharge.Pay_TopUp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.braintreepayments.cardform.view.CardForm
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityTopupBinding
import my.edu.tarc.ezcharge.databinding.ActivityVoucherTopupBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class VoucherTopupActivity : AppCompatActivity() {
    private lateinit var binding : ActivityVoucherTopupBinding

    private var toFirebase = 0.00

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent = intent
        val extras = intent.extras
        val userUID = extras!!.getString("userUID").toString()

        val  balanceHold = extras!!.getDouble("userBalance")

        super.onCreate(savedInstanceState)
        binding = ActivityVoucherTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewBackTopUp2.setOnClickListener {
            finish()
        }

        val code = binding.editTextVoucherCode

        binding.buttonVoucherTopup.setOnClickListener {
            //To get Formatted Date Format
            val timestamp = System.currentTimeMillis()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            var tempDateTime = formatted
            var getTopupAmount = 0.00
            if(code.text.toString() == "RDM20FTD"||code.text.toString() == "RDM40FTD"||code.text.toString() == "RDM60FTD"||code.text.toString() == "RDM100FTD"){
                val getTopupType = "Voucher Top-Up"
                if(code.text.toString() == "RDM20FTD"){
                    getTopupAmount = 20.00
                }else if(code.text.toString() == "RDM40FTD"){
                    getTopupAmount = 40.00
                }else if(code.text.toString() == "RDM60FTD"){
                    getTopupAmount = 60.00
                }else if(code.text.toString() == "RDM100FTD"){
                    getTopupAmount = 100.00
                }

                val getTopupDate = tempDateTime
                val TopUp = TopUp(getTopupType,getTopupAmount,getTopupDate)

                val ref = FirebaseDatabase.getInstance().getReference("TopUp").child(userUID)
                ref.child(timestamp.toString()).setValue(TopUp).addOnSuccessListener {
                    Toast.makeText(this,"Top-Up Successfully!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"Top-Up Failed! Please Try Again", Toast.LENGTH_SHORT).show()
                }

                toFirebase = balanceHold + getTopupAmount
                val TopUp1 = TopUp(getTopupType,toFirebase,getTopupDate)

                //Total Wallte Balance
                val refTotal = FirebaseDatabase.getInstance().getReference("TopUpTotal")
                refTotal.child(userUID).setValue(TopUp1).addOnSuccessListener {
                    Toast.makeText(this,"Top-Up Successfully!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"Top-Up Failed! Please Try Again", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Login","1")
                startActivity(intent)
            }else{
                Toast.makeText(this,"Invalid Code! Please try again", Toast.LENGTH_SHORT).show()
            }
        }


    }
}