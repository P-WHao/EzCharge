package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_charging_btm_plate.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingBtmPlateBinding
import java.util.regex.Pattern

class ChargingBottomSheetFragment: BottomSheetDialogFragment() {
    private lateinit var binding: ActivityChargingBtmPlateBinding

    val extras = Bundle()

    private var passToPay = 0.00
    private var refreshID = ""
    private var stationR = ""
    private var addressR = ""
    private var walletBalance = 0.00
    private var userPin = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityChargingBtmPlateBinding.inflate(layoutInflater, container, false)
        val passToPay1 =  arguments?.getDouble("TOTAL_PRICE")
        val refreshID1 =  arguments?.getString("USER_ID")
        val stationR1 =  arguments?.getString("LOCATION_NAME")
        val addressR1 =  arguments?.getString("PUMP_ADDRESS")
        val walletBalance1 =  arguments?.getDouble("WALLET_BALANCE")
        val userPin1 =  arguments?.getString("USER_PIN")
        if (passToPay1 != null) {
            passToPay = passToPay1
        }
        if (refreshID1 != null) {
            refreshID = refreshID1
        }
        if (stationR1 != null) {
            stationR = stationR1
        }
        if (addressR1 != null) {
            addressR = addressR1
        }
        if (walletBalance1 != null) {
            walletBalance = walletBalance1
        }
        if (userPin1 != null) {
            userPin = userPin1
        }

        //return inflater.inflate(R.layout.activity_charging_btm_plate, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToPay.setOnClickListener {
            when{
                binding.editTextPlatNo.text.isEmpty() -> {
                    binding.editTextPlatNo.error = "Required Input"
                    binding.editTextPlatNo.requestFocus()
                    return@setOnClickListener
                }
            }

            //Can try regex
            val value = binding.editTextPlatNo.text.toString()
            val pattern = Pattern.compile("[A-Z]{3}a{1,3}$") //WWW1234
            val matcher = pattern.matcher(value)
            val found = matcher.find()

            val pattern0 = Pattern.compile("[A-Z]{3}[0-9]{1}$") //WWW1
            val matcher0 = pattern0.matcher(value)
            val found0 = matcher0.find()

            val pattern00 = Pattern.compile("[A-Z]{3}[0-9]{2}$") //WWW12
            val matcher00 = pattern00.matcher(value)
            val found00 = matcher00.find()

            val pattern000 = Pattern.compile("[A-Z]{3}[0-9]{3}$") //WWW123
            val matcher000 = pattern000.matcher(value)
            val found000 = matcher000.find()

            val pattern1 = Pattern.compile("[A-Z]{2}[0-9]{4}[A-Z]{1}$") //WW1234W
            val matcher1 = pattern1.matcher(value)
            val found1 = matcher1.find()

            val pattern11 = Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z]{1}$") //WW123W
            val matcher11 = pattern11.matcher(value)
            val found11 = matcher11.find()

            val pattern111 = Pattern.compile("[A-Z]{2}[0-9]{2}[A-Z]{1}$") //WW12W
            val matcher111 = pattern111.matcher(value)
            val found111 = matcher111.find()

            val pattern1111 = Pattern.compile("[A-Z]{2}[0-9]{1}[A-Z]{1}$") //WW1W
            val matcher1111 = pattern1111.matcher(value)
            val found1111 = matcher1111.find()

            val pattern2 = Pattern.compile("[A-Z]{1}[0-9]{4}[A-Z]{1}$") //W1234W
            val matcher2 = pattern2.matcher(value)
            val found2 = matcher2.find()

            val pattern22 = Pattern.compile("[A-Z]{1}[0-9]{3}[A-Z]{1}$") //W123W
            val matcher22 = pattern22.matcher(value)
            val found22 = matcher22.find()

            val pattern222 = Pattern.compile("[A-Z]{1}[0-9]{2}[A-Z]{1}$") //W12W
            val matcher222 = pattern222.matcher(value)
            val found222 = matcher222.find()

            val pattern2222 = Pattern.compile("[A-Z]{1}[0-9]{1}[A-Z]{1}$") //W1W
            val matcher2222 = pattern2222.matcher(value)
            val found2222 = matcher2222.find()

            val pattern3 = Pattern.compile("[A-Z]{2}[0-9]{4}$") //WW1234
            val matcher3 = pattern3.matcher(value)
            val found3 = matcher3.find()

            val pattern4 = Pattern.compile("[A-Z]{2}[0-9]{1}$") //WW1
            val matcher4 = pattern4.matcher(value)
            val found4 = matcher4.find()

            val pattern44 = Pattern.compile("[A-Z]{2}[0-9]{2}$") //WW12
            val matcher44 = pattern44.matcher(value)
            val found44 = matcher44.find()

            val pattern444 = Pattern.compile("[A-Z]{2}[0-9]{3}$") //WW123
            val matcher444 = pattern444.matcher(value)
            val found444 = matcher444.find()

            if(found || found0 || found00 || found000 || found1 || found11 || found111 || found1111 || found2 || found22 || found222 || found2222 || found3 || found4 || found44 || found444){
                extras.putDouble("TOTAL_PRICE", passToPay)
                extras.putString("ACTIVITY", "Ez Snack")
                extras.putString("USER_ID", refreshID)
                extras.putString("LOCATION_NAME", stationR)
                extras.putString("PUMP_ADDRESS", addressR)
                extras.putDouble("WALLET_BALANCE", walletBalance)
                extras.putString("USER_PIN", userPin)
                val intent = Intent(context, ChargingPinActivity::class.java)
                intent.putExtras(extras)
                Toast.makeText(context, "TO PAY", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                Toast.makeText(context, "Invalid Plate Number", Toast.LENGTH_SHORT).show()
            }

        }
    }
}