package my.edu.tarc.ezcharge.PumpCharging

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


lateinit var progressDialog: ProgressDialog

class ChargingActivity : AppCompatActivity() {
    val extras = Bundle()

    private lateinit var binding: ActivityChargingBinding
    private var progr = 0
    private val stop: Boolean = false
    private var stop1: Boolean = false

    private var tempID = ""
    private var tempStationNameR = ""
    private var tempTypes = ""
    private var tempTotalPay = ""
    private var tempDateTime = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        val extras = intent.extras
        val totalPay = extras!!.getDouble("TOTAL_PAY")
        val endTime = extras!!.getString("END_TIME")
        val activity = extras!!.getString("ACTIVITY")
        val duration = extras!!.getInt("DURATION")
        val stationNameR = extras!!.getString("LOCATION_NAME").toString()
        val stationPumpR = extras!!.getInt("PUMP_NO")
        val passAddress = extras!!.getString("PUMP_ADDRESS")
        val types = extras.getString("CONNECTOR_TYPES").toString()
        val walletBalance = extras!!.getDouble("WALLET_AMOUNT")

        val userID = extras!!.getString("USER_ID").toString()

        //For history firebase
        tempID = userID
        tempStationNameR = stationNameR
        tempTypes = types
        tempTotalPay = totalPay.toString()
//        val intent = intent
//        val totalPay = intent.getDoubleExtra("totalPay", 0.00)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        super.onCreate(savedInstanceState)
        binding = ActivityChargingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTime()
        addRecordToFirebase()

        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                if(progr != 100 && !stop1){
                    autoAdd()
                    handler.postDelayed(this, 1000)
                }else if(progr == 100 && !stop1){
                    binding.buttonStopDone.text = getString(R.string.scan_proceed)
                    Toast.makeText(this@ChargingActivity, getString(R.string.charging_settle), Toast.LENGTH_SHORT).show()
                }else{

                }

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
            if(progr != 100 && binding.buttonStopDone.text == "STOP"){
                Toast.makeText(this@ChargingActivity, "Stop", Toast.LENGTH_SHORT).show()
                stop()
            }else if(progr != 100 && binding.buttonStopDone.text == "RESUME"){
                stop1 = false
                binding.buttonStopDone.text = "STOP"
                val handler = Handler()
                val runnable: Runnable = object : Runnable {
                    override fun run() {
                        if(progr != 100 && !stop1){
                            autoAdd()
                            handler.postDelayed(this, 1000)
                        }else if(progr == 100 && !stop1){
                            binding.buttonStopDone.text = getString(R.string.scan_proceed)
                            Toast.makeText(this@ChargingActivity, getString(R.string.charging_settle), Toast.LENGTH_SHORT).show()
                        }else{
                        }
                    }
                }
                handler.postDelayed(runnable, 1000)
                Toast.makeText(this@ChargingActivity, "Resume", Toast.LENGTH_SHORT).show()
            }else{
                binding.buttonStopDone.text = getString(R.string.scan_proceed)
                //Toast.makeText(this@ChargingActivity, getString(R.string.charging_settle), Toast.LENGTH_SHORT).show()

                //Todo
                //Get charging type and pass also
                //Get wallet balance and pass also
                //Get Card no
                extras.putDouble("TOTAL_PRICE",totalPay)
                extras.putInt("PUMP_NO", stationPumpR)
                extras.putString("LOCATION_NAME", stationNameR)
                extras.putString("PUMP_ADDRESS", passAddress)
                extras.putString("CONNECTOR_TYPES", types)
                extras.putDouble("WALLET_AMOUNT", walletBalance)
                //Get address and pass also
                val intent = Intent(this, ChargingCompleteActivity::class.java)
                intent.putExtras(extras)
                startActivity(intent)
            }
        }
        binding.locationSmall.text = stationNameR
        binding.textViewDuration1.text = duration.toString() + " Minutes"
        binding.textViewPrice1.text = String.format("RM %.2f", totalPay)
        binding.textViewFinishAt1.text = endTime
    }

    private fun autoAdd(){
        stop1 = false
        progr += 10
        updateProgressBar()
    }

    private fun stop(){
        stop1 = true
        progr = progr
        updateProgressBar()
        binding.buttonStopDone.text = "RESUME"
        //Toast.makeText(this@ChargingActivity, "Stop", Toast.LENGTH_SHORT).show()
    }

    private fun updateProgressBar(){
        binding.progressBar.progress = progr
        binding.textViewProgress.text = "$progr%"
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

        hashMap["histtypes"] = "EzCharge"
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