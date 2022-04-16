package my.edu.tarc.ezcharge.Pay_TopUp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.cardform.view.CardEditText
import com.braintreepayments.cardform.view.CardForm
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityCreditCardFormBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class CreditCardForm : AppCompatActivity() {
    val extras = Bundle()
    private lateinit var binding: ActivityCreditCardFormBinding
    private lateinit var database: DatabaseReference

    private var tempID = ""
    private var toFirebase = 0.00

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent = intent
        val extras = intent.extras
        val topupAmount = extras!!.getDouble("topupAmount")
        val topupAmountOthers = extras!!.getDouble("topupAmountOthers")
        val topupType = extras!!.getString("topupType")
        val userUID = extras!!.getString("userUID").toString()

        val  balanceHold = extras!!.getDouble("userBalance")

        super.onCreate(savedInstanceState)
        binding =  ActivityCreditCardFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Define Credit Card Form
        val cardForm = findViewById<View>(R.id.card_form) as CardForm
        cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .cardholderName(CardForm.FIELD_REQUIRED)
            .setup(this)

        //Process amount to be display at button
        val topupToString = String.format("%.2f",topupAmountOthers)
        val topup2DecimalAmount = topupToString.toDouble()
        val topupForDisplayIntAmount = topupAmount.toInt()
        //define this variable to choose one of the amount to be store into firebase
        var topupFinalAmount = 0.00

        if(topupAmountOthers==0.00){
            topupFinalAmount = topupAmount
            binding.buttonReadyTopup.text = String.format("TOP-UP RM$topupForDisplayIntAmount")
        }else{
            topupFinalAmount = topup2DecimalAmount
            binding.buttonReadyTopup.text = String.format("TOP-UP RM$topup2DecimalAmount")
        }

        binding.viewBackTopup.setOnClickListener {
            finish()
        }

        binding.buttonReadyTopup.setOnClickListener {
            //To get Formatted Date Format
            val timestamp = System.currentTimeMillis()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            var tempDateTime = formatted
            if(cardForm.isValid){
                val getTopupType = topupType
                val getTopupAmount = topupFinalAmount
                val getTopupDate = tempDateTime
                val TopUp = TopUp(getTopupType,getTopupAmount,getTopupDate)

                toFirebase = balanceHold + getTopupAmount
                val TopUp1 = TopUp(getTopupType,toFirebase,getTopupDate)

                val ref = FirebaseDatabase.getInstance().getReference("TopUp").child(userUID)
                ref.child(timestamp.toString()).setValue(TopUp).addOnSuccessListener {
                    Toast.makeText(this,"Top-Up Successfully!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"Top-Up Failed! Please Try Again", Toast.LENGTH_SHORT).show()
                }

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

            }else
                Toast.makeText(this,"Please make sure all fields are filled in correctly", Toast.LENGTH_SHORT).show()

        }

    }
}