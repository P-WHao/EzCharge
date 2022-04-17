package my.edu.tarc.ezcharge.PumpCharging

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingCompleteBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ChargingCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingCompleteBinding

    lateinit var preferences: SharedPreferences

    private var tempID = ""
    private var tempStationNameR = ""
    private var tempTypes = ""
    private var tempTotalPay = ""
    private var tempDateTime = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        val extras = intent.extras
        val totalPay = extras!!.getDouble("TOTAL_PRICE")
        val pumpNo = extras!!.getInt("PUMP_NO")
        val stationName = extras!!.getString("LOCATION_NAME").toString()
        val passAddress = extras!!.getString("PUMP_ADDRESS")
        val types = extras!!.getString("CONNECTOR_TYPES").toString()
        val walletAmount = extras!!.getDouble("WALLET_AMOUNT")

        val userID = extras!!.getString("USER_ID").toString()

        //For history firebase
        tempID = userID
        tempStationNameR = stationName
        tempTypes = types
        tempTotalPay = totalPay.toString()
//        val intent = intent
//        val totalPay = intent.getDoubleExtra("totalPay", 0.00)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //Shared Preferences to get ezcharge card number
        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val ezChargeCardNo = preferences.getString("EZ_CHARGE_CARD", "")

        //Date
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        super.onCreate(savedInstanceState)
        binding = ActivityChargingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(types == "Ez Snack"){
            getTime()
            addRecordToFirebase()
            binding.textView2.text = "Receipt"
        }

        binding.textViewProduct.text = types
        binding.textViewPrice.text = String.format("%.2f", totalPay)
        binding.textViewTotal.text = String.format("%.2f", totalPay)
        binding.textViewTotalPriceAmount.text = String.format("RM %.2f", totalPay)
        binding.textViewPump.text = pumpNo.toString()
        binding.textViewLocation.text = stationName.toString()
        binding.textViewReceiptDateResult.text = currentDate
        binding.textViewLocationAddr.text = passAddress

        if(stationName == "Ez Charge Wangsa Maju"){
            binding.imageViewLocationImage.setImageResource(R.drawable.wangsanew)
        }else if(stationName == "Ez Charge Taman Bunga Raya"){
            binding.imageViewLocationImage.setImageResource(R.drawable.tbrnew)
        }else if(stationName == "Ez Charge Danau Kota"){
            binding.imageViewLocationImage.setImageResource(R.drawable.danaunew)
        }else if(stationName == "Ez Charge Ampang Jaya"){
            binding.imageViewLocationImage.setImageResource(R.drawable.ampangnew)
        }else if(stationName == "Ez Charge Setiawangsa"){
            binding.imageViewLocationImage.setImageResource(R.drawable.setianew)
        }else if(stationName == "Ez Charge City Centre"){
            binding.imageViewLocationImage.setImageResource(R.drawable.citynew)
        }

        binding.textViewWalletBalance.text = String.format("RM %.2f", walletAmount)
        //binding.textViewEzChargeNoResult.text = ezChargeCardNo

        binding.imageViewCloseButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Login","1")
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime() {

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)

        tempDateTime = formatted

        println("Current Date is: $formatted")
    }

    private fun addRecordToFirebase() {
        progressDialog.show()

        val hashMap = HashMap<String, Any>()

        val timestamp = System.currentTimeMillis()

        hashMap["histtypes"] = "EzSnack"
        hashMap["location"] = tempStationNameR
        hashMap["types"] = tempTypes
        hashMap["pay"] = tempTotalPay
        hashMap["timedate"] = tempDateTime

        val ref = FirebaseDatabase.getInstance().getReference("ChargeHis").child(tempID)
        ref.child(timestamp.toString())
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //Toast.makeText(this,"Pin added successfully!", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()
                //Toast.makeText(this,"Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}