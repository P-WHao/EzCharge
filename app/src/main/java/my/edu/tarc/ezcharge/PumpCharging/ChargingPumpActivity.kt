package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.ezcharge.Data.ViewModel.PumpItemViewModel
import my.edu.tarc.ezcharge.MyApplication
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingPumpBinding
import java.text.SimpleDateFormat
import java.util.*

class ChargingPumpActivity : AppCompatActivity() {

    //TO DO
    //Fix the ending time
    //Fix the button type


    private lateinit var binding: ActivityChargingPumpBinding

    var count = 30
    var distance = 120
    var price = 60.00
    var types = ""

    private val pumpItemViewModel: PumpItemViewModel by viewModels{
        PumpItemViewModel.PumpItemViewModelFactory((application as MyApplication).pumpItemRespository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityChargingPumpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = Bundle()

        //Get scanned item id from previous activity
        val itemCode = intent.getStringExtra("itemCode").toString()

        //Set text view
        val stationName: TextView = binding.stationName
        val stationPumpNo: TextView = binding.stationPumpNo
        val stationCable: RadioGroup = binding.radioGroupConnector
        val radioButtonType1: RadioButton = binding.radioButtonType1
        val radioButtonType2: RadioButton = binding.radioButtonType2
        val radioButtonType3: RadioButton = binding.radioButtonType3
        val radioButtonType4: RadioButton = binding.radioButtonType4

        //Variable store searched item
        var stationIDR : String = ""
        var stationNameR : String = ""
        var stationPumpR : Int = 0
        var stationCableR : String = ""
        var addTime : Int = 30
        var stationAddressR : String = ""

        var passName : String = ""
        var passPumpNo : Int = 0
        var passAddress : String = ""

        //Get time
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val d = simpleDateFormat.parse(currentDateAndTime)
        val cal = Calendar.getInstance()

        //Retrieve scan item from item with view model
            pumpItemViewModel.getScanItem(itemCode).observe(this, { itemList ->
                itemList?.let {
                    val items = itemList.toString()
                    val itemsFilter1 = items.replace("[PumpItem(Pump_ID=", "")
                    val itemsFilter2 = itemsFilter1.replace("Pump_Name=", "")
                    val itemsFilter3 = itemsFilter2.replace("Pump_No=", "")
                    val itemsFilter4 = itemsFilter3.replace("Pump_Type=", "")
                    val itemsFilter5 = itemsFilter4.replace("Pump_Address=", "")
                    val itemsFilter6 = itemsFilter5.replace(")]","")
                    val itemsFilter7 = itemsFilter6.replace(" ","")
                    val itemArrayNice: List<String> = itemsFilter6.split(",")
                    val itemArray: List<String> = itemsFilter7.split(",")

                    //Then store the result to variable
                    stationIDR = itemArray[0]
                    stationNameR = itemArrayNice[1]
                    stationPumpR = itemArray[2].toInt()
                    stationCableR = itemArray[3]
                    stationAddressR = itemArrayNice[4]
                    stationNameR = stationNameR.replace("\\s".toRegex(), " ")
                    stationAddressR = stationAddressR.replace("\\s".toRegex(), " ")

                    //set view
                    stationName.text = stationNameR
                    stationPumpNo.text = itemArray[2]

                    passName = stationNameR
                    passPumpNo = stationPumpR
                    passAddress = stationAddressR

//                when(stationCableR) {
//                    "universal" -> {
//                        radioButtonType1.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType2.setBackgroundResource(R.drawable.ccs)
//                        radioButtonType3.setBackgroundResource(R.drawable.gbt)
//                        radioButtonType4.setBackgroundResource(R.drawable.menkenes)
//                    }
//                    "multi" -> {
//                        radioButtonType1.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType2.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType3.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType4.setBackgroundResource(R.drawable.chademo)
//                    }
//                    "many" -> {
//                        radioButtonType1.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType2.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType3.setBackgroundResource(R.drawable.chademo)
//                        radioButtonType4.setBackgroundResource(R.drawable.chademo)
//                    }
//                }
                }
            })
        cal.time = d
        cal.add(Calendar.MINUTE, addTime)
        val newTime = simpleDateFormat.format(cal.time)

        //Default
        binding.startTimeResult.text = currentDateAndTime
        binding.endTimeResult.text = newTime
        binding.durationTimeResult.text = count.toString() + " Minutes"
        binding.distanceTimeResult.text = distance.toString() + " KM"
        //We set 50km = RM 10.00
        binding.priceTimeResult.text = String.format("RM %.2f", price)

        //Button click
        binding.buttonIn.setOnClickListener{
            if(count >= 55) count = 55
            else{
                count+=5
                distance+=20
                price+=10
                addTime+=5
            }
            cal.time = d
            cal.add(Calendar.MINUTE, addTime)
            val newTime = simpleDateFormat.format(cal.time)
            binding.chargeValue.text = count.toString()
            binding.endTimeResult.text = newTime
            binding.durationTimeResult.text = count.toString() + " Minutes"
            binding.distanceTimeResult.text = distance.toString() + " KM"
            //We set 50km = RM 10.00
            binding.priceTimeResult.text = String.format("RM %.2f", price)

        }

        binding.buttonDe.setOnClickListener{
            if(count <= 5) count = 5
            else{
                count-=5
                distance-=20
                price-=10
                addTime-=5
            }
            cal.time = d
            cal.add(Calendar.MINUTE, addTime)
            val newTime = simpleDateFormat.format(cal.time)
            binding.chargeValue.text = count.toString()
            binding.endTimeResult.text = newTime
            binding.durationTimeResult.text = count.toString() + " Minutes"
            binding.distanceTimeResult.text = distance.toString() + " KM"
            //We set 50km = RM 10.00
            binding.priceTimeResult.text = String.format("RM %.2f", price)
        }

        binding.buttonPay.setOnClickListener {
            val gender = binding.radioGroupConnector.checkedRadioButtonId
            if(gender == binding.radioButtonType1.id){
                types = "CHAdeMo - Japan"
            }else if(gender == binding.radioButtonType2.id){
                types = "CCS1 - N.America"
            }else if(gender == binding.radioButtonType3.id){
                types = "GB/T - China"
            }else{
                types = "Menneskes - EU"
            }

            extras.putString("CONNECTOR_TYPES", types)
            extras.putDouble("TOTAL_PRICE",price)
            extras.putInt("DURATION", count)
            extras.putString("ACTIVITY", "Charge")
            extras.putString("END_TIME", newTime)
            extras.putString("LOCATION_NAME", passName)
            extras.putInt("PUMP_NO", passPumpNo)
            extras.putString("PUMP_ADDRESS", passAddress)
            val intent = Intent(this, ChargingPinActivity::class.java)
            intent.putExtras(extras)
            startActivity(intent)
            //val intent = Intent(this, ChargingPinActivity::class.java)
//            intent.putExtra(EXTRA_PRICE, price)
//            intent.putExtra(EXTRA_ACTIVITY, "Charge")
//            startActivity(intent)
        }



        binding.imageViewBack.setOnClickListener {
            finish()
        }
    }

//    companion object{
//        public const val EXTRA_PRICE = "my.edu.tarc.bmi_calculator.price"
//        public const val EXTRA_ACTIVITY = "my.edu.tarc.bmi_calculator.Charge"
//    }

}